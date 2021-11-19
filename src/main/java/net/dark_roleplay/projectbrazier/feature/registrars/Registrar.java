package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.marg.common.material.MaterialCondition;
import net.dark_roleplay.projectbrazier.experimental_features.data_props.ItemPropertyLoader;
import net.dark_roleplay.projectbrazier.util.EnumMaterialRegistryObject;
import net.dark_roleplay.projectbrazier.util.EnumRegistryObject;
import net.dark_roleplay.projectbrazier.util.MaterialRegistryObject;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;

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
	protected static <T extends Block> RegistryObject<T> registerBlock(String name, Function<AbstractBlock.Properties, T> factory, AbstractBlock.Properties props, boolean registerItem) {
		return (registerItem ?
				BrazierRegistries.BLOCKS :
				BrazierRegistries.BLOCKS_NO_ITEMS)
				.register(name, () -> factory.apply(props));
	}

	protected static <T extends Enum<T>, B extends Block> EnumMaterialRegistryObject<T, B> registerBlock(String name, Class<T> clazz, MaterialCondition condition, Function<AbstractBlock.Properties, B> supplier, Function<MargMaterial, AbstractBlock.Properties> propertyFactory, boolean registerItem) {
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

	protected static <T extends Enum<T>, B extends Block> EnumRegistryObject<T, B> registerBlock(String name, Class<T> clazz, Function<AbstractBlock.Properties, B> supplier, AbstractBlock.Properties props, boolean registerItem) {
		return registerBlock(name, clazz, type -> true, supplier, props, registerItem);
	}

	protected static <T extends Enum<T>, B extends Block> EnumRegistryObject<T, B> registerBlock(String name, Class<T> clazz, Predicate<T> filter, Function<AbstractBlock.Properties, B> supplier, AbstractBlock.Properties props, boolean registerItem) {
		EnumRegistryObject<T, B> regObj = new EnumRegistryObject<>(clazz);
		for (T type : clazz.getEnumConstants())
			if(filter.test(type))
				if(type instanceof IFancyNamer)
					regObj.register(type, registerBlock(((IFancyNamer) type).processFancyName(name), supplier, props, registerItem));
				else
					regObj.register(type, registerBlock(String.format(name, type.name().toLowerCase(Locale.ROOT)), supplier, props, registerItem));

		return regObj;
	}

	protected static <T extends Block> MaterialRegistryObject<T> registerBlock(String name, MaterialCondition condition, Function<AbstractBlock.Properties, T> factory, Function<MargMaterial, AbstractBlock.Properties> propertyFactory, boolean createItem) {
		return registerBlock(name, condition, (material, props) -> factory.apply(props), propertyFactory, createItem);
	}

	protected static <T extends Block> MaterialRegistryObject<T> registerBlock(String name, MaterialCondition condition, BiFunction<MargMaterial, AbstractBlock.Properties, T> factory, Function<MargMaterial, AbstractBlock.Properties> propertyFactory, boolean createItem) {
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
	protected static RegistryObject<Item> registerItem(String name) {
		return registerItem(name, Item::new);
	}

	protected static <T extends Item> RegistryObject<T> registerItem(String name, Function<Item.Properties, T> supplier) {
		return BrazierRegistries.ITEMS.register(name, () -> supplier.apply(ItemPropertyLoader.getProp(name)));
	}
	//endregion

	//region Containers

	protected static <T extends Container> RegistryObject<ContainerType<T>> registerContainer(String name, IContainerFactory<T> factory) {
		return BrazierRegistries.CONTAINERS.register(name, () -> new ContainerType<>(factory));
	}

	//endregion

	//region TileEntityTypes
	protected static <T extends TileEntity> RegistryObject<TileEntityType<T>> registerBlockEntity(String name, Supplier<T> supplier, RegistryObject<Block>... blocks) {
		return BrazierRegistries.BLOCK_ENTITIES.register(name, () -> createType(supplier, blocks));
	}

	protected static <T extends TileEntity> RegistryObject<TileEntityType<T>> registerBlockEntity(String name, Supplier<T> supplier, Collection<RegistryObject<Block>>... blocks) {
		return BrazierRegistries.BLOCK_ENTITIES.register(name, () -> createType(supplier, blocks));
	}

	private static <T extends TileEntity> TileEntityType<T> createType(Supplier<T> supplier, RegistryObject<Block>... blocks) {
		return TileEntityType.Builder.create(supplier, Arrays.stream(blocks).map(ro -> ro.get()).collect(Collectors.toList()).toArray(new Block[blocks.length])).build(null);
	}

	private static <T extends TileEntity> TileEntityType<T> createType(Supplier<T> supplier, Collection<RegistryObject<Block>>... blocks) {
		return TileEntityType.Builder.create(supplier, Arrays.stream(blocks).flatMap(col -> col.stream()).map(RegistryObject::get).toArray(i -> new Block[i])).build(null);
	}
	//endregion

	//region Entities

	protected static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, Supplier<EntityType<T>> supplier) {
		return BrazierRegistries.ENTITIES.register(name, supplier);
	}

	//endregion

	//region AbstractBlock.Properties
	protected static Block.Properties MARG_WOOD(MargMaterial material) {
		return AbstractBlock.Properties.create(Material.WOOD, material.getProperties().getMapColor())
				.hardnessAndResistance(2.0F, 3.0F)
				.sound(SoundType.WOOD).notSolid();
	}

	protected static final AbstractBlock.Properties WOOD =
			AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD)
					.hardnessAndResistance(2.0F, 3.0F)
					.sound(SoundType.WOOD)
					.notSolid();

	protected static final AbstractBlock.Properties WOOD_SOLID =
			AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD)
					.hardnessAndResistance(2.0F, 3.0F)
					.sound(SoundType.WOOD);

	protected static AbstractBlock.Properties METAL =
			AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.IRON)
					.hardnessAndResistance(5.0F, 1200.0F)
					.sound(SoundType.METAL)
					.notSolid();

	protected static AbstractBlock.Properties METAL_GLOW =
			AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.IRON)
					.hardnessAndResistance(5.0F, 1200.0F)
					.sound(SoundType.METAL)
					.setLightLevel(state -> 15)
					.notSolid();

	protected static AbstractBlock.Properties METAL_SOLID =
			AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.IRON)
					.hardnessAndResistance(5.0F, 1200.0F)
					.sound(SoundType.METAL);

	protected static AbstractBlock.Properties STONE =
			AbstractBlock.Properties.
					create(Material.ROCK, MaterialColor.STONE)
					.hardnessAndResistance(1.5F, 6.0F)
					.sound(SoundType.STONE)
					.notSolid();

	protected static AbstractBlock.Properties STONE_SOLID =
			AbstractBlock.Properties.
					create(Material.ROCK, MaterialColor.STONE)
					.hardnessAndResistance(1.5F, 6.0F)
					.sound(SoundType.STONE);

	protected static final AbstractBlock.Properties SNOW_SOLID =
			Block.Properties.create(Material.SNOW)
					.hardnessAndResistance(0.3F)
					.sound(SoundType.SNOW);

	protected static AbstractBlock.Properties PLANT_FUNGI =
			AbstractBlock.Properties.
					create(Material.PLANTS, MaterialColor.WOOD)
					.hardnessAndResistance(1.5F, 6.0F)
					.sound(SoundType.PLANT)
					.notSolid();

	public static AbstractBlock.Properties CROP =
			AbstractBlock.Properties
					.create(Material.PLANTS)
					.doesNotBlockMovement()
					.tickRandomly()
					.zeroHardnessAndResistance()
					.sound(SoundType.CROP);

	public static AbstractBlock.Properties ROPE =
			AbstractBlock.Properties
					.create(Material.WOOL, MaterialColor.SNOW)
					.hardnessAndResistance(0.8F)
					.sound(SoundType.CLOTH);
	//endregion
}