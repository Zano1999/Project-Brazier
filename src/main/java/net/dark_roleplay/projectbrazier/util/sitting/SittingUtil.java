package net.dark_roleplay.projectbrazier.util.sitting;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class SittingUtil {

	public static boolean sitOnBlock(World world, Vector3i pos, Entity entity, double heightOffset, BlockState state){
		return sitDownEntity(world, new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), entity, null, null, heightOffset, state);
	}

	public static boolean sitOnBlockWithRotation(World world, Vector3i pos, Entity entity, Direction facing, double heightOffset, BlockState state){
		return sitDownEntity(world, new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), entity, facing, null, heightOffset, state);
	}

	public static boolean sitOnBlock(World world, Vector3i pos, Entity entity, Direction initFacing, double heightOffset, BlockState state){
		return sitDownEntity(world, new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), entity, null, initFacing, heightOffset, state);
	}

	public static boolean sitOnBlockWithRotation(World world, Vector3i pos, Entity entity, Direction facing, Direction initFacing, double heightOffset, BlockState state){
		return sitDownEntity(world, new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), entity, facing, initFacing, heightOffset, state);
	}

	public static boolean sitDownEntity(World world, Vector3d pos, Entity entity, Direction facing, Direction initFacing, double heightOffset, BlockState state){
		if(world.isClientSide()) return false;

		if(state != null && entity instanceof PlayerEntity){
			if(entity.position().distanceToSqr(new Vector3d(pos.x(), pos.y(), pos.z())) > 9){
				((PlayerEntity)entity).displayClientMessage(new TranslationTextComponent("interaction.projectbrazier.chair_to_far", state.getBlock().getName()), true);
				return false;
			}

			if(isSomeoneSitting(world, pos)){
				((PlayerEntity)entity).displayClientMessage(new TranslationTextComponent("interaction.projectbrazier.chair_occupied", state.getBlock().getName()), true);
				return false;
			}
		}

		SittableEntity chairEntity = new SittableEntity(BrazierEntities.SITTABLE.get(), world, pos.x(), pos.y(), pos.z(), heightOffset, state != null);

		if(facing != null)
			chairEntity.setRotation(facing);

		if(initFacing != null){
			entity.yRotO = initFacing.toYRot();
			entity.yRot = initFacing.toYRot();
		}

		((ServerWorld) world).addWithUUID(chairEntity);
		entity.startRiding(chairEntity);

		return true;
	}
	
	public static boolean isSomeoneSitting(World world, Vector3d pos){
		List<SittableEntity> listEMB = world.getEntitiesOfClass(SittableEntity.class, new AxisAlignedBB((int)pos.x(), (int)pos.y(), (int)pos.z(), (int)pos.x() + 1.0D, (int)pos.y() + 1.0D, (int)pos.z() + 1.0D));
		for (SittableEntity mount : listEMB){
			return mount.isVehicle();
		}
		return false;
	}
}
