package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.util.sitting.SittableEntity;
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

	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {}
}
