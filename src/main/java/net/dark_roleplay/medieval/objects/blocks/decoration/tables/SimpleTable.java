package net.dark_roleplay.medieval.objects.blocks.decoration.tables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SimpleTable extends Block {

    private Map<BlockState, VoxelShape> shapes = new HashMap<>();

    public SimpleTable(Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        for(Connection con : Connection.values()){
            builder.add(con.getProperty());
        }
        for(Leg leg : Leg.values()){
            builder.add(leg.getProperty());
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
        VoxelShape shape = shapes.get(state);
        if(shape != null) return shape;

        shape = Block.makeCuboidShape(0, 14.5, 0, 16, 16, 16);

        for(Leg leg : Leg.values()){
            if(state.get(leg.getProperty()))
                shape = VoxelShapes.combineAndSimplify(shape, leg.getShape(), IBooleanFunction.OR);
        }
        for(Connection con : Connection.values()){
            if(!state.get(con.getProperty()))
                shape = VoxelShapes.combineAndSimplify(shape, con.getShape(), IBooleanFunction.OR);
        }

        shapes.put(state, shape);

        return shape;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();

        BlockState state = this.getDefaultState();
        for(Connection con : Connection.values()){
            state = state.with(con.getProperty(), con.shouldBeAvailable(state, world, pos));
        }

        for(Leg leg : Leg.values()){
            state = state.with(leg.getProperty(), leg.shouldBeAvailable(state, world, pos));
        }
        return state;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.DOWN || facing == Direction.UP) return state;
        Connection con = Connection.getForDirection(facing);
        if(con != null)
            state = state.with(con.getProperty(), con.shouldBeAvailable(state, world, currentPos));

        for(Leg leg : Leg.values()){
            state = state.with(leg.getProperty(), leg.shouldBeAvailable(state, world, currentPos));
        }

        return state;
    }

    private enum Connection{
        NORTH(BooleanProperty.create("connected_n"), Direction.NORTH, Block.makeCuboidShape(0, 12.5, 1, 16, 14.5, 2)),
        EAST(BooleanProperty.create("connected_e"), Direction.EAST, Block.makeCuboidShape(14, 12.5, 0, 15, 14.5, 16)),
        SOUTH(BooleanProperty.create("connected_s"), Direction.SOUTH, Block.makeCuboidShape(0, 12.5, 14, 16, 14.5, 15)),
        WEST(BooleanProperty.create("connected_w"), Direction.WEST, Block.makeCuboidShape(1, 12.5, 0, 2, 14.5, 16));

        private BooleanProperty prop;
        private Direction dir;
        private VoxelShape shape;

        Connection(BooleanProperty property, Direction dir, VoxelShape shape){
            this.prop = property;
            this.dir = dir;
            this.shape = shape;
        }

        public BooleanProperty getProperty(){
            return this.prop;
        }

        public boolean shouldBeAvailable(BlockState state, IWorld world, BlockPos pos){
            return  world.getBlockState(pos.offset(dir)).getBlock() == state.getBlock();
        }

        public static Connection getForDirection(Direction dir){
            switch(dir){
                case NORTH: return NORTH;
                case EAST: return EAST;
                case SOUTH: return SOUTH;
                case WEST: return WEST;
                default: return null;
            }
        }

        public VoxelShape getShape(){
            return this.shape;
        }
    }

    private enum Leg{
        NE(BooleanProperty.create("leg_ne"), Direction.NORTH, Block.makeCuboidShape(13.5, 0, 0.5, 15.5, 14.5, 2.5)),
        NW(BooleanProperty.create("leg_nw"), Direction.WEST, Block.makeCuboidShape(0.5, 0, 0.5, 2.5, 14.5, 2.5)),
        SE(BooleanProperty.create("leg_se"), Direction.EAST, Block.makeCuboidShape(13.5, 0, 13.5, 15.5, 14.5, 15.5)),
        SW(BooleanProperty.create("leg_sw"), Direction.SOUTH, Block.makeCuboidShape(0.5, 0, 13.5, 2.5, 14.5, 15.5));

        private BooleanProperty prop;
        private Direction dir;
        private VoxelShape shape;

        Leg(BooleanProperty property, Direction dir, VoxelShape shape){
            this.prop = property;
            this.dir = dir;
            this.shape = shape;
        }

        public BooleanProperty getProperty(){
            return this.prop;
        }

        public boolean shouldBeAvailable(BlockState state, IWorld world, BlockPos pos){
            Block self = state.getBlock();
            boolean front = world.getBlockState(pos.offset(dir)).getBlock() == self;
            boolean right = world.getBlockState(pos.offset(dir.rotateY())).getBlock() == self;
            boolean diagonal = world.getBlockState(pos.offset(dir).offset(dir.rotateY())).getBlock() == self;
            return  (front && right &&  !diagonal) || (!front && !right);
        }

        public VoxelShape getShape(){
            return this.shape;
        }
    }
}
