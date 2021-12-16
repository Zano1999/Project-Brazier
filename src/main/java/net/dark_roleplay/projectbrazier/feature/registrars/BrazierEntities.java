package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.feature.entities.ZiplineEntity;
import net.dark_roleplay.projectbrazier.feature.entities.SittableEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BrazierEntities{

//	public static final RegistryObject<EntityType<CameraEntity>> CAMERA =
//			Registrar.registerEntity("camera", () -> EntityType.Builder.of(CameraEntity::new, MobCategory.MISC)
//					.noSummon()
//					.setShouldReceiveVelocityUpdates(false)
//					.sized(0.5F, 0.001F)
//					.build("camera"));

	public static final RegistryObject<EntityType<SittableEntity>> SITTABLE =
			Registrar.registerEntity("sittable",
					() -> EntityType.Builder.<SittableEntity>of(SittableEntity::new, MobCategory.MISC)
							.noSummon()
							.setShouldReceiveVelocityUpdates(false)
							.sized(0.5F, 0.001F)
							.build("sittable")
			);

	public static final RegistryObject<EntityType<ZiplineEntity>> ZIPLINE =
			Registrar.registerEntity("zipline",
					() -> EntityType.Builder.<ZiplineEntity>of(ZiplineEntity::new, MobCategory.MISC)
							.noSummon()
							.setUpdateInterval(10)
							.sized(0.5F, 0.5F)
							.build("zipline")
			);

	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {}
}
