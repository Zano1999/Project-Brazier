package net.dark_roleplay.medieval.objects.blocks.building.timbered_clay;

import java.util.Map;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.templates.AxisBlock;
import net.dark_roleplay.medieval.objects.enums.TimberedClayEnums;
import net.dark_roleplay.medieval.objects.enums.TimberedClayEnums.TimberRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.Tag;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TimberedClay extends AxisBlock{

	public static final BooleanProperty TOP = BooleanProperty.create("top");
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");

	public static final Map<PlayerEntity, TimberedClayEnums.ClickInfo> clicks = new WeakHashMap<PlayerEntity, TimberedClayEnums.ClickInfo>();
	
	private static Tag<Item> WOOD_BEAM_TAG = new Tag<Item>(new ResourceLocation(DarkRoleplayMedieval.MODID, "wood_beam"));
	
	private TimberedClayEnums.TimberedClayType type = null;
	
	public TimberedClay(Properties properties, TimberedClayEnums.TimberedClayType type) {
		super(properties);
		this.type = type;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return VoxelShapes.fullCube();
	}

	@Override
    public BlockRenderLayer getRenderLayer(){
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(TOP, BOTTOM, LEFT, RIGHT);
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().rotateY().getAxis());
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		TimberRecipe[] recipes = TimberedClayEnums.recipes.get(this.type);
		if(recipes == null || !player.getHeldItem(hand).getItem().isIn(WOOD_BEAM_TAG)) return false;
		
		if(world.isRemote()) return true;
		
		return true;
	}
}
