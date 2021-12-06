package net.dark_roleplay.projectbrazier.experimental_features.drawbridges;

import net.dark_roleplay.projectbrazier.experimental_features.collisions.CollisionListener;
import net.dark_roleplay.projectbrazier.feature.packets.SyncDrawbridgeState;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.dark_roleplay.projectbrazier.util.blocks.VoxelShapeHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;

public class DrawbridgeAnchorTileEntity extends BlockEntity implements ITickableTileEntity {

	public DrawbridgeAnchorTileEntity(){
		super(BrazierBlockEntities.DRAWBRODGE_ANCHOR.get());
	}

	public DrawbridgeAnchorTileEntity(Direction facing) {
		this();
		this.facing = facing;
	}

	private int width = 5, height = 8;
	private float prevAngle = 0, angle = 0;
	private State movementState = State.STOPPED;
	private Direction facing;

	@Override
	public CompoundTag save(CompoundTag compound) {
		compound = super.save(compound);

		CompoundTag bridgeComp = new CompoundTag();
		bridgeComp.putInt("width", width);
		bridgeComp.putInt("height", height);
		bridgeComp.putFloat("angle", angle);
		bridgeComp.putInt("state", movementState.getID());
		bridgeComp.putInt("facing", facing.get3DDataValue());
		compound.put("bridgeData", bridgeComp);

		return compound;
	}

	@Override
	public void load(BlockState state, CompoundTag compound) {
		super.load(state, compound);

		CompoundTag bridgeComp = compound.getCompound("bridgeData");
		width = bridgeComp.getInt("width");
		height = bridgeComp.getInt("height");
		angle = bridgeComp.getInt("angle");
		facing = Direction.from3DDataValue(bridgeComp.getInt("facing"));
		prevAngle = angle;
		movementState = State.getFromID(bridgeComp.getInt("state"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public AABB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public CompoundTag getUpdateTag() {
		return save(new CompoundTag());
	}

	public void startLowering() {
		this.setMovementState(State.LOWERING);
	}

	public void stopMovement() {
		this.setMovementState(State.STOPPED);
	}

	public void startRaising() {
		this.setMovementState(State.RAISING);
	}

	public void setMovementState(State state) {
		if (state == this.movementState) return;
		this.movementState = state;
		SyncDrawbridgeState pkt = new SyncDrawbridgeState(this);

		if (state == State.STOPPED)
			addCollisionBox();
		else
			removeCollisionBox();

		if (!this.getLevel().isClientSide)
			for (Player player : this.getLevel().players()) {
				BrazierPackets.CHANNEL.sendTo(pkt, ((ServerPlayer) player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
			}
	}

	public void setAngle(float angle) {
		this.prevAngle = angle;
		this.angle = angle;
	}

	public float getAngle() {
		return this.angle;
	}

	public float getPrevAngle() {
		return this.prevAngle;
	}

	public State getMovementState() {
		return this.movementState;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		removeCollisionBox();
	}

	@Override
	public void onChunkUnloaded() {
		removeCollisionBox();
	}

	@Override
	public void onLoad() {
		super.onLoad();
		addCollisionBox();
	}


	private void addCollisionBox() {
		if (angle < 1) {
			CollisionListener.addCollision(this,
					VoxelShapeHelper.rotateShape(
							Shapes.box(1, 0.0625 * 10.5F, +1, width + 1, 1 - 0.09375, -height + 1), facing)
							.move(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ()
							));
		} else if (angle >= 1) {
			CollisionListener.addCollision(this,
					VoxelShapeHelper.rotateShape(
							Shapes.box(1, 0, 1 - 0.34375, width + 1, height, 1), facing)
							.move(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ()
							));
		}
	}

	private void removeCollisionBox() {
		CollisionListener.removeCollision(this);
	}

	@Override
	public void tick() {
		if (movementState != State.STOPPED) {
			prevAngle = angle;
			float tmpAngle = angle + (movementState == State.RAISING ? 1 : -1);
			angle = Math.max(0, Math.min(90, tmpAngle));
			if (tmpAngle <= 0 || tmpAngle >= 90)
				stopMovement();
		}
	}

	public enum State {
		RAISING(0),
		LOWERING(1),
		STOPPED(2);

		private final int id;

		State(int id) {
			this.id = id;
		}

		public int getID() {
			return this.id;
		}

		public static State getFromID(int id) {
			switch (id) {
				case 0:
					return RAISING;
				case 1:
					return LOWERING;
				default:
					return STOPPED;
			}
		}
	}
}
