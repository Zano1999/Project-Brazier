package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;

public class CauliflowerBlock extends CropsBlock {

	private static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;

	public CauliflowerBlock(Properties builder) {
		super(builder);
	}

	@Override
	protected IItemProvider getSeedsItem() {
		return BrazierItems.CAULIFLOWER_SEEDS.get();
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return this.AGE;
	}

	@Override
	public int getMaxAge() {
		return 5;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(this.getAgeProperty());
	}
}
