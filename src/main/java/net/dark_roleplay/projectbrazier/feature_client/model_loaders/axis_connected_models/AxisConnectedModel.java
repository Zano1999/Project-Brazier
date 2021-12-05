package net.dark_roleplay.projectbrazier.feature_client.model_loaders.axis_connected_models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import org.openjdk.nashorn.internal.objects.annotations.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class AxisConnectedModel implements IModelGeometry {

	UnbakedModel defaultModel, positiveModel, negativeModel, centeredModel;

	public AxisConnectedModel(UnbakedModel defaultModel, UnbakedModel positiveModel, UnbakedModel negativeModel, UnbakedModel centeredModel){
		this.defaultModel = defaultModel;
		this.positiveModel = positiveModel;
		this.negativeModel = negativeModel;
		this.centeredModel = centeredModel;
	}

	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
		return new ConnectedBakedModel(
				this.defaultModel.bake(bakery, spriteGetter, modelTransform, modelLocation),
				this.positiveModel == null ? null : this.positiveModel.bake(bakery, spriteGetter, modelTransform, modelLocation),
				this.negativeModel == null ? null : this.negativeModel.bake(bakery, spriteGetter, modelTransform, modelLocation),
				this.centeredModel.bake(bakery, spriteGetter, modelTransform, modelLocation)
		);
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		Set<Material> textures = new HashSet<>();

		textures.addAll(defaultModel.getMaterials(modelGetter, missingTextureErrors));
		if(positiveModel != null) textures.addAll(positiveModel.getMaterials(modelGetter, missingTextureErrors));
		if(negativeModel != null) textures.addAll(negativeModel.getMaterials(modelGetter, missingTextureErrors));
		textures.addAll(centeredModel.getMaterials(modelGetter, missingTextureErrors));

		return textures;
	}

	public static class ConnectedBakedModel extends BakedModelWrapper {

		protected BakedModel positiveModel, negativeModel, centeredModel;

		public ConnectedBakedModel(BakedModel defaultModel, BakedModel positiveModel, BakedModel negativeModel, BakedModel centeredModel) {
			super(defaultModel);
			this.positiveModel = positiveModel == null ? centeredModel : positiveModel;
			this.negativeModel = negativeModel == null ? centeredModel : negativeModel;
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
		public IModelData getModelData(@Nonnull BlockAndTintGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
			IModelData data = new AxisConnectedModelData();
			data.setData(AxisConnectedModelData.CONNECTION, AxisConnectionType.getConnections(world, pos, state));
			return data;
		}
	}

	public static class Loader implements IModelLoader {

		@Override
		public void onResourceManagerReload(ResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			UnbakedModel defaultModel = null, positiveModel = null, negativeModel = null, centeredModel = null;
			if(modelContents.has("default")){
				defaultModel = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"default"), BlockModel.class);
			}
			if(modelContents.has("positive")){
				positiveModel = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"positive"), BlockModel.class);
			}
			if(modelContents.has("negative")){
				negativeModel = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"negative"), BlockModel.class);
			}
			if(modelContents.has("centered")){
				centeredModel = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"centered"), BlockModel.class);
			}

			if(modelContents.has("single")){
				defaultModel = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"single"), BlockModel.class);
			}
			if(modelContents.has("multi")){
				centeredModel = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"multi"), BlockModel.class);
			}

			return new AxisConnectedModel(defaultModel, positiveModel, negativeModel, centeredModel);
		}
	}


}
