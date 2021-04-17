package net.dark_roleplay.projectbrazier.feature_client.blocks;

import net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel.IQuadProvider;
import net.dark_roleplay.projectbrazier.feature.blocks.FlowerContainerData;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class CFlowerContainerData extends FlowerContainerData implements IQuadProvider {
	private EnumMap<Direction, List<BakedQuad>> quads;
	private List<BakedQuad> nullQuads;

	@Override
	public void deserialize(CompoundNBT nbt) {
		super.deserialize(nbt);
		quads = null;
		nullQuads = null;
	}

	public List<BakedQuad> getQuads(@Nullable BlockState teState, @Nullable Direction side, @Nonnull Random rand) {
		if (this.flower.isEmpty()) return null;
		if (quads == null) {
			quads = new EnumMap(Direction.class);
			nullQuads = new ArrayList<>();

			Item item = flower.getItem();

			BlockItem block = (BlockItem) item;
			BlockState state = block.getBlock().getDefaultState();

			BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
			IBakedModel cachedModel = blockrendererdispatcher.getModelForState(state);

			for (Direction dir : Direction.values()) {
				List<BakedQuad> facedQuads = quads.computeIfAbsent(dir, key -> new ArrayList<BakedQuad>());
				facedQuads.addAll(cachedModel.getQuads(state, side, rand));
			}
			nullQuads.addAll(cachedModel.getQuads(state, null, rand));
		}

		if (side == null)
			return nullQuads;

		return quads.get(side);
	}
}
