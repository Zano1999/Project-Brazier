package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.util.sitting.EntitySittable;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class MedievalEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = MedievalRegistries.ENTITIES;

	public static final RegistryObject<EntityType<?>> SITTABLE =
			ENTITIES.register("sittable",
					() -> EntityType.Builder.create(EntitySittable::new, EntityClassification.MISC)
							.disableSummoning()
							.disableSerialization()
							.setShouldReceiveVelocityUpdates(false)
							.size(1F, 2F)
							.build("sittable")
			);
}
