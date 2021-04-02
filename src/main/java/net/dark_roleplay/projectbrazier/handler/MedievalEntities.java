package net.dark_roleplay.projectbrazier.handler;

import net.dark_roleplay.projectbrazier.experiments.immersive_screen.CameraEntity;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class MedievalEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = MedievalRegistries.ENTITIES;

	public static final RegistryObject<EntityType<CameraEntity>> CAMERA =
			ENTITIES.register("camera",
					() -> EntityType.Builder.create(CameraEntity::new, EntityClassification.MISC)
							.disableSummoning()
							.setShouldReceiveVelocityUpdates(false)
							.size(0.5F, 0.001F)
							.build("camera")
			);

	public static final RegistryObject<EntityType<SittableEntity>> SITTABLE =
			ENTITIES.register("sittable",
					() -> EntityType.Builder.<SittableEntity>create(SittableEntity::new, EntityClassification.MISC)
							.disableSummoning()
							.setShouldReceiveVelocityUpdates(false)
							.size(0.5F, 0.001F)
							.build("sittable")
			);
}
