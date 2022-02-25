package net.dark_roleplay.projectbrazier.experimental_features.chopping_block;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

public class ChoppingBlockBlockEntity extends BlockEntity {

	private ItemStack heldItem = new ItemStack(Items.AIR);

	public ChoppingBlockBlockEntity(BlockPos pos, BlockState state) {
		super(BrazierBlockEntities.CHOPPING_BLOCK.get(), pos, state);
	}

	public ItemStack getHeldItem() {
		return heldItem;
	}

	public boolean isEmpty(){
		return this.heldItem.isEmpty();
	}

	public void addHeldItem(ItemStack heldItem) {
		if(!isEmpty()) return;
		this.heldItem = new ItemStack(heldItem.getItem(), 1);
		this.setChanged();

		heldItem.shrink(1);
	}

	public void setChanged() {
		super.setChanged();

		if(this.level != null && !this.level.isClientSide()){
			this.syncToClient();
		}
	}

	//region Client synch
	public void forceSetItem(ItemStack item){
		this.heldItem = item;
	}

	public void syncToClient(){
		BrazierPackets.BLOCK_ENTITY.send(PacketDistributor.TRACKING_CHUNK.with(() -> this.level.getChunkAt(this.getBlockPos())), new ChoppingBlockBlockEntitySync(this));
	}
	//endregion

	//region (De-)Serialization
	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if(tag.contains("item"))
			this.heldItem = ItemStack.of(tag.getCompound("item"));
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		if(!this.heldItem.isEmpty())
			tag.put("item", this.heldItem.serializeNBT());
	}
	//endregion
}
