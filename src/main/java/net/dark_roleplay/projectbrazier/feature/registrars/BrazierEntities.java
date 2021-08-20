package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.experimental_features.immersive_screen.CameraEntity;
import net.dark_roleplay.projectbrazier.experimental_features.zipline.ZiplineEntity;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BrazierEntities extends Registrar {

	public static final RegistryObject<EntityType<CameraEntity>> CAMERA =
			registerEntity("camera", () -> EntityType.Builder.create(CameraEntity::new, EntityClassification.MISC)
					.disableSummoning()
					.setShouldReceiveVelocityUpdates(false)
					.size(0.5F, 0.001F)
					.build("camera"));

	public static final RegistryObject<EntityType<SittableEntity>> SITTABLE =
			registerEntity("sittable",
					() -> EntityType.Builder.<SittableEntity>create(SittableEntity::new, EntityClassification.MISC)
							.disableSummoning()
							.setShouldReceiveVelocityUpdates(false)
							.size(0.5F, 0.001F)
							.build("sittable")
			);

	public static final RegistryObject<EntityType<ZiplineEntity>> ZIPLINE =
			registerEntity("zipline",
					() -> EntityType.Builder.<ZiplineEntity>create(ZiplineEntity::new, EntityClassification.MISC)
							.disableSummoning()
							.size(0.5F, 0.5F)
							.build("zipline")
			);

	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {}
}
