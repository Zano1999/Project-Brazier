package net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;

public class MixedTileEntity extends BlockEntity {
	
	public MixedTileEntity(BlockEntityType<?> tileEntityType) {
		super(tileEntityType);
	}

	@Nonnull
	@Override
	public IModelData getModelData(){
		return EmptyModelData.INSTANCE;
	}
}
