package net.dark_roleplay.medieval.testing.blocks.spinning_wheel;

public class SpinningWheelTileEntity{}/*  extends TileEntity{

	protected ItemStackHandler inventory = new ItemStackHandler(4) {
	    @Override
	    public int getSlotLimit(int slot){
	        return 1;
	    }
		
		@Override
	    protected void onContentsChanged(int slot){
			SpinningWheelTileEntity.this.markDirty();
	    }
	};; 
	
	public SpinningWheelTileEntity() {
	}
	
	@Override
    public void readFromNBT(CompoundNBT compound){
        super.readFromNBT(compound);
        if(compound.hasKey("inventory"))
        	inventory.deserializeNBT((CompoundNBT) compound.getTag("inventory"));   
    }

    public CompoundNBT writeToNBT(CompoundNBT compound) {
        CompoundNBT comp = super.writeToNBT(compound);
        
        CompoundNBT inventory = this.inventory.serializeNBT();
        comp.setTag("inventory", inventory);
        
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
			return (T) this.inventory;
    	return super.getCapability(capability, facing);
	}
}*/
