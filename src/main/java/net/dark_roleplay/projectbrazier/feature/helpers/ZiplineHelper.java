package net.dark_roleplay.projectbrazier.feature.helpers;

import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature.entities.ZiplineEntity;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ZiplineHelper {
	public static void startZipline(Entity entity, Level world, Vector3f start, Vector3f end, Vector3f mid){
		if(world.isClientSide) return;

		ZiplineEntity ziplineEntity = new ZiplineEntity(BrazierEntities.ZIPLINE.get(), world, start, end, mid);

		((ServerLevel) world).addFreshEntity(ziplineEntity);
		entity.startRiding(ziplineEntity);
	}
}
