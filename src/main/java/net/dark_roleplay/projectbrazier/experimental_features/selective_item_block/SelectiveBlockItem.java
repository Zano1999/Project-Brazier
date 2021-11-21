package net.dark_roleplay.projectbrazier.experimental_features.selective_item_block;

import com.mojang.authlib.GameProfile;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.item.Item.Properties;

public class SelectiveBlockItem extends Item {

	protected final Map<GameProfile, Integer> PLAYER_SELECTED = new WeakHashMap<>();

	protected Block[] blocks;

	public SelectiveBlockItem(Block[] blocks, Properties builder) {
		super(builder);
		this.blocks = blocks;
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));
		return !actionresulttype.consumesAction() && this.isEdible() ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : actionresulttype;
	}

	public ActionResultType tryPlace(BlockItemUseContext context) {
		if (!context.canPlace()) {
			return ActionResultType.FAIL;
		} else {
			BlockItemUseContext blockitemusecontext = context;
			if (blockitemusecontext == null) {
				return ActionResultType.FAIL;
			} else {
				BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
				if (blockstate == null) {
					return ActionResultType.FAIL;
				} else if (!this.placeBlock(blockitemusecontext, blockstate)) {
					return ActionResultType.FAIL;
				} else {
					BlockPos blockpos = blockitemusecontext.getClickedPos();
					World world = blockitemusecontext.getLevel();
					PlayerEntity playerentity = blockitemusecontext.getPlayer();
					ItemStack itemstack = blockitemusecontext.getItemInHand();
					BlockState blockstate1 = world.getBlockState(blockpos);
					Block block = blockstate1.getBlock();
					if (block == blockstate.getBlock()) {
						blockstate1 = this.getStateFromItem(blockpos, world, itemstack, blockstate1);
						this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
						block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
						if (playerentity instanceof ServerPlayerEntity) {
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos, itemstack);
						}
					}

					SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
					world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					if (playerentity == null || !playerentity.abilities.instabuild) {
						itemstack.shrink(1);
					}

					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		}
	}

	protected BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = getCurrentBlock(context.getPlayer().getGameProfile()).getStateForPlacement(context);
		return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
	}

	protected boolean canPlace(BlockItemUseContext context, BlockState state) {
		PlayerEntity playerentity = context.getPlayer();
		ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.empty() : ISelectionContext.of(playerentity);
		return (state.canSurvive(context.getLevel(), context.getClickedPos())) && context.getLevel().isUnobstructed(state, context.getClickedPos(), iselectioncontext);
	}

	private BlockState getStateFromItem(BlockPos pos, World world, ItemStack itemStack, BlockState state) {
		BlockState blockstate = state;
		CompoundNBT compoundnbt = itemStack.getTag();
		if (compoundnbt != null) {
			CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
			StateContainer<Block, BlockState> statecontainer = state.getBlock().getStateDefinition();

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

	public ITextComponent getName(ItemStack stack) {
		return new TranslationTextComponent(this.getDescriptionId(stack));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ITextComponent getDescription() {
		return new TranslationTextComponent(Minecraft.getInstance().player == null ? this.blocks[0].getDescriptionId() : this.getCurrentBlock(Minecraft.getInstance().player.getGameProfile()).getDescriptionId());
	}

	protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
		return true; //setTileEntityNBT(worldIn, player, pos, stack);
	}

	private static <T extends Comparable<T>> BlockState setValueByString(BlockState state, Property<T> property, String value) {
		return property.getValue(value).map(parsedValue -> state.setValue(property, parsedValue)).orElse(state);
	}

	protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
		return context.getLevel().setBlock(context.getClickedPos(), state, 11);
	}

	protected SoundEvent getPlaceSound(BlockState state, World world, BlockPos pos, PlayerEntity entity) {
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

	public static SelectiveBlockItem getHeldSelectiveBlockItem(PlayerEntity player){
		Item item = player.getMainHandItem().getItem();
		if(item instanceof SelectiveBlockItem)
			return (SelectiveBlockItem) item;

		item = player.getOffhandItem().getItem();
		if(item instanceof SelectiveBlockItem)
			return (SelectiveBlockItem) item;

		return null;
	}
}
