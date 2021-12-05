package net.dark_roleplay.projectbrazier.feature_client.model_loaders.emissive;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.*;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.CompositeModel;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmissiveModel implements IModelGeometry {

	private final UnbakedModel emissiveModel, nonEmissiveModel;
	private final int skyLight, blockLight;

	public EmissiveModel(UnbakedModel emissiveModel, UnbakedModel nonEmissiveModel, int skyLight, int blockLight){
		this.emissiveModel = emissiveModel;
		this.nonEmissiveModel = nonEmissiveModel;
		this.skyLight = skyLight;
		this.blockLight = blockLight;
	}

	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
		ImmutableMap.Builder<String, BakedModel> builder = ImmutableMap.builder();
		if(this.nonEmissiveModel != null)
			builder.put("non_emissive", this.nonEmissiveModel.bake(bakery, spriteGetter, modelTransform, modelLocation));

		BakedModel preEmissiveBakedModel = this.emissiveModel.bake(bakery, spriteGetter, modelTransform, modelLocation);
		if(preEmissiveBakedModel instanceof SimpleBakedModel){
			List<BakedQuad> quads = transformQuads(preEmissiveBakedModel.getQuads(null, null, null, EmptyModelData.INSTANCE), this.skyLight, this.blockLight);
			Map<Direction, List<BakedQuad>> faceQuads = new EnumMap<>(Direction.class);

			for(Direction dir : Direction.values()){
				faceQuads.put(dir, transformQuads(preEmissiveBakedModel.getQuads(null, dir, null, EmptyModelData.INSTANCE), this.skyLight, this.blockLight));
			}

			builder.put("emissive",new SimpleBakedModel(
					quads, faceQuads,
					preEmissiveBakedModel.useAmbientOcclusion(),
					preEmissiveBakedModel.usesBlockLight(),
					preEmissiveBakedModel.isGui3d(),
					preEmissiveBakedModel.getParticleIcon(),
					preEmissiveBakedModel.getTransforms(),
					preEmissiveBakedModel.getOverrides()
			));
		}
		return new CompositeModel(owner.isShadedInGui(), owner.isSideLit(), owner.useSmoothLighting(), ((Function<Material, TextureAtlasSprite>)spriteGetter).apply(owner.resolveTexture("particle")), builder.build(), owner.getCombinedTransform(), overrides);
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		Set<Material> textures = new HashSet<>();
		textures.addAll(emissiveModel.getMaterials(modelGetter, missingTextureErrors));
		if(nonEmissiveModel != null)
		textures.addAll(nonEmissiveModel.getMaterials(modelGetter, missingTextureErrors));
		return textures;
	}

	public static class Loader implements IModelLoader {

		@Override
		public void onResourceManagerReload(ResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			UnbakedModel nonEmissive = null, emissive = null;
			if(modelContents.has("nonEmissive")){
				nonEmissive = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"nonEmissive"), BlockModel.class);
			}
			if(modelContents.has("emissive")){
				emissive = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"emissive"), BlockModel.class);
			}
			int skyLight = GsonHelper.getAsInt(modelContents, "skyLight", 15);
			int blockLight = GsonHelper.getAsInt(modelContents, "blockLight", 15);

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
		int[] vertexData = quad.getVertices().clone();

		// Set lighting to light value on all vertices
		vertexData[6] = light;
		vertexData[6 + 8] = light;
		vertexData[6 + 8 + 8] = light;
		vertexData[6 + 8 + 8 + 8] = light;

		//isShade -> shouldApplyDiffuseLighting
		return new BakedQuad(
				vertexData,
				quad.getTintIndex(),
				quad.getDirection(),
				quad.getSprite(),
				quad.isShade()
		);
	}
}
