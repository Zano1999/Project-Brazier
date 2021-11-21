package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraft.item.Item.Properties;

public class DecorItem extends Item {

	private Decor decor;

	public DecorItem(Properties properties) {
		super(properties);
	}

	public void setDecor(Decor decor){
		this.decor = decor;
	}

	public ActionResultType useOn(ItemUseContext context) {
		Chunk targetChunk = context.getLevel().getChunkAt(context.getClickedPos());

		LazyOptional<DecorContainer> capability = targetChunk.getCapability(DecorRegistrar.DECOR);
		capability.ifPresent(decorCon -> {
			DecorChunk decChunk = decorCon.getDecorChunk(context.getClickedPos().getY() >> 4, true);
			DecorState decorState;
			decChunk.addDecor(decorState = new DecorState(decor));
			decorState.setPosition(new Vector3d(context.getClickedPos().getX() & 0xF, Math.floorMod(context.getClickedPos().relative(context.getClickedFace()).getY(), 16), Math.floorMod(context.getClickedPos().getZ(), 16)));
		});
		if(context.getLevel().isClientSide()){
			DecorListener.markChunkDirty(context.getLevel(), context.getClickedPos());
		}
		return ActionResultType.SUCCESS;
	}
}
