package net.dark_roleplay.medieval.objects.blocks.decoration.chairs;

import java.util.EnumMap;
import java.util.stream.Stream;

import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.template.ChairBlock;
import net.dark_roleplay.medieval.util.blocks.VoxelShapeHelper;
import net.dark_roleplay.medieval.util.sitting.SittingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class SolidChairBlock extends ChairBlock {

	public static final BooleanProperty HIDDEN_COMPARTMENT = BooleanProperty.create("hidden_compartment");

	protected final EnumMap<Direction, VoxelShape> compartmentShapes = new EnumMap<Direction, VoxelShape>(
			Direction.class);

	protected final EnumMap<Direction, AxisAlignedBB> buttons = new EnumMap<Direction, AxisAlignedBB>(
			Direction.class);

	public SolidChairBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(HIDDEN_COMPARTMENT, false));

		setShapes(Stream.of(Block.makeCuboidShape(1.5, 0, 3, 3.5, 7, 5), Block.makeCuboidShape(12.5, 0, 3, 14.5, 7, 5),
				Block.makeCuboidShape(12.5, 0, 13, 14.5, 18, 15), Block.makeCuboidShape(1.5, 0, 13, 3.5, 18, 15),
				VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 5.5, 3.5, 14, 7, 14.5),
						Block.makeCuboidShape(3, 5.5, 4.5, 13, 7, 13.5), IBooleanFunction.ONLY_FIRST),
				Block.makeCuboidShape(1, 7, 2.5, 15, 8, 15), Block.makeCuboidShape(3.5, 11, 13.5, 12.5, 17, 14.5))
				.reduce((v1, v2) -> {
					return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
				}).get()
		);

		setShapesCompartment(
				Stream.of(
					Block.makeCuboidShape(1.5, 0, 3, 3.5, 7, 5),
					Block.makeCuboidShape(12.5, 0, 3, 14.5, 7, 5), 
					Block.makeCuboidShape(12.5, 0, 13, 14.5, 18, 15),
					Block.makeCuboidShape(1.5, 0, 13, 3.5, 18, 15), 
					Block.makeCuboidShape(1, 7, 2.5, 15, 8, 15),
					Block.makeCuboidShape(3.5, 11, 13.5, 12.5, 17, 14.5),
					Block.makeCuboidShape(6.5, 6.5, 14.5, 9.5, 7, 14.75), 
					Block.makeCuboidShape(2, 5.5, 3.5, 14, 7, 14.5))
				.reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
		);
		setButtons(
			Block.makeCuboidShape(6.5, 6.5, 14.5, 9.5, 7, 14.75),
			Block.makeCuboidShape(1.25, 6.5, 6.5, 1.5, 7, 9.5), 
			Block.makeCuboidShape(6.5, 6.5, 1.25, 9.5, 7, 1.5),
			Block.makeCuboidShape(14.5, 6.5, 6.5, 14.75, 7, 9.5)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		if (state.get(HIDDEN_COMPARTMENT)) {
			return compartmentShapes.get(state.get(HORIZONTAL_FACING));
		} else {
			return shapes.get(state.get(HORIZONTAL_FACING));
		}
	}

	protected void setButtons(VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
		this.buttons.put(Direction.NORTH,
				north.getBoundingBox().expand(0.0001, 0.0001, 0.0001).expand(-0.0001, -0.0001, -0.0001));
		this.buttons.put(Direction.EAST,
				east.getBoundingBox().expand(0.0001, 0.0001, 0.0001).expand(-0.0001, -0.0001, -0.0001));
		this.buttons.put(Direction.SOUTH,
				south.getBoundingBox().expand(0.0001, 0.0001, 0.0001).expand(-0.0001, -0.0001, -0.0001));
		this.buttons.put(Direction.WEST,
				west.getBoundingBox().expand(0.0001, 0.0001, 0.0001).expand(-0.0001, -0.0001, -0.0001));
	}

	protected void setShapesCompartment(VoxelShape north) {
		this.compartmentShapes.put(Direction.NORTH, north);
		this.compartmentShapes.put(Direction.EAST, VoxelShapeHelper.rotateShape(north, Direction.EAST));
		this.compartmentShapes.put(Direction.SOUTH, VoxelShapeHelper.rotateShape(north, Direction.SOUTH));
		this.compartmentShapes.put(Direction.WEST, VoxelShapeHelper.rotateShape(north, Direction.WEST));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HIDDEN_COMPARTMENT);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.get(HIDDEN_COMPARTMENT);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new SolidChairTileEntity();
	}

	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = world.getTileEntity(pos);
			
			if (tileentity == null) return;
			LazyOptional<IItemHandler> itemHandler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			if (!itemHandler.isPresent()) return;
			IItemHandler handler = itemHandler.orElse(null);
			if (!handler.getStackInSlot(0).isEmpty())
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), handler.extractItem(0, handler.getStackInSlot(0).getCount(), false));
		}
		super.onReplaced(state, world, pos, newState, isMoving);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		Vec3d hitVec = rayTraceResult.getHitVec();
		if (state.get(HIDDEN_COMPARTMENT) && buttons.get(state.get(HORIZONTAL_FACING)).contains(hitVec.x, hitVec.y, hitVec.z)) {
			TileEntity te = world.getTileEntity(pos);

			if (te == null)
				return ActionResultType.SUCCESS;
			
			if(!world.isRemote)NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, pos);
			return ActionResultType.SUCCESS;
		} else {
			if(player.getPositionVec().squareDistanceTo(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) < 9) {
				if(!world.isRemote())
					SittingUtil.sitOnBlockWithRotation((ServerWorld) world, pos.getX(), pos.getY(), pos.getZ(), player, state.get(HORIZONTAL_FACING), 0.2F);
			} else {
				player.sendStatusMessage(new TranslationTextComponent("interaction.drpmedieval.chair.to_far",
						state.getBlock().getNameTextComponent().getFormattedText()), true);
			}
			return ActionResultType.SUCCESS;
		}
	}
}
