package net.dark_roleplay.projectbrazier.feature_client.blocks;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.dark_roleplay.projectbrazier.experimental_features.builtin_mixed_model.IQuadProvider;
import net.dark_roleplay.projectbrazier.feature.blocks.FlowerContainerData;
import net.dark_roleplay.projectbrazier.feature_client.listeners.ResourceReloadUtil;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class CFlowerContainerData extends FlowerContainerData implements IQuadProvider {
	private EnumMap<Direction, List<BakedQuad>> quads;
	private List<BakedQuad> nullQuads;

	public CFlowerContainerData(){
		ResourceReloadUtil.addReloadListener(this, () -> quads = null);
	}

	@Override
	public void deserialize(CompoundTag nbt) {
		super.deserialize(nbt);
		quads = null;
		nullQuads = null;
	}

	public List<BakedQuad> getQuads(@Nullable BlockState teState, @Nullable Direction side, @Nonnull Random rand) {
		if (this.flower.isEmpty()) return null;
		if (quads == null) {
			Item item = flower.getItem();
			if(!(item instanceof BlockItem)) return new ArrayList<>();

			quads = new EnumMap(Direction.class);
			nullQuads = new ArrayList<>();

			BlockItem block = (BlockItem) item;
			BlockState[] states = {block.getBlock().defaultBlockState(), null};
			Vec3i offset = this.placement;

			if(states[0].hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)){
				DoubleBlockHalf sourceHalf = states[0].getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
				states[1] = states[0].setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, sourceHalf == DoubleBlockHalf.LOWER ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER);
			}

			BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();

			for(BlockState state : states) {
				if(state == null) continue;
				BakedModel cachedModel = blockrendererdispatcher.getBlockModel(state);

				for (Direction dir : Direction.values()) {
					List<BakedQuad> facedQuads = quads.computeIfAbsent(dir, key -> new ArrayList<BakedQuad>());
					List<BakedQuad> quads = cachedModel.getQuads(state, side, rand);
					for(BakedQuad quad : quads)
						facedQuads.add(translateQuad(quad, offset));

				}
				List<BakedQuad> quads = cachedModel.getQuads(state, null, rand);
				for(BakedQuad quad : quads)
					nullQuads.add(translateQuad(quad, offset));

				offset = offset.above(16);
			}
		}

		if (side == null)
			return nullQuads;

		return quads.get(side);
	}

	private BakedQuad translateQuad(BakedQuad quad, Vec3i offset){
		int[] vertexData = quad.getVertices();
		int[] newVertexData = new int[vertexData.length];
		VertexFormat format = DefaultVertexFormat.BLOCK;
		float offsetX = offset.getX() * 0.0625F - 0.5F + 0.03125F;
		float offsetY = offset.getY() * 0.0625F;
		float offsetZ = offset.getZ() * 0.0625F - 0.5F + 0.03125F;

		for(int i = 0; i < vertexData.length; i += format.getIntegerSize()){
			newVertexData[i] = Float.floatToIntBits(Float.intBitsToFloat(vertexData[i]) + offsetX);
			newVertexData[i + 1] = Float.floatToIntBits(Float.intBitsToFloat(vertexData[i + 1]) + offsetY);
			newVertexData[i + 2] = Float.floatToIntBits(Float.intBitsToFloat(vertexData[i + 2]) + offsetZ);
			for(int j = 3; j < format.getIntegerSize(); j++)
				newVertexData[i + j] = vertexData[i + j];
		}
		return new BakedQuad(newVertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), false);
	}
}
