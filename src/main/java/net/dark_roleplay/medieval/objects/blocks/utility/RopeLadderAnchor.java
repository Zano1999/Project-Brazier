package net.dark_roleplay.medieval.objects.blocks.utility;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.dark_roleplay.medieval.util.AdvancedInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RopeLadderAnchor extends HorizontalBlock {

    public static final BooleanProperty HAS_ROPE = BooleanProperty.create("has_rope");

    public RopeLadderAnchor(Properties properties) {
        super(properties);
        setShapes(Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D));

        AdvancedInteractionHelper.register(
                (world, pos, state) -> state.getBlock() == this && state.get(HAS_ROPE),
                "blockInteraction." + DarkRoleplayMedieval.MODID + ".rope_ladder_anchor.dropLadder",
                15,
                this::dropLadder);

        AdvancedInteractionHelper.register(
                (world, pos, state) -> state.getBlock() == this && state.get(HAS_ROPE),
                "blockInteraction." + DarkRoleplayMedieval.MODID + ".rope_ladder_anchor.dropLadder",
                30,
                this::pullLadder);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HAS_ROPE);
    }

    public void dropLadder(World world, BlockPos pos, BlockState state){
        if (world.isRemote()) {
            //Client send Packet

        }else{
            //Server, handle the drop
        }
    }

    public void pullLadder(World world, BlockPos pos, BlockState state){
        if (world.isRemote()) {
            //Client send Packet

        }else{
            //Server, handle the drop
        }
    }

}
