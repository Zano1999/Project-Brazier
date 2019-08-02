package net.dark_roleplay.medieval.testing.blocks;

public class TE_StorageArea{}/*  extends TileEntity {

	private MultiStackHandler inventoryMain = new MultiStackHandler(4) {
		@Override
		protected void onContentsChanged(int slot) {
			TE_StorageArea.this.markDirty();
	        BlockState state = TE_StorageArea.this.world.getBlockState(TE_StorageArea.this.getPos());
			TE_StorageArea.this.world.notifyBlockUpdate(TE_StorageArea.this.getPos(), state, state, 2);
		}
	};

	@Override
    public void readFromNBT(CompoundNBT compound){
        super.readFromNBT(compound);

//        this.inventoryMain = new ItemStackHandler() {
//			@Override
//		    protected void onContentsChanged(int slot){
//				DynamicStorageTileEntity.this.markDirty();
//		    }
//		};

        if(compound.hasKey("inventoryMain"))
        	this.inventoryMain.deserializeNBT((CompoundNBT) compound.getTag("inventoryMain"));
    }

    @Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
        CompoundNBT comp = super.writeToNBT(compound);

        CompoundNBT inventory = this.inventoryMain.serializeNBT();
        comp.setTag("inventoryMain", inventory);

        return comp;
    }

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable Direction facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
    	return super.hasCapability(capability, facing);
	}

	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable Direction facing){
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.inventoryMain;
    	return super.getCapability(capability, facing);
	}
}*/
