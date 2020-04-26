package net.dark_roleplay.medieval.features.model_loaders.connected_models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class AxisConnectedModel implements IModelGeometry {

	IUnbakedModel defaultModel, positiveModel, negativeModel, centeredModel;

	public AxisConnectedModel(IUnbakedModel defaultModel, IUnbakedModel positiveModel, IUnbakedModel right, IUnbakedModel centeredModel){
		this.defaultModel = defaultModel;
		this.positiveModel = positiveModel;
		this.negativeModel = right;
		this.centeredModel = centeredModel;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		IBakedModel singleBaked = this.defaultModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation);
		IBakedModel leftBaked = this.positiveModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation);
		return new ConnectedBakedModel(singleBaked, leftBaked,
				this.negativeModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation),
				this.centeredModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation));
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		Set<Material> textures = new HashSet<>();

		textures.addAll(defaultModel.getTextures(modelGetter, missingTextureErrors));
		textures.addAll(positiveModel.getTextures(modelGetter, missingTextureErrors));
		textures.addAll(negativeModel.getTextures(modelGetter, missingTextureErrors));
		textures.addAll(centeredModel.getTextures(modelGetter, missingTextureErrors));

		return textures;
	}

	public static class ConnectedBakedModel extends BakedModelWrapper {

		protected IBakedModel positiveModel, negativeModel, centeredModel;

		public ConnectedBakedModel(IBakedModel defaultModel, IBakedModel positiveModel, IBakedModel negativeModel, IBakedModel centeredModel) {
			super(defaultModel);
			this.positiveModel = positiveModel;
			this.negativeModel = negativeModel;
			this.centeredModel = centeredModel;
		}

		@Nonnull
		@Override
		public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
			if (extraData instanceof AxisConnectedModelData) {
				switch (extraData.getData(AxisConnectedModelData.CONNECTION)) {
					case DEFAULT:
						return this.originalModel.getQuads(state, side, rand, extraData);
					case POSITIVE:
						return this.positiveModel.getQuads(state, side, rand, extraData);
					case NEGATIVE:
						return this.negativeModel.getQuads(state, side, rand, extraData);
					case CENTERED:
						return this.centeredModel.getQuads(state, side, rand, extraData);
				}
			}

			return this.originalModel.getQuads(state, side, rand, extraData);
		}

		@Nonnull
		@Override
		public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
			IModelData data = new AxisConnectedModelData();
			data.setData(AxisConnectedModelData.CONNECTION, AxisConnectionType.getConnections(world, pos, state));
			return data;
		}
	}

	public static class Loader implements IModelLoader {

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			IUnbakedModel defaultModel = null, positiveModel = null, negativeModel = null, centeredModel = null;
			if(modelContents.has("default")){
				defaultModel = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"default"), BlockModel.class);
			}
			if(modelContents.has("positive")){
				positiveModel = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"positive"), BlockModel.class);
			}
			if(modelContents.has("negative")){
				negativeModel = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"negative"), BlockModel.class);
			}
			if(modelContents.has("centered")){
				centeredModel = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"centered"), BlockModel.class);
			}
			return new AxisConnectedModel(defaultModel, positiveModel, negativeModel, centeredModel);
		}
	}


}
