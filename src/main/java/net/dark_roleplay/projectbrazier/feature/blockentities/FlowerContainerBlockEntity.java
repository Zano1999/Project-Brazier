package net.dark_roleplay.projectbrazier.feature.blockentities;

import com.google.common.collect.ImmutableList;
import net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel.BuiltinMixedModelData;
import net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel.IQuadProvider;
import net.dark_roleplay.projectbrazier.feature.blocks.FlowerContainerData;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature_client.blocks.CFlowerContainerHelper;
import net.dark_roleplay.projectbrazier.util.blocks.ChunkRenderUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FlowerContainerBlockEntity extends TileEntity {

	protected final int flowerCount;
	protected List<FlowerContainerData> flowers;

	public FlowerContainerBlockEntity() {
		this(3);
	}

	public FlowerContainerBlockEntity(int flowerCount) {
		super(BrazierBlockEntities.FLOWER_CONTAINER.get());
		this.flowerCount = flowerCount;

		this.flowers = DistExecutor.unsafeRunForDist(() -> () -> (List<FlowerContainerData>)(List<?>) CFlowerContainerHelper.createFlowerData(flowerCount), () -> () -> this.createFlowerData(flowerCount));
	}

	protected List<FlowerContainerData> createFlowerData(int flowerCount) {
		List<FlowerContainerData> flowers = new ArrayList<>(flowerCount);
		for(int i = 0; i < flowerCount; i++)
			flowers.add(0, new FlowerContainerData());
		return ImmutableList.copyOf(flowers);
	}

	public ItemStack addFlower(ItemStack stack, Vector3i offset){
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
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);

		if(!compound.contains("flowers")) return;
		ListNBT flowers = compound.getList("flowers", Constants.NBT.TAG_COMPOUND);

		for(int i = 0; i < flowers.size(); i++)
			if(!flowers.getCompound(i).isEmpty())
				this.flowers.get(i).deserialize(flowers.getCompound(i));
			else
				this.flowers.get(i).setFlower(ItemStack.EMPTY);


		this.requestModelDataUpdate();
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);

		ListNBT flowers = new ListNBT();
		for(FlowerContainerData flower : this.getFlowerData())
			flowers.add(flower.serialize());

		compound.put("flowers", flowers);
		return compound;
	}
	//endregion

	//region Server -> Client Sync
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.getBlockPos(), 1, this.save(new CompoundNBT()));
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}


	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
		this.load(null, pkt.getTag());
		requestModelDataUpdate();
		if(this.getLevel().isClientSide())
			ChunkRenderUtils.rerenderChunk((ClientWorld) this.getLevel(), this.getBlockPos());
	}
	//endregion

	@Override
	@Nonnull
	public IModelData getModelData() {
		return new BuiltinMixedModelData((List<IQuadProvider>)(List<?>)this.getFlowerData());
	}
}

