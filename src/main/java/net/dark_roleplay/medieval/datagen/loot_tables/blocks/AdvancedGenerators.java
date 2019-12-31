package net.dark_roleplay.medieval.datagen.loot_tables.blocks;

import net.dark_roleplay.medieval.handler.MedievalBlocks;
import net.dark_roleplay.medieval.handler.MedievalItems;
import net.dark_roleplay.medieval.objects.blocks.decoration.light_sources.TorchHolderBlock;
import net.dark_roleplay.medieval.objects.enums.TorchHolderEnums;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.state.IProperty;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.Alternative;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class AdvancedGenerators {

    public static LootTable.Builder createTorchHolderPool(){
        LootTable.Builder tableBuilder = LootTable.builder();

        Block b = MedievalBlocks.TORCH_HOLDER.get();

        tableBuilder.addLootPool(
            LootPool.builder()
                .name(b.getRegistryName().getPath())
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(b))
        ).addLootPool(
            LootPool.builder()
            .name("torch")
            .rolls(ConstantRange.of(1))
            .addEntry(
                ItemLootEntry.builder(Items.TORCH).acceptCondition(
                    altStateCond(b, TorchHolderBlock.TORCH, TorchHolderEnums.Torch.UNLIT, TorchHolderEnums.Torch.LIT)
                )
            )
        ).addLootPool(
            LootPool.builder()
                .name("addons")
                .rolls(ConstantRange.of(1))
                .addEntry(
                    AlternativesLootEntry.func_216149_a(
                        ItemLootEntry.builder(Items.LEVER).acceptCondition(
                            altStateCond(b, TorchHolderBlock.ADDONS, TorchHolderEnums.Addons.HIDDEN_LEVER, TorchHolderEnums.Addons.PULLED_HIDDEN_LEVER)
                        ),
                        ItemLootEntry.builder(MedievalItems.TRIGGER_TRAP.get()).acceptCondition(
                            altStateCond(b, TorchHolderBlock.ADDONS, TorchHolderEnums.Addons.LEVER, TorchHolderEnums.Addons.PULLED_LEVER)
                        ),
                        ItemLootEntry.builder(Items.FLINT).acceptCondition(
                            BlockStateProperty.builder(b).with(TorchHolderBlock.ADDONS, TorchHolderEnums.Addons.LIGHTER)
                        )
                    )
                )
        );

        return tableBuilder;
    }

    public static <T extends Comparable<T>> ILootCondition.IBuilder altStateCond(Block block, IProperty<T> propertyIn, T... values) {
        Alternative.Builder altCond = Alternative.builder();

        for(T value : values){
            altCond.alternative(
                BlockStateProperty.builder(block).with(propertyIn, value)
            );
        }

        return altCond;
    }
}
