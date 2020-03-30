package net.dark_roleplay.medieval.objects.blocks.decoration.benches;

import java.util.EnumMap;
import java.util.stream.Stream;

import net.dark_roleplay.medieval.objects.blocks.templates.AxisBlock;
import net.dark_roleplay.medieval.objects.enums.BenchSection;
import net.dark_roleplay.medieval.util.sitting.SittingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BenchBlock extends AxisBlock{

	public static final EnumProperty<BenchSection> BENCH_SECTION = EnumProperty.create("section", BenchSection.class);
	
	protected final EnumMap<BenchSection, EnumMap<Axis, VoxelShape>> shapes = new EnumMap<BenchSection, EnumMap<Axis, VoxelShape>>(BenchSection.class);

	
	public BenchBlock(Properties properties) {
		super(properties);
		setShapes(
			new VoxelShape[] {
				Stream.of(
					Block.makeCuboidShape(0, 7, 2, 16, 8.5, 14),
					Block.makeCuboidShape(0, 3, 7, 16, 4, 9)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Block.makeCuboidShape(0.5, 0, 3, 2, 7, 13),
				Block.makeCuboidShape(14, 0, 3, 15.5, 7, 13),
				Block.makeCuboidShape(7.25, 0, 3, 8.75, 7, 13)
			},
			new VoxelShape[] {
				Stream.of(
					Block.makeCuboidShape(2, 7, 0, 14, 8.5, 16),
					Block.makeCuboidShape(7, 3, 0, 9, 4, 16)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Block.makeCuboidShape(3, 0, 0.5, 13, 7, 2),
				Block.makeCuboidShape(3, 0, 14, 13, 7, 15.5),
				Block.makeCuboidShape(3, 0, 7.25, 13, 7, 8.75)
			}
		);
		
	}

	protected void setShapes(VoxelShape[] x, VoxelShape[] z) {
		EnumMap<Axis, VoxelShape> single = new EnumMap<Axis, VoxelShape>(Axis.class);
		EnumMap<Axis, VoxelShape> left = new EnumMap<Axis, VoxelShape>(Axis.class);
		EnumMap<Axis, VoxelShape> right = new EnumMap<Axis, VoxelShape>(Axis.class);
		EnumMap<Axis, VoxelShape> center = new EnumMap<Axis, VoxelShape>(Axis.class);
		EnumMap<Axis, VoxelShape> centerSupport = new EnumMap<Axis, VoxelShape>(Axis.class);
		
		single.put(Axis.X, Stream.of(x[0], x[1], x[2]).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
		single.put(Axis.Z, Stream.of(z[0], z[1], z[2]).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
		centerSupport.put(Axis.X, Stream.of(x[0], x[3]).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
		centerSupport.put(Axis.Z, Stream.of(z[0], z[3]).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
		center.put(Axis.X, x[0]);
		center.put(Axis.Z, z[0]);
		left.put(Axis.X, Stream.of(x[0], x[2]).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
		left.put(Axis.Z, Stream.of(z[0], z[2]).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
		right.put(Axis.X, Stream.of(x[0], x[1]).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
		right.put(Axis.Z, Stream.of(z[0], z[1]).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
		
		shapes.put(BenchSection.SINGLE, single);
		shapes.put(BenchSection.LEFT, left);
		shapes.put(BenchSection.RIGHT, right);
		shapes.put(BenchSection.CENTER, center);
		shapes.put(BenchSection.CENTER_SUPPORT, centerSupport);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return shapes.get(state.get(BENCH_SECTION)).get(state.get(HORIZONTAL_AXIS));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(BENCH_SECTION);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if(player.getPositionVec().squareDistanceTo(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) < 9) {
			if(!world.isRemote())
				SittingUtil.sitOnBlock((ServerWorld) world, pos.getX(), pos.getY(), pos.getZ(), player, 0.3F);
		}else {
			player.sendStatusMessage(new TranslationTextComponent("interaction.drpmedieval.chair.to_far", state.getBlock().getNameTextComponent().getFormattedText()), true);
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (facing != Direction.DOWN) {
			return getPropperBenchState(state, facing, facingState, world, currentPos, facingPos);
		}
		if (!Block.hasSolidSideOnTop(world, facingPos))
			return Blocks.AIR.getDefaultState();
		return state;
	}
	
	private BlockState getPropperBenchState(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		
		Axis axis = state.get(HORIZONTAL_AXIS);
		if(axis == Axis.X) {
			int left = 0, right = 0;
			BlockState otherState;
			if((otherState = world.getBlockState(currentPos.west())).getBlock().getClass() == this.getClass()) {
				BenchSection bench = otherState.get(BENCH_SECTION);
				if(!world.isRemote()) System.out.println("L: " + bench);
				left = (bench == BenchSection.CENTER_SUPPORT ) ? 2 : 1;
			}
			if((otherState = world.getBlockState(currentPos.east())).getBlock().getClass() == this.getClass()) {
				BenchSection bench = otherState.get(BENCH_SECTION);
				if(!world.isRemote()) System.out.println("R: " + bench);
				right = (bench == BenchSection.CENTER_SUPPORT ) ? 2 : 1;
			}
			
			if(!world.isRemote()) System.out.println("B: " + left + ", " + right);
			
			if(left == 1 && right == 1 || left == 2 && right == 1 || left == 1 && right == 2) {
				return state.with(BENCH_SECTION, BenchSection.CENTER_SUPPORT);
			}else if(left == 2 && right == 2) {
				return state.with(BENCH_SECTION, BenchSection.CENTER);
			}else if(left == 1 || left == 2) {
				return state.with(BENCH_SECTION, BenchSection.LEFT);
			}else if(right == 1 || right == 2) {
				return state.with(BENCH_SECTION, BenchSection.RIGHT);
			}
		}else if(axis == Axis.Z) {
			int left = 0, right = 0;
			BlockState otherState;
			if((otherState = world.getBlockState(currentPos.north())).getBlock().getClass() == this.getClass()) {
				BenchSection bench = otherState.get(BENCH_SECTION);
				if(!world.isRemote()) System.out.println("L: " + bench);
				left = (bench == BenchSection.CENTER_SUPPORT ) ? 2 : 1;
			}
			if((otherState = world.getBlockState(currentPos.south())).getBlock().getClass() == this.getClass()) {
				BenchSection bench = otherState.get(BENCH_SECTION);
				if(!world.isRemote()) System.out.println("R: " + bench);
				right = (bench == BenchSection.CENTER_SUPPORT ) ? 2 : 1;
			}
			
			if(!world.isRemote()) System.out.println("B: " + left + ", " + right);
			
			if(left == 1 && right == 1 || left == 2 && right == 1 || left == 1 && right == 2) {
				return state.with(BENCH_SECTION, BenchSection.CENTER_SUPPORT);
			}else if(left == 2 && right == 2) {
				return state.with(BENCH_SECTION, BenchSection.CENTER);
			}else if(left == 1 || left == 2) {
				return state.with(BENCH_SECTION, BenchSection.LEFT);
			}else if(right == 1 || right == 2) {
				return state.with(BENCH_SECTION, BenchSection.RIGHT);
			}
		}
		return state.with(BENCH_SECTION, BenchSection.SINGLE);
	}
}
