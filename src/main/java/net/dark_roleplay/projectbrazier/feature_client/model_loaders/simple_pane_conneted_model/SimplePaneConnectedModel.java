package net.dark_roleplay.projectbrazier.feature_client.model_loaders.simple_pane_conneted_model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.axis_connected_models.AxisConnectionType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.resources.IResourceManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class SimplePaneConnectedModel implements IModelGeometry {

	private final IUnbakedModel baseModel, priPosModel, priNegModel, secPosModel, secNegModel;

	public SimplePaneConnectedModel(IUnbakedModel baseModel, IUnbakedModel priPosModel, IUnbakedModel priNegModel, IUnbakedModel secPosModel, IUnbakedModel secNegModel) {
		this.baseModel = baseModel;
		this.priPosModel = priPosModel;
		this.priNegModel = priNegModel;
		this.secPosModel = secPosModel;
		this.secNegModel = secNegModel;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		return new ConnectedBakedModel(
				this.baseModel.bake(bakery, spriteGetter, modelTransform, modelLocation),
				this.priPosModel.bake(bakery, spriteGetter, modelTransform, modelLocation),
				this.priNegModel.bake(bakery, spriteGetter, modelTransform, modelLocation),
				this.secPosModel.bake(bakery, spriteGetter, modelTransform, modelLocation),
				this.secNegModel.bake(bakery, spriteGetter, modelTransform, modelLocation));
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {

		Set<RenderMaterial> textures = new HashSet<>();

		textures.addAll(baseModel.getMaterials(modelGetter, missingTextureErrors));
		textures.addAll(priPosModel.getMaterials(modelGetter, missingTextureErrors));
		textures.addAll(priNegModel.getMaterials(modelGetter, missingTextureErrors));
		textures.addAll(secPosModel.getMaterials(modelGetter, missingTextureErrors));
		textures.addAll(secNegModel.getMaterials(modelGetter, missingTextureErrors));

		return textures;
	}

	public static class ConnectedBakedModel extends BakedModelWrapper {

		protected final IBakedModel priPosModel, priNegModel, secPosModel, secNegModel;

		public ConnectedBakedModel(IBakedModel baseModel, IBakedModel priPosModel, IBakedModel priNegModel, IBakedModel secPosModel, IBakedModel secNegModel) {
			super(baseModel);
			this.priPosModel = priPosModel;
			this.priNegModel = priNegModel;
			this.secPosModel = secPosModel;
			this.secNegModel = secNegModel;
		}


		//hasProperty -> BlockState#has
		@Nonnull
		@Override
		public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
			Direction facing = state.hasProperty(BlockStateProperties.HORIZONTAL_FACING) ? state.getValue(BlockStateProperties.HORIZONTAL_FACING) :
					state.hasProperty(BlockStateProperties.FACING) ? state.getValue(BlockStateProperties.FACING) : null;
			boolean flag = facing == Direction.NORTH || facing == Direction.EAST || facing == Direction.UP || facing == Direction.DOWN;
			boolean flag2 = facing == Direction.DOWN;


			if (extraData instanceof SimplePaneModelData) {
				List<BakedQuad> totalQuads = new ArrayList<>();
				totalQuads.addAll(this.originalModel.getQuads(state, side, rand, extraData));

				AxisConnectionType primaryConnection = extraData.getData(SimplePaneModelData.PRIMARY_CONNECTION);
				if(!flag2 ? !primaryConnection.isPositive() : !primaryConnection.isNegative())
					totalQuads.addAll(this.priPosModel.getQuads(state, side, rand, extraData));
				if(!flag2 ? !primaryConnection.isNegative() : !primaryConnection.isPositive())
					totalQuads.addAll(this.priNegModel.getQuads(state, side, rand, extraData));

				AxisConnectionType secondaryConnection = extraData.getData(SimplePaneModelData.SECONDARY_CONNECTION);
				if(flag && !secondaryConnection.isPositive() || !flag && !secondaryConnection.isNegative())
					totalQuads.addAll(this.secPosModel.getQuads(state, side, rand, extraData));
				if(flag && !secondaryConnection.isNegative() || !flag && !secondaryConnection.isPositive())
					totalQuads.addAll(this.secNegModel.getQuads(state, side, rand, extraData));
				return totalQuads;
			}

			return this.originalModel.getQuads(state, side, rand, extraData);
		}

		// hasProperty -> BlockState#has
		@Nonnull
		@Override
		public IModelData getModelData(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
			IModelData data = new SimplePaneModelData();

			boolean flag, flag2 = false, flag3 = false;
			if ((flag = state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) ||
					(flag2 = state.hasProperty(BlockStateProperties.AXIS)) ||
					(flag3 = state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) ||
					state.hasProperty(BlockStateProperties.FACING)
			) {
				Direction.Axis axis = flag ?
						state.getValue(BlockStateProperties.HORIZONTAL_AXIS) :
						flag2 ?
								state.getValue(BlockStateProperties.AXIS) :
								flag3 ? state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis() :
										state.getValue(BlockStateProperties.FACING).getAxis();

				switch(axis){
					case X:
						data.setData(SimplePaneModelData.PRIMARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.Y));
						data.setData(SimplePaneModelData.SECONDARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.Z));
						break;
					case Z:
						data.setData(SimplePaneModelData.PRIMARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.Y));
						data.setData(SimplePaneModelData.SECONDARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.X));
						break;
					case Y:
						data.setData(SimplePaneModelData.PRIMARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.Z));
						data.setData(SimplePaneModelData.SECONDARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.X));
						break;
				}
			}

			return data;
		}
	}

	public static class Loader implements IModelLoader {
		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserCtx, JsonObject modelContents) {
			IUnbakedModel baseModel, priPosModel, priNegModel, secPosModel, secNegModel;

			JsonObject textures = JSONUtils.getAsJsonObject(modelContents, "textures", null);

			JsonObject primarySubContents = JSONUtils.getAsJsonObject(modelContents, "primary");
			JsonObject secondarySubContents = JSONUtils.getAsJsonObject(modelContents, "secondary");

			baseModel = loadSubModel(deserCtx, modelContents, "base", textures);
			priPosModel = loadSubModel(deserCtx, primarySubContents, "positive", textures);
			priNegModel = loadSubModel(deserCtx, primarySubContents, "negative", textures);
			secPosModel = loadSubModel(deserCtx, secondarySubContents, "positive", textures);
			secNegModel = loadSubModel(deserCtx, secondarySubContents, "negative", textures);

			return new SimplePaneConnectedModel(baseModel, priPosModel, priNegModel, secPosModel, secNegModel);
		}

		private IUnbakedModel loadSubModel(JsonDeserializationContext deserCtx, JsonObject base, String subModelName, JsonObject textures){
			JsonObject subModelJson = JSONUtils.getAsJsonObject(base, subModelName);
			if(textures != null){
				JsonObject subModelTextures = JSONUtils.getAsJsonObject(base, "textures", new JsonObject());
				for(Map.Entry<String, JsonElement> entry : textures.entrySet())
					subModelTextures.add(entry.getKey(), entry.getValue());

				subModelJson.add("textures", subModelTextures);
			}

			return deserCtx.deserialize(subModelJson, BlockModel.class);
		}
	}
}
