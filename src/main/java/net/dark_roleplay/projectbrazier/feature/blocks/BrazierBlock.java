package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BrazierBlock extends DecoBlock {

	public static final BooleanProperty BURNING = BooleanProperty.create("burning");

	private final int fireDamage;

	public BrazierBlock(Properties props, int fireDamage, String shape) {
		super(props, shape);
		this.fireDamage = fireDamage;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(BURNING, false);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BURNING);
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(BURNING) ? 15 : 0;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.isImmuneToFire() && state.get(BURNING) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entityIn)) {
			entityIn.attackEntityFrom(DamageSource.IN_FIRE, (float)this.fireDamage);
		}

		super.onEntityCollision(state, worldIn, pos, entityIn);
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
			if(heldItem.getToolTypes().contains(ToolType.SHOVEL)){
				if(world.isRemote()) return ActionResultType.SUCCESS;
				if(!player.isCreative())
					heldItem.attemptDamageItem(1, player.getRNG(), (ServerPlayerEntity) (player));
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
			}
		}
		return ActionResultType.PASS;
	}
}
