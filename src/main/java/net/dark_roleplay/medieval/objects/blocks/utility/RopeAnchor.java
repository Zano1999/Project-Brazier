package net.dark_roleplay.medieval.objects.blocks.utility;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler_2.MedievalBlocks;
import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.dark_roleplay.medieval.util.AdvancedInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RopeAnchor extends HorizontalBlock {

    public static final BooleanProperty HAS_ROPE = BooleanProperty.create("has_rope");

    public RopeAnchor(Properties properties) {
        super(properties);
        setShapes(Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D));

        AdvancedInteractionHelper.register(
                (world, pos, state) -> state.getBlock() == this && state.get(HAS_ROPE),
                "blockInteraction." + DarkRoleplayMedieval.MODID + ".rope_anchor.dropRope",
                15,
                this::dropLadder);

        AdvancedInteractionHelper.register(
                (world, pos, state) -> state.getBlock() == this && !state.get(HAS_ROPE),
                "blockInteraction." + DarkRoleplayMedieval.MODID + ".rope_anchor.pullRope",
                30,
                this::pullRope);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HAS_ROPE);
    }

    @Override
    public boolean hasTileEntity(BlockState state)    {
        return true;
    }
    @Nullable
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new AnchorTileEntity(32);
    }


    public void dropLadder(World world, BlockPos pos, BlockState state){
//        if (world.isRemote()) {
//            //Client send Packet
//            System.out.println("DEBUUUUUUG");
//
//        }else{
            Direction direction = state.get(HORIZONTAL_FACING);
            for(int i = 1; i < 16; i++){
                BlockPos downPos = pos.offset(direction).offset(Direction.DOWN, i);
                if(world.isAirBlock(downPos)){
                    world.setBlockState(downPos, MedievalBlocks.ROPE.get().getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)).with(Rope.IS_PILE, false));
                }else{
                    world.setBlockState(downPos.up(), MedievalBlocks.ROPE.get().getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)).with(Rope.IS_PILE, true));
                    break;
                }
            }
            world.setBlockState(pos, state.with(HAS_ROPE, false));
//        }
    }

    public void pullRope(World world, BlockPos pos, BlockState state){

        Direction direction = state.get(HORIZONTAL_FACING);
        for(int i = 1; i < 16; i++){
            BlockPos downPos = pos.offset(direction).offset(Direction.DOWN, i);
            if(world.getBlockState(downPos).getBlock() == MedievalBlocks.ROPE.get()){
                world.destroyBlock(downPos, false);
            }else{
                break;
            }
        }
        world.setBlockState(pos, state.with(HAS_ROPE, true));

        if (world.isRemote()) {
            //Client send Packet

        }else{
            //Server, handle the drop
        }
    }
}
