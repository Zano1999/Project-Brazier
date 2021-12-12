package net.dark_roleplay.projectbrazier.feature.blockentities;

import com.google.common.collect.ImmutableList;
import net.dark_roleplay.projectbrazier.experimental_features.builtin_mixed_model.BuiltinMixedModelData;
import net.dark_roleplay.projectbrazier.experimental_features.builtin_mixed_model.IQuadProvider;
import net.dark_roleplay.projectbrazier.feature.blocks.FlowerContainerData;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature_client.blocks.CFlowerContainerHelper;
import net.dark_roleplay.projectbrazier.util.blocks.ChunkRenderUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FlowerContainerBlockEntity extends BlockEntity {

	protected final int flowerCount;
	protected List<FlowerContainerData> flowers;

	public FlowerContainerBlockEntity(BlockPos pos, BlockState state) {
		this(3, pos, state);
	}

	public FlowerContainerBlockEntity(int flowerCount, BlockPos pos, BlockState state) {
		super(BrazierBlockEntities.FLOWER_CONTAINER.get(), pos, state);
		this.flowerCount = flowerCount;

		this.flowers = DistExecutor.unsafeRunForDist(() -> () -> (List<FlowerContainerData>)(List<?>) CFlowerContainerHelper.createFlowerData(flowerCount), () -> () -> this.createFlowerData(flowerCount));
	}

	protected List<FlowerContainerData> createFlowerData(int flowerCount) {
		List<FlowerContainerData> flowers = new ArrayList<>(flowerCount);
		for(int i = 0; i < flowerCount; i++)
			flowers.add(0, new FlowerContainerData());
		return ImmutableList.copyOf(flowers);
	}

	public ItemStack addFlower(ItemStack stack, Vec3i offset){
		for(int i = 0; i < flowers.size(); i++){
			FlowerContainerData flower = flowers.get(i);
			if(flower.getFlower().isEmpty()){
				ItemStack copy = stack.copy();
				copy.setCount(1);
				flower.setFlower(copy);
				flower.setPlacement(offset);
				stack.shrink(1);
				this.setChanged();
				break;
			}
		}
		return stack;
	}

	public ItemStack removeFlower(){
		for(int i = flowers.size()-1; i >= 0; i--){
			FlowerContainerData flower = flowers.get(i);
			if(flower.getFlower().isEmpty()) continue;
			ItemStack stack = flower.getFlower();
			flower.setFlower(ItemStack.EMPTY);
			this.setChanged();
			return stack;
		}
		return ItemStack.EMPTY;
	}

	protected List<FlowerContainerData> getFlowerData(){
		return this.flowers;
	}

	//region (De-)Serialization
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);

		if(!compound.contains("flowers")) return;
		ListTag flowers = compound.getList("flowers", CompoundTag.TAG_COMPOUND);

		for(int i = 0; i < flowers.size(); i++)
			if(!flowers.getCompound(i).isEmpty())
				this.flowers.get(i).deserialize(flowers.getCompound(i));
			else
				this.flowers.get(i).setFlower(ItemStack.EMPTY);


		this.requestModelDataUpdate();
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);

		ListTag flowers = new ListTag();
		for(FlowerContainerData flower : this.getFlowerData())
			flowers.add(flower.serialize());

		compound.put("flowers", flowers);
	}
	//endregion

	//region Server -> Client Sync
	@Override
	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag);
		return tag;
	}


	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt){
		this.load(pkt.getTag());
		requestModelDataUpdate();
		if(this.getLevel().isClientSide())
			ChunkRenderUtils.rerenderChunk((ClientLevel) this.getLevel(), this.getBlockPos());
	}
	//endregion

	@Override
	@Nonnull
	public IModelData getModelData() {
		return new BuiltinMixedModelData((List<IQuadProvider>)(List<?>)this.getFlowerData());
	}
}

