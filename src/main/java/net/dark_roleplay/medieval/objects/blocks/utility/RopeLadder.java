package net.dark_roleplay.medieval.objects.blocks.utility;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class RopeLadder extends HorizontalBlock {

    public static final BooleanProperty HAS_ROPE = BooleanProperty.create("has_ladder");

    public RopeLadder(Properties properties) {
        super(properties);
        setShapes(Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D));
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }
}
