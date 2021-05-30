package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.dark_roleplay.projectbrazier.util.Inventories;
import net.dark_roleplay.projectbrazier.util.blocks.IDisplayTicker;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BurningBlock extends DecoBlock {

	public static final BooleanProperty BURNING = BooleanProperty.create("burning");
	private final IDisplayTicker displayTicker;

	private Item replacementItem;
	private Block emptyBlock;

	private final int burningLightLevel;

	public BurningBlock(AbstractBlock.Properties properties, String shapeName, int burningLightLevel, IDisplayTicker displayTicker) {
		super(properties, shapeName);
		this.burningLightLevel = burningLightLevel;
		this.setDefaultState(this.getDefaultState().with(BURNING, false));
		this.displayTicker = displayTicker;
	}

	public void setItem(Item replacementItem, Block emptyBlock){
		this.replacementItem = replacementItem;
		this.emptyBlock = emptyBlock;
	}

	@Override
	@Deprecated
	public void onProjectileCollision(World world, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
		if(!world.isRemote() && projectile.isBurning()){
			Entity owner = projectile.func_234616_v_();
			//Check if mob griefing is active
			boolean flag = owner == null || owner instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, owner);
			if (flag && !state.get(BURNING))
				world.setBlockState(hit.getPos(), state.with(BURNING, true), 3);
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getHeldItem(hand);
		if(state.get(BURNING)){
			if(player.isCrouching()){
				if(world.isRemote()) return ActionResultType.SUCCESS;
				world.setBlockState(pos, state.with(BURNING, false));
				world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 0.8f, 2f);
				return ActionResultType.SUCCESS;
			}
		}else{
			if(heldItem.getItem() == Items.FLINT_AND_STEEL){
				if(world.isRemote()) return ActionResultType.SUCCESS;
				if(!player.isCreative())
					heldItem.attemptDamageItem(1, player.getRNG(), (ServerPlayerEntity) (player));
				world.setBlockState(pos, state.with(BURNING, true));
				world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1f, 1f);
				return ActionResultType.SUCCESS;
			}else if(player.isCrouching()){
				Inventories.givePlayerItem(player, new ItemStack(this.replacementItem), hand, true);
				BlockState newState = emptyBlock.getDefaultState();
				world.setBlockState(pos, newState);
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(BURNING) ? burningLightLevel : 0;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(BURNING);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		this.displayTicker.animateTick(state, world, pos, rand);
	}
}
