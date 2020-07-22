package net.dark_roleplay.projectbrazier.features.blocks.special;

import net.dark_roleplay.projectbrazier.features.blocks.BrazierStateProperties;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

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
		return state.get(FACING).isAngled() ? shapesRotated.get(state.get(FACING).toDirection()) : shapesNormal.get(state.get(FACING).toDirection());
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		if(player == null) return this.getDefaultState();
		return this.getDefaultState().with(FACING, BrazierStateProperties.MultiFacing.byAngle(player.getRotationYawHead()));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, state.get(FACING).rotate(rot));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.with(FACING, state.get(FACING).mirror(mirror));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
