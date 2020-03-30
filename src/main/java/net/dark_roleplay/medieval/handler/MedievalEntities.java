package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.util.sitting.EntitySittable;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MedievalEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister(ForgeRegistries.ENTITIES, DarkRoleplayMedieval.MODID);

    public static final RegistryObject<EntityType<?>> SITTABLE =
            ENTITIES.register("sittable",
                    () -> Builder.create(EntitySittable::new, EntityClassification.MISC)
                            .disableSummoning()
                            .disableSerialization()
                            .setShouldReceiveVelocityUpdates(false)
                            .size(1F, 2F)
                            .build("sittable")
            );

//    public static final RegistryObject<EntityType<?>> TRAINING_DUMMY =
//            ENTITIES.register("training_dummy",
//                    () -> Builder.create(TrainingDummyEntity::new, EntityClassification.AMBIENT)
//                            .disableSerialization()
//                            .setShouldReceiveVelocityUpdates(false)
//                            .size(0.001F, 0.001F)
//                            .build("training_dummy")
//            );
}
