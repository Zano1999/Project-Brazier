package net.dark_roleplay.projectbrazier.features.blocks.lattice_block;

import net.dark_roleplay.projectbrazier.util.blocks.AxisVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;

public class AxisLatticeBlock extends Block {

	protected static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	protected Item item;
	protected RegistryObject<Block> mainBlock;

	protected final AxisVoxelShape shapes;

	public AxisLatticeBlock(Properties properties, String shapeName) {
		super(properties);
		this.shapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(shapeName), true);
	}

	public void initMainBlock(RegistryObject<Block> mainBlock){
		this.mainBlock = mainBlock;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shapes.get(state.get(AXIS));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(AXIS, context.getPlacementHorizontalFacing().rotateY().getAxis());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction.Axis currentAxis = state.get(AXIS);
		if(currentAxis != Direction.Axis.Y);
		Direction newDir = rot.rotate(currentAxis == Direction.Axis.X ? Direction.EAST : Direction.NORTH);
		return state.with(AXIS, newDir.getAxis());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	public Item asItem() {
		if (this.item == null) {
			this.item = Item.getItemFromBlock(this.mainBlock.get());
		}

		return this.item.delegate.get(); //Forge: Vanilla caches the items, update with registry replacements.
	}
}