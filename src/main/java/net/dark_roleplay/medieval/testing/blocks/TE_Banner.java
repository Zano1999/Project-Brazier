package net.dark_roleplay.medieval.testing.blocks;

public class TE_Banner{}/*  extends TileEntity{

	private ImageHelper image;

	public TE_Banner() {}

	public TE_Banner(ImageHelper image) {
		this.image = image;
	}

	public void setImage(ImageHelper image) {
		this.image = image;
	}

	public ImageHelper getImage() {
		return this.image;
	}

	@Override
	public void readFromNBT(CompoundNBT compound){
        super.readFromNBT(compound);
        if(compound.hasKey("image")){
        	this.image = ImageHelper.read((CompoundNBT) compound.getTag("image"));
        }
    }

	@Override
    public CompoundNBT writeToNBT(CompoundNBT compound){
    	compound = super.writeToNBT(compound);
    	if(this.image != null)
    		compound.setTag("image", this.image.write());
    	return compound;
    }

	@Override
	public CompoundNBT getUpdateTag() {
		return super.getUpdateTag();
	}

	@Override
	public void handleUpdateTag(CompoundNBT compound) {
		super.handleUpdateTag(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
	    if(this.image == null) return null;
	    return new SPacketUpdateTileEntity(this.getPos(), 1, this.image.write());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
	    if(pkt == null) return;
	    this.image = ImageHelper.read(pkt.getNbtCompound());
	}
}*/
