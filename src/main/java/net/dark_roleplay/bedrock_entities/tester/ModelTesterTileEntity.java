package net.dark_roleplay.bedrock_entities.tester;

import net.dark_roleplay.medieval.handler_2.MedievalTileEntities;
import net.minecraft.tileentity.TileEntity;

public class ModelTesterTileEntity extends TileEntity{

    public ModelTesterTileEntity() {
        this(16);
    }

    public ModelTesterTileEntity(int length) {
        super(MedievalTileEntities.ANCHOR.get());
    }
}
