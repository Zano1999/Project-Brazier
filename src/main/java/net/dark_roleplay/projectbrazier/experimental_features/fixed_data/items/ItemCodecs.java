package net.dark_roleplay.projectbrazier.experimental_features.fixed_data.items;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.CodecUtil;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.feature.items.PlantSeedsItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ItemCodecs {

	public static final MapCodec<PlantSeedsItem> PLANT_SEED_ITEM = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceLocation.CODEC.xmap(ForgeRegistries.BLOCKS::getValue, Block::getRegistryName).fieldOf("plant").forGetter(i -> i.getBlock()),
			CodecUtil.ITEM_PROPERTIES.forGetter(i -> new Item.Properties())
	).apply(instance, PlantSeedsItem::new));

	public static final MapCodec<SelectiveBlockItem> SELECTIVE_BLOCK_ITEM = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceLocation.CODEC.xmap(ForgeRegistries.BLOCKS::getValue, Block::getRegistryName).listOf().fieldOf("blocks").forGetter(i -> new ArrayList <>()),
			CodecUtil.ITEM_PROPERTIES.forGetter(i -> new Item.Properties())
	).apply(instance, SelectiveBlockItem::new));
}
