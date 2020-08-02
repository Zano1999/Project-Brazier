package net.dark_roleplay.projectbrazier.features.model_loaders.emissive;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.CompositeModel;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmissiveModel implements IModelGeometry {

	private final IUnbakedModel emissiveModel, nonEmissiveModel;
	private final int skyLight, blockLight;

	public EmissiveModel(IUnbakedModel emissiveModel, IUnbakedModel nonEmissiveModel, int skyLight, int blockLight){
		this.emissiveModel = emissiveModel;
		this.nonEmissiveModel = nonEmissiveModel;
		this.skyLight = skyLight;
		this.blockLight = blockLight;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		ImmutableMap.Builder<String, IBakedModel> builder = ImmutableMap.builder();
		if(this.nonEmissiveModel != null)
			builder.put("non_emissive", this.nonEmissiveModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation));

		IBakedModel preEmissiveBakedModel = this.emissiveModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation);
		if(preEmissiveBakedModel instanceof SimpleBakedModel){
			List<BakedQuad> quads = transformQuads(preEmissiveBakedModel.getQuads(null, null, null, EmptyModelData.INSTANCE), this.skyLight, this.blockLight);
			Map<Direction, List<BakedQuad>> faceQuads = new EnumMap<>(Direction.class);

			for(Direction dir : Direction.values()){
				faceQuads.put(dir, transformQuads(preEmissiveBakedModel.getQuads(null, dir, null, EmptyModelData.INSTANCE), this.skyLight, this.blockLight));
			}

			builder.put("emissive",new SimpleBakedModel(
					quads, faceQuads,
					preEmissiveBakedModel.isAmbientOcclusion(),
					preEmissiveBakedModel.func_230044_c_(),
					preEmissiveBakedModel.isGui3d(),
					preEmissiveBakedModel.getParticleTexture(),
					preEmissiveBakedModel.getItemCameraTransforms(),
					preEmissiveBakedModel.getOverrides()
			));
		}
		return new CompositeModel(owner.isShadedInGui(), owner.isSideLit(), owner.useSmoothLighting(), ((Function<RenderMaterial, TextureAtlasSprite>)spriteGetter).apply(owner.resolveTexture("particle")), builder.build(), owner.getCombinedTransform(), overrides);
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		Set<Material> textures = new HashSet<>();
		textures.addAll(emissiveModel.getTextures(modelGetter, missingTextureErrors));
		if(nonEmissiveModel != null)
		textures.addAll(nonEmissiveModel.getTextures(modelGetter, missingTextureErrors));
		return textures;
	}

	public static class Loader implements IModelLoader {

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			IUnbakedModel nonEmissive = null, emissive = null;
			if(modelContents.has("nonEmissive")){
				nonEmissive = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"nonEmissive"), BlockModel.class);
			}
			if(modelContents.has("emissive")){
				emissive = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"emissive"), BlockModel.class);
			}
			int skyLight = JSONUtils.getInt(modelContents, "skyLight", 15);
			int blockLight = JSONUtils.getInt(modelContents, "blockLight", 15);

			return new EmissiveModel(emissive, nonEmissive, skyLight, blockLight);
		}
	}

	private static List<BakedQuad> transformQuads(List<BakedQuad> quads, int skyLight, int blockLight){
		int light = blockLight << 4 | skyLight << 20;
		return quads.stream().map(quad -> EmissiveModel.transformQuad(quad, light)).collect(Collectors.toList());
	}


	private static BakedQuad transformQuad(BakedQuad quad, int skyLight, int blockLight) {
		return transformQuad(quad, blockLight << 4 | skyLight << 20);
	}

	private static BakedQuad transformQuad(BakedQuad quad, int light) {
		int[] vertexData = quad.getVertexData().clone();

		// Set lighting to light value on all vertices
		vertexData[6] = light;
		vertexData[6 + 8] = light;
		vertexData[6 + 8 + 8] = light;
		vertexData[6 + 8 + 8 + 8] = light;

		//func_239287_f_ -> shouldApplyDiffuseLighting
		return new BakedQuad(
				vertexData,
				quad.getTintIndex(),
				quad.getFace(),
				quad.func_187508_a(),
				quad.func_239287_f_()
		);
	}
}
