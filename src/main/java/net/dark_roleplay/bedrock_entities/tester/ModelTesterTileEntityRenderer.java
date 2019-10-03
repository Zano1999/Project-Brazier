package net.dark_roleplay.bedrock_entities.tester;

import net.dark_roleplay.bedrock_entities.Skeleton;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

public class ModelTesterTileEntityRenderer extends TileEntityRenderer<ModelTesterTileEntity> {

    private int reloadIn = 500;

    Skeleton skeleton;

    @Override
    public void render(ModelTesterTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        if(reloadIn <= 0){
            //skeleton = new Skeleton();
            reloadIn = 5000;
        }
        reloadIn -= 1;
    }
}
