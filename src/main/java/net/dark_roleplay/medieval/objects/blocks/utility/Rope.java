package net.dark_roleplay.medieval.objects.blocks.utility;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;

public class Rope extends HorizontalBlock {

    public static final BooleanProperty IS_PILE = BooleanProperty.create("is_pile");

    public Rope(Properties properties) {
        super(properties);
        setShapes(Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(IS_PILE);
    }

}
