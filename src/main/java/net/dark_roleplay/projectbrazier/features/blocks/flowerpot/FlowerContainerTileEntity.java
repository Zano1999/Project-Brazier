package net.dark_roleplay.projectbrazier.features.blocks.flowerpot;

import com.google.common.collect.ImmutableList;
import net.dark_roleplay.projectbrazier.experiments.BultinMixedModel.BuiltinMixedModelData;
import net.dark_roleplay.projectbrazier.experiments.BultinMixedModel.IQuadProvider;
import net.dark_roleplay.projectbrazier.handler.MedievalTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FlowerContainerTileEntity extends TileEntity {

	protected final int flowerCount;
	protected List<FlowerData> flowers;

	public FlowerContainerTileEntity() {
		this(3);
	}

	public FlowerContainerTileEntity(int flowerCount) {
		super(MedievalTileEntities.FLOWER_CONTAINER.get());
		this.flowerCount = flowerCount;

		this.flowers = DistExecutor.unsafeRunForDist(() -> () -> (List<FlowerData>)(List<?>)ClientFlowerContainerHelper.createFlowerData(flowerCount), () -> () -> this.createFlowerData(flowerCount));
	}

	protected List<FlowerData> createFlowerData(int flowerCount) {
		List<FlowerData> flowers = new ArrayList<>(flowerCount);
		for(int i = 0; i < flowerCount; i++)
			flowers.add(0, new FlowerData());
		return ImmutableList.copyOf(flowers);
	}

	public ItemStack addFlower(ItemStack stack){
		for(int i = 0; i < flowers.size(); i++){
			FlowerData flower = flowers.get(i);
			if(flower.getFlower().isEmpty()){
				ItemStack copy = stack.copy();
				copy.setCount(1);
				flower.setFlower(copy);
				stack.shrink(1);
				this.markDirty();
				break;
			}
		}
		return stack;
	}

	public ItemStack removeFlower(){
		for(int i = flowers.size()-1; i >= 0; i--){
			FlowerData flower = flowers.get(i);
			if(flower.getFlower().isEmpty()) continue;
			ItemStack stack = flower.getFlower();
			flower.setFlower(ItemStack.EMPTY);
			this.markDirty();
			return stack;
		}
		return ItemStack.EMPTY;
	}

	protected List<FlowerData> getFlowerData(){
		return this.flowers;
	}

	//region (De-)Serialization
	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);

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
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);

		ListNBT flowers = new ListNBT();
		for(FlowerData flower : this.getFlowerData())
			flowers.add(flower.serialize());

		compound.put("flowers", flowers);
		return compound;
	}
	//endregion

	//region Server -> Client Sync
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.getPos(), 1, this.write(new CompoundNBT()));
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}


	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
		this.read(null, pkt.getNbtCompound());
	}
	//endregion

	@Override
	@Nonnull
	public IModelData getModelData() {
		return new BuiltinMixedModelData((List<IQuadProvider>)(List<?>)this.getFlowerData());
	}
}

