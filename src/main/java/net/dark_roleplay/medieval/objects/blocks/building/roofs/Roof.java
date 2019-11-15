package net.dark_roleplay.medieval.objects.blocks.building.roofs;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.dark_roleplay.medieval.objects.enums.RoofSegment;
import net.dark_roleplay.medieval.util.blocks.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.stream.Stream;

public class Roof extends HorizontalBlock {

    public static final EnumProperty<RoofSegment> SEGMENT = EnumProperty.create("segment", RoofSegment.class);
    public static final BooleanProperty HAS_TE = BooleanProperty.create("has_te");

    protected final EnumMap<Direction, VoxelShape> shapesF = new EnumMap<Direction, VoxelShape>(Direction.class);


    public Roof(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(SEGMENT, RoofSegment.STRAIGHT).with(HAS_TE, false));
        setShapes(Stream.of(
                Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                Block.makeCuboidShape(0, 5, 5, 16, 7, 7),
                Block.makeCuboidShape(0, 10, 10, 16, 12, 12),
                Block.makeCuboidShape(0, 1, 1, 16, 3, 3),
                Block.makeCuboidShape(0, 6, 6, 16, 8, 8),
                Block.makeCuboidShape(0, 11, 11, 16, 13, 13),
                Block.makeCuboidShape(0, 2, 2, 16, 4, 4),
                Block.makeCuboidShape(0, 7, 7, 16, 9, 9),
                Block.makeCuboidShape(0, 12, 12, 16, 14, 14),
                Block.makeCuboidShape(0, 3, 3, 16, 5, 5),
                Block.makeCuboidShape(0, 8, 8, 16, 10, 10),
                Block.makeCuboidShape(0, 13, 13, 16, 15, 15),
                Block.makeCuboidShape(0, 4, 4, 16, 6, 6),
                Block.makeCuboidShape(0, 9, 9, 16, 11, 11),
                Block.makeCuboidShape(0, 14, 14, 16, 16, 16)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());

        setShapesF(Stream.of(
                Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
                Block.makeCuboidShape(0, 0, 1, 16, 2, 2),
                Block.makeCuboidShape(0, 0, 2, 16, 3, 3),
                Block.makeCuboidShape(0, 0, 3, 16, 4, 4),
                Block.makeCuboidShape(0, 0, 4, 16, 5, 5),
                Block.makeCuboidShape(0, 0, 5, 16, 6, 6),
                Block.makeCuboidShape(0, 0, 6, 16, 7, 7),
                Block.makeCuboidShape(0, 0, 7, 16, 8, 8),
                Block.makeCuboidShape(0, 0, 8, 16, 9, 9),
                Block.makeCuboidShape(0, 0, 9, 16, 10, 10),
                Block.makeCuboidShape(0, 0, 10, 16, 11, 11),
                Block.makeCuboidShape(0, 0, 11, 16, 12, 12),
                Block.makeCuboidShape(0, 0, 12, 16, 13, 13),
                Block.makeCuboidShape(0, 0, 13, 16, 14, 14),
                Block.makeCuboidShape(0, 0, 14, 16, 15, 15),
                Block.makeCuboidShape(0, 0, 15, 16, 16, 16)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
    }

    protected void setShapesF(VoxelShape north) {
        this.shapesF.put(Direction.NORTH, north);
        this.shapesF.put(Direction.EAST, VoxelShapeHelper.rotateShape(north, Direction.EAST));
        this.shapesF.put(Direction.SOUTH, VoxelShapeHelper.rotateShape(north, Direction.SOUTH));
        this.shapesF.put(Direction.WEST, VoxelShapeHelper.rotateShape(north, Direction.WEST));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
        return state.get(HAS_TE) ? shapesF.get(state.get(HORIZONTAL_FACING)) : shapes.get(state.get(HORIZONTAL_FACING));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(SEGMENT, HAS_TE);
    }


    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(player.getHeldItem(handIn).getItem() instanceof RoofItem) return false;
        if(worldIn.isRemote() && state.get(HAS_TE)){
            TileEntity te = worldIn.getTileEntity(pos);
            if(te != null && te instanceof RoofTileEntity){
                ((RoofTileEntity) te).invalidatedModel();
            }
        }
        ItemStack stack = player.getHeldItem(handIn);
        if (stack.getItem() instanceof BlockItem) {
            BlockItem bI = (BlockItem) stack.getItem();
            Block block = bI.getBlock();
            BlockState containedState = block.getStateForPlacement(new BlockItemUseContext(new ItemUseContext(player, handIn, hit)));
            if(containedState != null){
                worldIn.setBlockState(pos, state.with(HAS_TE,true));
                TileEntity te = worldIn.getTileEntity(pos);
                if(te != null && te instanceof RoofTileEntity){
                    ((RoofTileEntity) te).setContainedState(containedState);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction dir = context.getPlayer().getHorizontalFacing().getOpposite();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        RoofSegment segment = RoofSegment.STRAIGHT;

        BlockState behind = world.getBlockState(pos.offset(dir.getOpposite()));

        if(behind.getBlock() instanceof Roof) {
            Direction bDir = behind.get(HORIZONTAL_FACING);
            RoofSegment bSeg = behind.get(SEGMENT);
            if((bDir == dir.rotateY() && bSeg == RoofSegment.STRAIGHT) || (bDir == dir.getOpposite() && bSeg == RoofSegment.OUTER_CORNER)){
                segment = RoofSegment.OUTER_CORNER;
                dir = dir.rotateY();
                return this.getDefaultState().with(HORIZONTAL_FACING, dir).with(SEGMENT, segment);
            }else if((bDir == dir.rotateYCCW() && bSeg == RoofSegment.STRAIGHT) || (bDir == dir.rotateYCCW() && bSeg == RoofSegment.OUTER_CORNER)){
                segment = RoofSegment.OUTER_CORNER;
                return this.getDefaultState().with(HORIZONTAL_FACING, dir).with(SEGMENT, segment);
            }
        }

        BlockState front = world.getBlockState(pos.offset(dir));
        if(front.getBlock() instanceof Roof) {
            Direction fDir = front.get(HORIZONTAL_FACING);
            RoofSegment fSeg = front.get(SEGMENT);
            if((fDir == dir.rotateYCCW() && fSeg == RoofSegment.STRAIGHT) || (fDir == dir.getOpposite() && fSeg == RoofSegment.INNER_CORNER)){
                segment = RoofSegment.INNER_CORNER;
                dir = dir.rotateYCCW();
                return this.getDefaultState().with(HORIZONTAL_FACING, dir).with(SEGMENT, segment);
            }else if((fDir == dir.rotateY() && fSeg == RoofSegment.STRAIGHT) || (fDir == dir.rotateY() && fSeg == RoofSegment.INNER_CORNER)){
                segment = RoofSegment.INNER_CORNER;
                return this.getDefaultState().with(HORIZONTAL_FACING, dir).with(SEGMENT, segment);
            }
        }


        return this.getDefaultState().with(HORIZONTAL_FACING, dir).with(SEGMENT, segment);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return state.get(HAS_TE);
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new RoofTileEntity();
    }
}
