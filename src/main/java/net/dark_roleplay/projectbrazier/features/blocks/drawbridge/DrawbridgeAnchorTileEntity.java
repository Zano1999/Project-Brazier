package net.dark_roleplay.projectbrazier.features.blocks.drawbridge;

import net.dark_roleplay.projectbrazier.features.packets.SyncDrawbridgeState;
import net.dark_roleplay.projectbrazier.handler.MedievalNetworking;
import net.dark_roleplay.projectbrazier.handler.MedievalTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkHooks;

public class DrawbridgeAnchorTileEntity extends TileEntity implements ITickableTileEntity {

	public DrawbridgeAnchorTileEntity() {
		super(MedievalTileEntities.DRAWBRODGE_ANCHOR.get());
	}

	private int width = 5, height = 8;
	private float prevAngle = 0, angle = 0;
	private State movementState = State.STOPPED;

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound = super.write(compound);

		CompoundNBT bridgeComp = new CompoundNBT();
		bridgeComp.putInt("width", width);
		bridgeComp.putInt("height", height);
		bridgeComp.putFloat("angle", angle);
		bridgeComp.putInt("state", movementState.getID());
		compound.put("bridgeData", bridgeComp);

		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);

		CompoundNBT bridgeComp = compound.getCompound("bridgeData");
		width = bridgeComp.getInt("width");
		height = bridgeComp.getInt("height");
		angle = bridgeComp.getInt("angle");
		prevAngle = angle;
		movementState = State.getFromID(bridgeComp.getInt("state"));
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return write(new CompoundNBT());
	}

	public void startLowering(){
		this.setMovementState(State.LOWERING);
	}

	public void stopMovement(){
		this.setMovementState(State.STOPPED);
	}

	public void startRaising(){
		this.setMovementState(State.RAISING);
	}

	public void setMovementState(State state){
		this.movementState = state;
		SyncDrawbridgeState pkt = new SyncDrawbridgeState(this);

		if(!this.getWorld().isRemote)
			for(PlayerEntity player : this.getWorld().getPlayers()) {
				MedievalNetworking.CHANNEL.sendTo(pkt, ((ServerPlayerEntity)player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
			}

	}

	public void setAngle(float angle){
		this.prevAngle = angle;
		this.angle = angle;
	}

	public float getAngle(){
		return this.angle;
	}

	public float getPrevAngle(){
		return this.prevAngle;
	}

	public State getMovementState(){
		return this.movementState;
	}

	public int getWidth(){
		return this.width;
	}

	public int getHeight(){
		return this.height;
	}

	@Override
	public void tick() {
		if(movementState != State.STOPPED){
			prevAngle = angle;
			float tmpAngle = angle + (movementState == State.RAISING ? 1 : -1);
			angle = Math.max(0, Math.min(90, tmpAngle));
			if(tmpAngle <= 0 || tmpAngle >= 90)
				stopMovement();
		}
	}

	public enum State{
		RAISING(0),
		LOWERING(1),
		STOPPED(2);

		private final int id;
		State(int id){
			this.id = id;
		}

		public int getID(){
			return this.id;
		}

		public static State getFromID(int id){
			switch(id){
				case 0: return RAISING;
				case 1: return LOWERING;
				default: return STOPPED;
			}
		}
	}
}
