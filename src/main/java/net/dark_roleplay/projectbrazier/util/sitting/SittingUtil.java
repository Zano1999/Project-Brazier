package net.dark_roleplay.projectbrazier.util.sitting;

import net.dark_roleplay.projectbrazier.handler.MedievalEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class SittingUtil {

	public static boolean sitOnBlock(ServerWorld world, double x, double y, double z, PlayerEntity player, double offset){
		if (!checkForExistingEntity(world, x, y, z, player) && !world.isRemote){
			SittableEntity chairEntity = new SittableEntity(MedievalEntities.SITTABLE.get(), world, x, y, z, offset);
			world.addEntity(chairEntity);
			player.startRiding(chairEntity);
		}
		
		return true;
	}

	public static boolean sitOnBlockWithRotation(ServerWorld world, double x, double y, double z, PlayerEntity player, Direction facing, double offset){
		if (!checkForExistingEntity(world, x, y, z, player) && !world.isRemote){
			SittableEntity chairEntity = new SittableEntity(MedievalEntities.SITTABLE.get(), world, x, y, z, offset, facing);
			world.summonEntity(chairEntity);
			player.startRiding(chairEntity);
		}
		return true;
	}

	public static boolean checkForExistingEntity(World world, double x, double y, double z, PlayerEntity player){
		List<SittableEntity> listEMB = world.getEntitiesWithinAABB(SittableEntity.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
		for (SittableEntity mount : listEMB){
			if (mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z){
				if (!mount.isBeingRidden()){
					player.startRiding(mount);
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSomeoneSitting(World world, double x, double y, double z){
		List<SittableEntity> listEMB = world.getEntitiesWithinAABB(SittableEntity.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
		for (SittableEntity mount : listEMB){
			if (mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z){
				return mount.isBeingRidden();
			}
		}
		return false;
	}
}
