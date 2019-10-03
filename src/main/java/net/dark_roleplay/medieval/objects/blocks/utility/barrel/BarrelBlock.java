package net.dark_roleplay.medieval.objects.blocks.utility.barrel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;

public class BarrelBlock extends Block {

    public static final BooleanProperty IS_CLOSED = BooleanProperty.create("is_closed");

    public BarrelBlock(Properties properties) {
        super(properties);
    }



    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(IS_CLOSED);
    }

}
