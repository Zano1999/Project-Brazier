package net.dark_roleplay.projectbrazier.experimental_features.zipline;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierEntities;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ZiplineHelper {
	public static void startZipline(Entity entity, World world, Vector3d start, Vector3d end, Vector3d mid){
		if(world.isRemote()) return;

		ZiplineEntity ziplineEntity = new ZiplineEntity(BrazierEntities.ZIPLINE.get(), world, start, end, mid);

		((ServerWorld) world).summonEntity(ziplineEntity);
		entity.startRiding(ziplineEntity);
	}
}
