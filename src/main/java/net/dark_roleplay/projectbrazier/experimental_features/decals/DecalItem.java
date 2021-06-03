package net.dark_roleplay.projectbrazier.experimental_features.decals;

import net.dark_roleplay.projectbrazier.experimental_features.decals.capability.CapabilityAttachListener;
import net.dark_roleplay.projectbrazier.experimental_features.decals.capability.DecalChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decals.decal.Decal;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.LazyOptional;

public class DecalItem extends Item {

	private Decal decal;

	public DecalItem(Decal decal, Properties properties) {
		super(properties);
		this.decal = decal;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));
		return !actionresulttype.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
	}

	protected ActionResultType tryPlace(BlockItemUseContext context){
		Chunk targetChunk = context.getWorld().getChunkAt(context.getPos());

		LazyOptional<DecalChunk> capability = targetChunk.getCapability(CapabilityAttachListener.DECAL_CAPABILITY);
		capability.ifPresent(decalCon -> {
			decalCon.setDecalState(this.decal.getDefaultState(), context.getPos(), context.getFace());
		});
		return ActionResultType.SUCCESS;
	}
}
