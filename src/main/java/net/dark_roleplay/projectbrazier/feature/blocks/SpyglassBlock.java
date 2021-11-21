package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.util.blocks.BrazierStateProperties;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class SpyglassBlock extends Block {

	public static final EnumProperty<BrazierStateProperties.MultiFacing> FACING = BrazierStateProperties.MULTI_FACING;

	protected final HFacedVoxelShape shapesNormal;
	protected final HFacedVoxelShape shapesRotated;

	public SpyglassBlock(Properties properties, String shapeName, String rotatedShapeName) {
		super(properties);
		this.shapesNormal = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape(shapeName));
		this.shapesRotated = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape(rotatedShapeName));
	}


	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.getValue(FACING).isAngled() ? shapesRotated.get(state.getValue(FACING).toDirection()) : shapesNormal.get(state.getValue(FACING).toDirection());
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		if(player == null) return this.defaultBlockState();
		return this.defaultBlockState().setValue(FACING, BrazierStateProperties.MultiFacing.byAngle(player.getYHeadRot()));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, state.getValue(FACING).rotate(rot));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, state.getValue(FACING).mirror(mirror));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
		return false;
	}
}
