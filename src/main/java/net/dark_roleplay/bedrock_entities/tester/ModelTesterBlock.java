package net.dark_roleplay.bedrock_entities.tester;

import net.dark_roleplay.medieval.objects.blocks.utility.AnchorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ModelTesterBlock extends Block {

    public ModelTesterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state)    {
        return true;
    }

    @Nullable
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new ModelTesterTileEntity();
    }
}
