package net.dark_roleplay.bedrock_entities.tester;

import net.dark_roleplay.medieval.holders.MedievalTileEntities;
import net.dark_roleplay.medieval.objects.blocks.utility.AnchorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ModelTesterTileEntity extends TileEntity{

    public ModelTesterTileEntity() {
        this(16);
    }

    public ModelTesterTileEntity(int length) {
        super(MedievalTileEntities.ANCHOR);
    }
}
