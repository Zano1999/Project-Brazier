package net.dark_roleplay.projectbrazier.experimental_features.zipline;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierEntities;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ZiplineHelper {
	public static void startZipline(Entity entity, World world, Vector3f start, Vector3f end, Vector3f mid){
		if(world.isRemote()) return;

		ZiplineEntity ziplineEntity = new ZiplineEntity(BrazierEntities.ZIPLINE.get(), world, start, end, mid);

		((ServerWorld) world).summonEntity(ziplineEntity);
		entity.startRiding(ziplineEntity);
	}
}
