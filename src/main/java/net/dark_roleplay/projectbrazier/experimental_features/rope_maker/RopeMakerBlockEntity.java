package net.dark_roleplay.projectbrazier.experimental_features.rope_maker;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierItems;
import net.dark_roleplay.projectbrazier.util.Inventories;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;

public class RopeMakerBlockEntity extends BlockEntity {

	//region Attributes
	private CraftingStage stage = CraftingStage.ADD_MATERIAL;
	private ItemStackHandler itemHandler = new ItemStackHandler() {
		@Override
		protected void onContentsChanged(int slot){
			RopeMakerBlockEntity.this.setChanged();
		}
	};
	private float twistingProgress = 0;
	//endregion

	public RopeMakerBlockEntity(BlockPos pos, BlockState state) {
		//TODO set Type
		super(null, pos, state);
	}

	//region Logic
	public CraftingStage getState(){
		return this.stage;
	}

	public float getTwistingProgress(){
		return this.twistingProgress;
	}

	public ItemStackHandler getItemHandler(){
		return this.itemHandler;
	}

	public boolean addStrings(Player player){
		int missingItems = Inventories.doesPlayerHaveEnoughItems(player, Items.STRING, 8);
		if(missingItems > 0){
			if(player instanceof ServerPlayer sp)
				sp.displayClientMessage(new TranslatableComponent("interaction.projectbrazier.rope_maker.not_enough_string", missingItems), true);
			return false;
		}
		itemHandler.insertItem(0, new ItemStack(Items.STRING, 8), false);
		if(itemHandler.getStackInSlot(0).getCount() == 64){
			this.stage = CraftingStage.TWIST;
			this.setChanged();
		}
		Inventories.consumeAmountOfItems(player, Items.STRING, 8);
		return true;
	}

	public boolean twist(){
		this.twistingProgress += 1;
		if(this.twistingProgress >= 8){
			this.itemHandler.extractItem(0, 64, false);
			this.stage = CraftingStage.ADD_MATERIAL;
			this.setChanged();
			Inventories.dropItems(this.level, this.getBlockPos(), new ItemStack(BrazierItems.ROPE.get(), 8));
		}
		return false;
	}
	//endregion

	//region (De-)Serialization
	@Override
	public void load(CompoundTag data) {
		super.load(data);
		this.itemHandler.deserializeNBT(data.getCompound("inventory"));
		this.twistingProgress = data.getFloat("progress");
		this.stage = CraftingStage.valueOf(data.getString("stage"));
	}

	@Override
	public void saveAdditional(CompoundTag data) {
		data.put("inventory", itemHandler.serializeNBT());
		data.putFloat("progress", this.twistingProgress);
		data.putString("stage", this.stage.name());
	}
	//endregion

	public enum CraftingStage{
		ADD_MATERIAL,
		TWIST
	}
}