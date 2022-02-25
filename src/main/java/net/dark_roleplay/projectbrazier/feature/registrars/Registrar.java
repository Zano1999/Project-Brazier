package net.dark_roleplay.projectbrazier.feature.registrars;

import com.mojang.serialization.Codec;
import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.marg.common.material.MaterialCondition;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.ItemPropertyLoader;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.MedievalProperties;
import net.dark_roleplay.projectbrazier.util.marg.EnumMaterialRegistryObject;
import net.dark_roleplay.projectbrazier.util.marg.EnumRegistryObject;
import net.dark_roleplay.projectbrazier.util.MaterialRegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Registrar {

	//region Blocks
	public static <T extends Block> RegistryObject<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties props, boolean registerItem) {
		return (registerItem ?
				BrazierRegistries.BLOCKS :
				BrazierRegistries.BLOCKS_NO_ITEMS)
				.register(name, () -> factory.apply(props));
	}

	public static <T extends Enum<T>, B extends Block> EnumMaterialRegistryObject<T, B> registerBlock(String name, Class<T> clazz, MaterialCondition condition, Function<BlockBehaviour.Properties, B> supplier, Function<MargMaterial, BlockBehaviour.Properties> propertyFactory, boolean registerItem) {
		EnumMaterialRegistryObject<T, B> regObj = new EnumMaterialRegistryObject<>(clazz);
		for (T type : clazz.getEnumConstants()) {
			String enumizedName = "";
			if(type instanceof IFancyNamer)
				enumizedName = ((IFancyNamer) type).processFancyName(name);
			else
				enumizedName = String.format(name, type.name().toLowerCase(Locale.ROOT));
			for (MargMaterial material : condition) {
				regObj.register(type, material, registerBlock(material.getTextProvider().apply(enumizedName), supplier, propertyFactory.apply(material),registerItem));
			}
		}
		return regObj;
	}

	public static <T extends Enum<T>, B extends Block> EnumRegistryObject<T, B> registerBlock(String name, Class<T> clazz, Function<BlockBehaviour.Properties, B> supplier, BlockBehaviour.Properties props, boolean registerItem) {
		return registerBlock(name, clazz, type -> true, supplier, props, registerItem);
	}

	public static <T extends Enum<T>, B extends Block> EnumRegistryObject<T, B> registerBlock(String name, Class<T> clazz, Predicate<T> filter, Function<BlockBehaviour.Properties, B> supplier, BlockBehaviour.Properties props, boolean registerItem) {
		EnumRegistryObject<T, B> regObj = new EnumRegistryObject<>(clazz);
		for (T type : clazz.getEnumConstants())
			if(filter.test(type))
				if(type instanceof IFancyNamer)
					regObj.register(type, registerBlock(((IFancyNamer) type).processFancyName(name), supplier, props, registerItem));
				else
					regObj.register(type, registerBlock(String.format(name, type.name().toLowerCase(Locale.ROOT)), supplier, props, registerItem));

		return regObj;
	}

	public static <T extends Block> MaterialRegistryObject<T> registerBlock(String name, MaterialCondition condition, Function<BlockBehaviour.Properties, T> factory, Function<MargMaterial, BlockBehaviour.Properties> propertyFactory, boolean createItem) {
		return registerBlock(name, condition, (material, props) -> factory.apply(props), propertyFactory, createItem);
	}

	public static <T extends Block> MaterialRegistryObject<T> registerBlock(String name, MaterialCondition condition, BiFunction<MargMaterial, BlockBehaviour.Properties, T> factory, Function<MargMaterial, BlockBehaviour.Properties> propertyFactory, boolean createItem) {
		MaterialRegistryObject<T> blocks = new MaterialRegistryObject();

		for (MargMaterial material : condition) {
			String materializedName = material.getTextProvider().apply(name);
			blocks.register(material, (createItem ?
					BrazierRegistries.BLOCKS :
					BrazierRegistries.BLOCKS_NO_ITEMS)
					.register(materializedName, () -> factory.apply(material, propertyFactory.apply(material))));
		}

		return blocks;
	}
	//endregion

	//region Items
	public static RegistryObject<Item> registerItem(String name) {
		return registerItem(name, Item::new);
	}


	public static <T extends Item> RegistryObject<T> registerItem(String name, Function<Item.Properties, T> supplier) {
		return BrazierRegistries.ITEMS.register(name, () -> supplier.apply(ItemPropertyLoader.getProp(name)));
	}
	//endregion

	//region Containers

	public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerContainer(String name, IContainerFactory<T> factory) {
		return BrazierRegistries.CONTAINERS.register(name, () -> new MenuType<>(factory));
	}

	//endregion

	//region TileEntityTypes
	public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> supplier, RegistryObject<Block>... blocks) {
		return BrazierRegistries.BLOCK_ENTITIES.register(name, () -> createType(supplier, blocks));
	}

	public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Collection<RegistryObject<Block>>... blocks) {
		return BrazierRegistries.BLOCK_ENTITIES.register(name, () -> createType(supplier, blocks));
	}

	private static <T extends BlockEntity> BlockEntityType<T> createType(BlockEntityType.BlockEntitySupplier<T> supplier, RegistryObject<Block>... blocks) {
		return BlockEntityType.Builder.of(supplier, Arrays.stream(blocks).map(ro -> ro.get()).collect(Collectors.toList()).toArray(new Block[blocks.length])).build(null);
	}

	private static <T extends BlockEntity> BlockEntityType<T> createType(BlockEntityType.BlockEntitySupplier<T> supplier, Collection<RegistryObject<Block>>... blocks) {
		return BlockEntityType.Builder.of(supplier, Arrays.stream(blocks).flatMap(col -> col.stream()).map(RegistryObject::get).toArray(i -> new Block[i])).build(null);
	}
	//endregion

	//region Entities

	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, Supplier<EntityType<T>> supplier) {
		return BrazierRegistries.ENTITIES.register(name, supplier);
	}

	//endregion

	//region AbstractBlock.Properties
	public static Block.Properties MARG_WOOD(MargMaterial material) {
		return BlockBehaviour.Properties.of(Material.WOOD, material.getProperties().getMapColor())
				.strength(2.0F, 3.0F)
				.sound(SoundType.WOOD).noOcclusion();
	}

	public static final BlockBehaviour.Properties WOOD =
			BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD)
					.strength(2.0F, 3.0F)
					.sound(SoundType.WOOD)
					.noOcclusion();

	public static final BlockBehaviour.Properties WOOD_SOLID =
			BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD)
					.strength(2.0F, 3.0F)
					.sound(SoundType.WOOD);

	public static BlockBehaviour.Properties METAL =
			BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL)
					.strength(5.0F, 1200.0F)
					.sound(SoundType.METAL)
					.noOcclusion();

	public static BlockBehaviour.Properties METAL_BRAZIER =
			BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL)
					.strength(5.0F, 1200.0F)
					.sound(SoundType.METAL)
					.lightLevel(state -> state.getValue(MedievalProperties.BURNING) ? 15 : 0)
					.noOcclusion();

	public static BlockBehaviour.Properties METAL_SOUL_BRAZIER =
			BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL)
					.strength(5.0F, 1200.0F)
					.sound(SoundType.METAL)
					.lightLevel(state -> state.getValue(MedievalProperties.BURNING) ? 10 : 0)
					.noOcclusion();


	public static BlockBehaviour.Properties METAL_GLOW =
			BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL)
					.strength(5.0F, 1200.0F)
					.sound(SoundType.METAL)
					.lightLevel(state -> 15)
					.noOcclusion();

	public static BlockBehaviour.Properties METAL_SOLID =
			BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL)
					.strength(5.0F, 1200.0F)
					.sound(SoundType.METAL);

	public static BlockBehaviour.Properties STONE =
			BlockBehaviour.Properties.
					of(Material.STONE, MaterialColor.STONE)
					.strength(1.5F, 6.0F)
					.sound(SoundType.STONE)
					.noOcclusion();

	public static BlockBehaviour.Properties STONE_SOLID =
			BlockBehaviour.Properties.
					of(Material.STONE, MaterialColor.STONE)
					.strength(1.5F, 6.0F)
					.sound(SoundType.STONE);

	public static final BlockBehaviour.Properties SNOW_SOLID =
			Block.Properties.of(Material.TOP_SNOW)
					.strength(0.3F)
					.sound(SoundType.SNOW);

	public static BlockBehaviour.Properties PLANT_FUNGI =
			BlockBehaviour.Properties.
					of(Material.PLANT, MaterialColor.WOOD)
					.strength(1.5F, 6.0F)
					.sound(SoundType.GRASS)
					.noOcclusion();

	public static BlockBehaviour.Properties CROP =
			BlockBehaviour.Properties
					.of(Material.PLANT)
					.noCollission()
					.randomTicks()
					.instabreak()
					.sound(SoundType.CROP);

	public static BlockBehaviour.Properties ROPE =
			BlockBehaviour.Properties
					.of(Material.WOOL, MaterialColor.SNOW)
					.strength(0.8F)
					.sound(SoundType.WOOL);
	//endregion

	//region worldgen
	public static <R extends FeatureConfiguration, T extends Feature<R>> RegistryObject<Feature<R>> registerFeature(String name, Supplier<Feature<R>> supplier) {
		return BrazierRegistries.FEATURES.register(name, supplier);
	}

	private static <P extends PlacementModifier> PlacementModifierType<P> register(ResourceLocation resourceLocation, Codec<P> codec) {
		return Registry.register(Registry.PLACEMENT_MODIFIERS, resourceLocation, () -> codec);
	}
	//endregion
}