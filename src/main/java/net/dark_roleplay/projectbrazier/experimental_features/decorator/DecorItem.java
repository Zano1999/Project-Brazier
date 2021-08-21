package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.LazyOptional;

public class DecorItem extends Item {

	private Decor decor;

	public DecorItem(Properties properties) {
		super(properties);
	}

	public void setDecor(Decor decor){
		this.decor = decor;
	}

	public ActionResultType onItemUse(ItemUseContext context) {
		Chunk targetChunk = context.getWorld().getChunkAt(context.getPos());

		LazyOptional<DecorContainer> capability = targetChunk.getCapability(DecorRegistrar.DECOR);
		capability.ifPresent(decorCon -> {
			DecorChunk decChunk = decorCon.getDecorChunk(context.getPos().getY() >> 4, true);
			DecorState decorState = new DecorState(decor);
			decChunk.addDecor(decorState);
			Vector3d chunkRelativePos = new Vector3d(
					context.getPos().getX() & 0xF,
					Math.floorMod(context.getPos().offset(context.getFace()).getY(), 16),
					Math.floorMod(context.getPos().getZ(), 16)
			);

			decorState.setPosition(context.getPos(), chunkRelativePos);
		});
		if(context.getWorld().isRemote()){
			DecorListener.markChunkDirty(context.getWorld(), context.getPos());
		}
		return ActionResultType.SUCCESS;
	}
}
