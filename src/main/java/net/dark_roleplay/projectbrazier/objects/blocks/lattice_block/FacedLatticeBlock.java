package net.dark_roleplay.projectbrazier.objects.blocks.lattice_block;

import net.dark_roleplay.projectbrazier.util.blocks.FacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;

public class FacedLatticeBlock extends Block {

	protected static final DirectionProperty FACING = BlockStateProperties.FACING;

	protected final FacedVoxelShape shapes;
	protected final RegistryObject<Block> centerBlock;

	public FacedLatticeBlock(Properties props, String shapeName, RegistryObject<Block> centerBlock) {
		super(props);
		this.shapes = new FacedVoxelShape(VoxelShapeLoader.getVoxelShape(shapeName));
		this.centerBlock = centerBlock;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shapes.get(state.get(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		double hitX = context.getHitVec().getX() - context.getPos().getX();
		double hitY = context.getHitVec().getY() - context.getPos().getY();
		double hitZ = context.getHitVec().getZ() - context.getPos().getZ();

		Direction.Axis playerAxis = context.getPlacementHorizontalFacing().getAxis();

		if(context.getFace().getAxis() == Direction.Axis.Y){
			if(hitX > 0.75 && playerAxis == Direction.Axis.X){
				return this.getDefaultState().with(FACING, Direction.EAST);
			}else if(hitX < 0.25 && playerAxis == Direction.Axis.X){
				return this.getDefaultState().with(FACING, Direction.WEST);
			}else if(hitZ > 0.75 && playerAxis == Direction.Axis.Z) {
				return this.getDefaultState().with(FACING, Direction.SOUTH);
			}else if(hitZ < 0.25 && playerAxis == Direction.Axis.Z) {
				return this.getDefaultState().with(FACING, Direction.NORTH);
			}else{
				return this.centerBlock.get().getDefaultState().with(AxisLatticeBlock.AXIS, context.getPlacementHorizontalFacing().getAxis());
			}
		}else if(context.getFace().getAxis() == Direction.Axis.Z){
			if(hitY > 0.75){
				return this.getDefaultState().with(FACING, Direction.UP);
			}else if(hitY < 0.25){
				return this.getDefaultState().with(FACING, Direction.DOWN);
			}if(hitX > 0.75){
				return this.getDefaultState().with(FACING, Direction.EAST);
			}else if(hitX < 0.25){
				return this.getDefaultState().with(FACING, Direction.WEST);
			}else{
				return this.centerBlock.get().getDefaultState().with(AxisLatticeBlock.AXIS, Direction.Axis.Y);
			}
		}else if(context.getFace().getAxis() == Direction.Axis.X){
			if(hitY > 0.75){
				return this.getDefaultState().with(FACING, Direction.UP);
			}else if(hitY < 0.25){
				return this.getDefaultState().with(FACING, Direction.DOWN);
			}else if(hitZ > 0.75) {
				return this.getDefaultState().with(FACING, Direction.SOUTH);
			}else if(hitZ < 0.25) {
				return this.getDefaultState().with(FACING, Direction.NORTH);
			}else{
				return this.centerBlock.get().getDefaultState().with(AxisLatticeBlock.AXIS, Direction.Axis.Y);
			}
		}

		return this.getDefaultState().with(FACING, Direction.UP);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}