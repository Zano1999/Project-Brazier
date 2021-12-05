package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.util.blocks.BrazierStateProperties;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(FACING).isAngled() ? shapesRotated.get(state.getValue(FACING).toDirection()) : shapesNormal.get(state.getValue(FACING).toDirection());
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Player player = context.getPlayer();
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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		return false;
	}
}
