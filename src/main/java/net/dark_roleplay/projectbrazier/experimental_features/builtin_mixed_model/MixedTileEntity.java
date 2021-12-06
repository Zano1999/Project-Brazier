package net.dark_roleplay.projectbrazier.experimental_features.builtin_mixed_model;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;

public class MixedTileEntity extends BlockEntity {
	
	public MixedTileEntity(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
		super(tileEntityType, pos, state);
	}

	@Nonnull
	@Override
	public IModelData getModelData(){
		return EmptyModelData.INSTANCE;
	}
}
