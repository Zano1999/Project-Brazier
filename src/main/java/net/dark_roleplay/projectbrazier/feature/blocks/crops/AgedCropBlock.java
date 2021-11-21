package net.dark_roleplay.projectbrazier.feature.blocks.crops;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AgedCropBlock extends CropsBlock {

	private static final IntegerProperty[] AGES = {
			BlockStateProperties.AGE_1,
			BlockStateProperties.AGE_2,
			BlockStateProperties.AGE_3,
			IntegerProperty.create("age", 0, 4), //AGE_0_4
			BlockStateProperties.AGE_5,
			IntegerProperty.create("age", 0, 6), //AGE_0_6
			BlockStateProperties.AGE_7,
			IntegerProperty.create("age", 0, 8)  //AGE_0_8
	};

	private RegistryObject<Item> seeds;

	public AgedCropBlock(Properties builder, RegistryObject<Item> seeds) {
		super(builder);
		this.seeds = seeds;
	}

	@Override
	protected IItemProvider getBaseSeedId() {
		return seeds.get();
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return this.AGES[getMaxAge()-1];
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(this.getAgeProperty());
	}


}
