package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.dark_roleplay.projectbrazier.util.Inventories;
import net.dark_roleplay.projectbrazier.util.blocks.IDisplayTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BurningBlock extends DecoBlock {

	public static final BooleanProperty BURNING = BooleanProperty.create("burning");
	private final IDisplayTicker displayTicker;

	private Item replacementItem;
	private Block emptyBlock;

	private final int burningLightLevel;

	public BurningBlock(BlockBehaviour.Properties properties, String shapeName, int burningLightLevel, IDisplayTicker displayTicker) {
		super(properties, shapeName);
		this.burningLightLevel = burningLightLevel;
		this.registerDefaultState(this.defaultBlockState().setValue(BURNING, false));
		this.displayTicker = displayTicker;
	}

	public void setItem(Item replacementItem, Block emptyBlock){
		this.replacementItem = replacementItem;
		this.emptyBlock = emptyBlock;
	}

	@Override
	@Deprecated
	public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
		if(!world.isClientSide() && projectile.isOnFire()){
			Entity owner = projectile.getOwner();
			//Check if mob griefing is active
			boolean flag = owner == null || owner instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, owner);
			if (flag && !state.getValue(BURNING))
				world.setBlock(hit.getBlockPos(), state.setValue(BURNING, true), 3);
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack heldItem = player.getItemInHand(hand);
		if(state.getValue(BURNING)){
			if(player.isCrouching()){
				if(world.isClientSide()) return InteractionResult.SUCCESS;
				world.setBlockAndUpdate(pos, state.setValue(BURNING, false));
				world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.8f, 2f);
				return InteractionResult.SUCCESS;
			}
		}else{
			if(heldItem.getItem() == Items.FLINT_AND_STEEL){
				if(world.isClientSide()) return InteractionResult.SUCCESS;
				if(!player.isCreative())
					heldItem.hurt(1, player.getRandom(), (ServerPlayer) (player));
				world.setBlockAndUpdate(pos, state.setValue(BURNING, true));
				world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1f, 1f);
				return InteractionResult.SUCCESS;
			}else if(player.isCrouching()){
				Inventories.givePlayerItem(player, new ItemStack(this.replacementItem), hand, true);
				BlockState newState = emptyBlock.defaultBlockState();
				world.setBlockAndUpdate(pos, newState);
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return state.getValue(BURNING) ? burningLightLevel : 0;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BURNING);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
		this.displayTicker.animateTick(state, world, pos, rand);
	}
}
