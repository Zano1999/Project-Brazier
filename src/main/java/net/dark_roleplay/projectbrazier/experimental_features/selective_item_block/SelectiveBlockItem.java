package net.dark_roleplay.projectbrazier.experimental_features.selective_item_block;

import com.mojang.authlib.GameProfile;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.WeakHashMap;

public class SelectiveBlockItem extends Item {

	protected final Map<GameProfile, Integer> PLAYER_SELECTED = new WeakHashMap<>();

	protected Block[] blocks;

	public SelectiveBlockItem(Block[] blocks, Properties builder) {
		super(builder);
		this.blocks = blocks;
	}

	@Override
	public InteractionResult useOn(ItemUseContext context) {
		InteractionResult actionresulttype = this.tryPlace(new BlockPlaceContext(context));
		return !actionresulttype.consumesAction() && this.isEdible() ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : actionresulttype;
	}

	public InteractionResult tryPlace(BlockPlaceContext context) {
		if (!context.canPlace()) {
			return InteractionResult.FAIL;
		} else {
			BlockPlaceContext blockitemusecontext = context;
			if (blockitemusecontext == null) {
				return InteractionResult.FAIL;
			} else {
				BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
				if (blockstate == null) {
					return InteractionResult.FAIL;
				} else if (!this.placeBlock(blockitemusecontext, blockstate)) {
					return InteractionResult.FAIL;
				} else {
					BlockPos blockpos = blockitemusecontext.getClickedPos();
					Level world = blockitemusecontext.getLevel();
					Player playerentity = blockitemusecontext.getPlayer();
					ItemStack itemstack = blockitemusecontext.getItemInHand();
					BlockState blockstate1 = world.getBlockState(blockpos);
					Block block = blockstate1.getBlock();
					if (block == blockstate.getBlock()) {
						blockstate1 = this.getStateFromItem(blockpos, world, itemstack, blockstate1);
						this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
						block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
						if (playerentity instanceof ServerPlayer) {
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerentity, blockpos, itemstack);
						}
					}

					SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
					world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, context.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					if (playerentity == null || !playerentity.abilities.instabuild) {
						itemstack.shrink(1);
					}

					return InteractionResult.sidedSuccess(world.isClientSide);
				}
			}
		}
	}

	protected BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = getCurrentBlock(context.getPlayer().getGameProfile()).getStateForPlacement(context);
		return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
	}

	protected boolean canPlace(BlockPlaceContext context, BlockState state) {
		Player playerentity = context.getPlayer();
		CollisionContext iselectioncontext = playerentity == null ? CollisionContext.empty() : CollisionContext.of(playerentity);
		return (state.canSurvive(context.getLevel(), context.getClickedPos())) && context.getLevel().isUnobstructed(state, context.getClickedPos(), iselectioncontext);
	}

	private BlockState getStateFromItem(BlockPos pos, Level world, ItemStack itemStack, BlockState state) {
		BlockState blockstate = state;
		CompoundTag compoundnbt = itemStack.getTag();
		if (compoundnbt != null) {
			CompoundTag compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
			StateDefinition<Block, BlockState> statecontainer = state.getBlock().getStateDefinition();

			for(String s : compoundnbt1.getAllKeys()) {
				Property<?> property = statecontainer.getProperty(s);
				if (property != null) {
					String s1 = compoundnbt1.get(s).getAsString();
					blockstate = setValueByString(blockstate, property, s1);
				}
			}
		}

		if (blockstate != state) {
			world.setBlock(pos, blockstate, 2);
		}

		return blockstate;
	}

	public TextComponent getName(ItemStack stack) {
		return new TranslatableComponent(this.getDescriptionId(stack));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public TextComponent getDescription() {
		return new TranslatableComponent(Minecraft.getInstance().player == null ? this.blocks[0].getDescriptionId() : this.getCurrentBlock(Minecraft.getInstance().player.getGameProfile()).getDescriptionId());
	}

	protected boolean onBlockPlaced(BlockPos pos, Level worldIn, @Nullable Player player, ItemStack stack, BlockState state) {
		return true; //setTileEntityNBT(worldIn, player, pos, stack);
	}

	private static <T extends Comparable<T>> BlockState setValueByString(BlockState state, Property<T> property, String value) {
		return property.getValue(value).map(parsedValue -> state.setValue(property, parsedValue)).orElse(state);
	}

	protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
		return context.getLevel().setBlock(context.getClickedPos(), state, 11);
	}

	protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity) {
		return state.getSoundType(world, pos, entity).getPlaceSound();
	}

	public Block getCurrentBlock(GameProfile profile){
		return blocks[PLAYER_SELECTED.computeIfAbsent(profile, prof -> 0)];
	}

	public int getCurrentIndex(GameProfile profile){
		return PLAYER_SELECTED.computeIfAbsent(profile, prof -> 0);
	}

	public int getMaxIndex(){
		return this.blocks.length - 1;
	}

	public void setPlayerSelected(GameProfile profile, int selected){
		PLAYER_SELECTED.put(profile, Math.min(Math.max(selected, 0), blocks.length));
	}

	public static SelectiveBlockItem getHeldSelectiveBlockItem(Player player){
		Item item = player.getMainHandItem().getItem();
		if(item instanceof SelectiveBlockItem)
			return (SelectiveBlockItem) item;

		item = player.getOffhandItem().getItem();
		if(item instanceof SelectiveBlockItem)
			return (SelectiveBlockItem) item;

		return null;
	}
}
