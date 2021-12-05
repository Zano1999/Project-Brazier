package net.dark_roleplay.projectbrazier.feature.blocks.crops;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(this.getAgeProperty());
	}


}
