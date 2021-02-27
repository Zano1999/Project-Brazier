package net.dark_roleplay.projectbrazier.experiments.BultinMixedModel;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;

public class MixedTileEntity extends TileEntity {
	
	public MixedTileEntity(TileEntityType<?> tileEntityType) {
		super(tileEntityType);
	}

	@Nonnull
	@Override
	public IModelData getModelData(){
		return EmptyModelData.INSTANCE;
	}
}
