package net.dark_roleplay.medieval.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.medieval.datagen.loot_tables.MedievalLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenListener {
    private static final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> BLOCK_LOOT_TABLES =
            ImmutableList.of(Pair.of(MedievalLootTables::new, LootParameterSets.BLOCK));

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new LootTableProvider(generator){
            @Override
            protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
                return DataGenListener.BLOCK_LOOT_TABLES;
            }


            @Override
            protected void validate(Map<ResourceLocation, LootTable> map, ValidationResults validationresults) {
            }

            @Override
            public String getName() {
                return "Dark Roleplay Medieval - LootTables";
            }
        });
    }
}
