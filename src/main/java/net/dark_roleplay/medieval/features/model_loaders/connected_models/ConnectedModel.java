package net.dark_roleplay.medieval.features.model_loaders.connected_models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.resources.IResourceManager;
import net.minecraft.state.properties.BlockStateProperties;
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

public class ConnectedModel implements IModelGeometry {

	IUnbakedModel single, left, right, center;

	public ConnectedModel(IUnbakedModel single, IUnbakedModel left, IUnbakedModel right, IUnbakedModel center){
		this.single = single;
		this.left = left;
		this.right = right;
		this.center = center;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		IBakedModel singleBaked = this.single.bakeModel(bakery, spriteGetter, modelTransform, modelLocation);
		IBakedModel leftBaked = this.left.bakeModel(bakery, spriteGetter, modelTransform, modelLocation);
		return new ConnectedBakedModel(singleBaked, leftBaked,
				this.right.bakeModel(bakery, spriteGetter, modelTransform, modelLocation),
				this.center.bakeModel(bakery, spriteGetter, modelTransform, modelLocation));
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		return single.getTextures(modelGetter, missingTextureErrors);
	}

	public static class ConnectedBakedModel extends BakedModelWrapper {

		protected IBakedModel left, right, center;

		public ConnectedBakedModel(IBakedModel single, IBakedModel left, IBakedModel right, IBakedModel center) {
			super(single);
			this.left = left;
			this.right = right;
			this.center = center;
		}

		@Nonnull
		@Override
		public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
			if (extraData instanceof ConnectedModelData) {
				switch (extraData.getData(ConnectedModelData.CONNECTION)) {
					case SINGLE:
						return this.originalModel.getQuads(state, side, rand, extraData);
					case LEFT:
						return this.left.getQuads(state, side, rand, extraData);
					case RIGHT:
						return this.right.getQuads(state, side, rand, extraData);
					case CENTER:
						return this.center.getQuads(state, side, rand, extraData);
				}
			}

			return this.originalModel.getQuads(state, side, rand, extraData);
		}

		@Nonnull
		@Override
		public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
			IModelData data = new ConnectedModelData();

			ConnectedModelData.ConnectionType type = ConnectedModelData.ConnectionType.SINGLE;
			if (state.has(BlockStateProperties.HORIZONTAL_AXIS)) {
				Direction.Axis axis = state.get(BlockStateProperties.HORIZONTAL_AXIS);
				switch (axis) {
					case X:
						type = type.addLeft();
						break;
					case Z:
						type = type.addRight();
						break;
				}
			}
			type = type.addLeft();
			data.setData(ConnectedModelData.CONNECTION, type);
			return data;
		}
	}

	public static class Loader implements IModelLoader {

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			IUnbakedModel single = null, left = null, right = null, center = null;
			if(modelContents.has("singleModel")){
				single = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"singleModel"), BlockModel.class);
			}
			if(modelContents.has("leftModel")){
				left = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"leftModel"), BlockModel.class);
			}
			if(modelContents.has("rightModel")){
				right = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"rightModel"), BlockModel.class);
			}
			if(modelContents.has("centerModel")){
				center = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"centerModel"), BlockModel.class);
			}
			return new ConnectedModel(single, left, right, center);
		}
	}


}
