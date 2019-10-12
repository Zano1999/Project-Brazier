package net.dark_roleplay.medieval.handler_2;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.util.sitting.EntitySittable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.http.client.entity.EntityBuilder;

public class MedievalEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister(ForgeRegistries.ENTITIES, DarkRoleplayMedieval.MODID);

    public static final RegistryObject<EntityType<?>> SITTABLE =
            ENTITIES.register("sittable",
                    () -> Builder.create(EntitySittable::new, EntityClassification.MISC)
                            .disableSummoning()
                            .disableSerialization()
                            .setShouldReceiveVelocityUpdates(false)
                            .size(0.001F, 0.001F)
                            .build("sittable")
                    );

}
