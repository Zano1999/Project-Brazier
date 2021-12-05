package net.dark_roleplay.projectbrazier.util.sitting;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierEntities;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class SittingUtil {

	public static boolean sitOnBlock(Level world, Vec3i pos, Entity entity, double heightOffset, BlockState state){
		return sitDownEntity(world, new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), entity, null, null, heightOffset, state);
	}

	public static boolean sitOnBlockWithRotation(Level world, Vec3i pos, Entity entity, Direction facing, double heightOffset, BlockState state){
		return sitDownEntity(world, new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), entity, facing, null, heightOffset, state);
	}

	public static boolean sitOnBlock(Level world, Vec3i pos, Entity entity, Direction initFacing, double heightOffset, BlockState state){
		return sitDownEntity(world, new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), entity, null, initFacing, heightOffset, state);
	}

	public static boolean sitOnBlockWithRotation(Level world, Vec3i pos, Entity entity, Direction facing, Direction initFacing, double heightOffset, BlockState state){
		return sitDownEntity(world, new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), entity, facing, initFacing, heightOffset, state);
	}

	public static boolean sitDownEntity(Level world, Vec3 pos, Entity entity, Direction facing, Direction initFacing, double heightOffset, BlockState state){
		if(world.isClientSide()) return false;

		if(state != null && entity instanceof Player){
			if(entity.position().distanceToSqr(new Vec3(pos.x(), pos.y(), pos.z())) > 9){
				((Player)entity).displayClientMessage(new TranslatableComponent("interaction.projectbrazier.chair_to_far", state.getBlock().getName()), true);
				return false;
			}

			if(isSomeoneSitting(world, pos)){
				((Player)entity).displayClientMessage(new TranslatableComponent("interaction.projectbrazier.chair_occupied", state.getBlock().getName()), true);
				return false;
			}
		}

		SittableEntity chairEntity = new SittableEntity(BrazierEntities.SITTABLE.get(), world, pos.x(), pos.y(), pos.z(), heightOffset, state != null);

		if(facing != null)
			chairEntity.setRotation(facing);

		if(initFacing != null){
			entity.yRotO = initFacing.toYRot();
			entity.setYRot(initFacing.toYRot());
		}

		((ServerLevel) world).addWithUUID(chairEntity);
		entity.startRiding(chairEntity);

		return true;
	}
	
	public static boolean isSomeoneSitting(Level world, Vec3 pos){
		List<SittableEntity> listEMB = world.getEntitiesOfClass(SittableEntity.class, new AABB((int)pos.x(), (int)pos.y(), (int)pos.z(), (int)pos.x() + 1.0D, (int)pos.y() + 1.0D, (int)pos.z() + 1.0D));
		for (SittableEntity mount : listEMB){
			return mount.isVehicle();
		}
		return false;
	}
}
