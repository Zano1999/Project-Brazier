package net.dark_roleplay.medieval.util.sitting;

import java.util.List;

import net.dark_roleplay.medieval.handler.MedievalEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SittingUtil {

	public static boolean sitOnBlock(ServerWorld world, double x, double y, double z, PlayerEntity player, double offset){
		if (!checkForExistingEntity(world, x, y, z, player) && !world.isRemote){
			EntitySittable chairEntity = new EntitySittable(MedievalEntities.SITTABLE.get(), world, x, y, z, offset);
			world.addEntity(chairEntity);
			player.startRiding(chairEntity);
		}
		
		return true;
	}

	public static boolean sitOnBlockWithRotation(ServerWorld world, double x, double y, double z, PlayerEntity player, Direction facing, double offset){
		if (!checkForExistingEntity(world, x, y, z, player) && !world.isRemote){
			EntitySittable chairEntity = new EntitySittable(MedievalEntities.SITTABLE.get(), world, x, y, z, offset, facing);
			world.summonEntity(chairEntity);
			player.startRiding(chairEntity);
		}
		return true;
	}

	public static boolean checkForExistingEntity(World world, double x, double y, double z, PlayerEntity player){
		List<EntitySittable> listEMB = world.getEntitiesWithinAABB(EntitySittable.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
		for (EntitySittable mount : listEMB){
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
		List<EntitySittable> listEMB = world.getEntitiesWithinAABB(EntitySittable.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
		for (EntitySittable mount : listEMB){
			if (mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z){
				return mount.isBeingRidden();
			}
		}
		return false;
	}
}
