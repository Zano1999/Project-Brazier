package net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PaneCornerBakedModel extends BakedModelWrapper {
	private BakedModel[] inner_corner, outer_corner, horizontal, vertical, none;
	private static final List<BakedQuad> EMPTY = new ArrayList<>();

	public PaneCornerBakedModel(BakedModel uncodnitional, BakedModel[] inner_corner, BakedModel[] outer_corner, BakedModel[] horizontal, BakedModel[] vertical, BakedModel[] none) {
		super(uncodnitional);
		this.inner_corner = inner_corner;
		this.outer_corner = outer_corner;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.none = none;
	}

	@Nonnull
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
		List<BakedQuad> mergedModel = new ArrayList<>();

		mergedModel.addAll(this.originalModel.getQuads(state, side, rand, extraData));

		for (int i = 0; i < 4; i++)
			mergedModel.addAll(getQuadsForTypeAndIndex(extraData.getData(PaneCornerData.CONNECTION_PROPS[i]), i, state, side, rand, extraData));

		return mergedModel;
	}

	private List<BakedQuad> getQuadsForTypeAndIndex(PaneCornerType type, int index, @Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
		switch (type) {
			case NONE:
				return none == null ? EMPTY : this.none[index].getQuads(state, side, rand, extraData);
			case HORZIONTAL:
				return this.horizontal[index].getQuads(state, side, rand, extraData);
			case VERTICAL:
				return this.vertical[index].getQuads(state, side, rand, extraData);
			case INNER_CORNER:
				return this.inner_corner[index].getQuads(state, side, rand, extraData);
			case OUTER_CORNER:
				return this.outer_corner[index].getQuads(state, side, rand, extraData);
			default:
				return EMPTY;
		}
	}

	@Nonnull
	@Override
	public IModelData getModelData(@Nonnull BlockAndTintGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
		PaneCornerData data = new PaneCornerData();

		data.setData(PaneCornerData.CONNECTION_PROPS[0], PaneCornerType.getCornerType(world, pos, state, Direction.NORTH));
		data.setData(PaneCornerData.CONNECTION_PROPS[1], PaneCornerType.getCornerType(world, pos, state, Direction.EAST));
		data.setData(PaneCornerData.CONNECTION_PROPS[2], PaneCornerType.getCornerType(world, pos, state, Direction.SOUTH));
		data.setData(PaneCornerData.CONNECTION_PROPS[3], PaneCornerType.getCornerType(world, pos, state, Direction.WEST));

		return data;
	}
}