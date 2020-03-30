package net.dark_roleplay.medieval.datagen.loot_tables;

import net.dark_roleplay.medieval.datagen.loot_tables.blocks.AdvancedGenerators;
import net.dark_roleplay.medieval.handler.MedievalBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.functions.CopyName;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MedievalLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> lta) {
        lta.accept(MedievalBlocks.ANDESITE_PILLAR.get().getLootTable(), basic(MedievalBlocks.ANDESITE_PILLAR));
        lta.accept(MedievalBlocks.ANDESITE_BRICKS.get().getLootTable(), basic(MedievalBlocks.ANDESITE_BRICKS));
        lta.accept(MedievalBlocks.DIORITE_PILLAR.get().getLootTable(), basic(MedievalBlocks.DIORITE_PILLAR));
        lta.accept(MedievalBlocks.DIORITE_BRICKS.get().getLootTable(), basic(MedievalBlocks.DIORITE_BRICKS));
        lta.accept(MedievalBlocks.GRANITE_PILLAR.get().getLootTable(), basic(MedievalBlocks.GRANITE_PILLAR));
        lta.accept(MedievalBlocks.GRANITE_BRICKS.get().getLootTable(), basic(MedievalBlocks.GRANITE_BRICKS));
        lta.accept(MedievalBlocks.SNOW_BRICKS.get().getLootTable(), basic(MedievalBlocks.SNOW_BRICKS));
        lta.accept(MedievalBlocks.PACKED_ICE_BRICKS.get().getLootTable(), basic(MedievalBlocks.PACKED_ICE_BRICKS));
        lta.accept(MedievalBlocks.TORCH_HOLDER.get().getLootTable(), AdvancedGenerators.createTorchHolderPool());
    }

    protected LootTable.Builder basic(RegistryObject<Block> blockReg) {
        Block b = blockReg.get();
        LootPool.Builder builder = LootPool.builder()
                .name("main")
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(b));

        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder keepInv(String name, RegistryObject<Block> blockReg) {
        Block b = blockReg.get();
        LootPool.Builder builder = LootPool.builder()
                .name(name)
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(b))
                .acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY))
                .acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
                    .addOperation("inv", "BlockEntityTag.inv", CopyNbt.Action.REPLACE)
                    .addOperation("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE)
                );

        return LootTable.builder().addLootPool(builder);
    }


}