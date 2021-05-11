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
		if(world.isRemote()) return false;

		if(state != null && entity instanceof PlayerEntity){
			if(entity.getPositionVec().squareDistanceTo(new Vector3d(pos.getX(), pos.getY(), pos.getZ())) > 9){
				((PlayerEntity)entity).sendStatusMessage(new TranslationTextComponent("interaction.projectbrazier.chair_to_far", state.getBlock().getTranslatedName()), true);
				return false;
			}

			if(isSomeoneSitting(world, pos)){
				((PlayerEntity)entity).sendStatusMessage(new TranslationTextComponent("interaction.projectbrazier.chair_occupied", state.getBlock().getTranslatedName()), true);
				return false;
			}
		}

		SittableEntity chairEntity = new SittableEntity(BrazierEntities.SITTABLE.get(), world, pos.getX(), pos.getY(), pos.getZ(), heightOffset, state != null);

		if(facing != null)
			chairEntity.setRotation(facing);

		if(initFacing != null){
			entity.prevRotationYaw = initFacing.getHorizontalAngle();
			entity.rotationYaw = initFacing.getHorizontalAngle();
		}

		((ServerWorld) world).summonEntity(chairEntity);
		entity.startRiding(chairEntity);

		return true;
	}
	
	public static boolean isSomeoneSitting(World world, Vector3d pos){
		List<SittableEntity> listEMB = world.getEntitiesWithinAABB(SittableEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
		for (SittableEntity mount : listEMB){
			return mount.isBeingRidden();
		}
		return false;
	}
}
