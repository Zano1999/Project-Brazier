package net.dark_roleplay.medieval.objects.guis.fourteen;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler_2.MedievalBlocks;
import net.dark_roleplay.medieval.objects.blocks.building.timbered_clay.TimberedClay;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.dark_roleplay.medieval.util.BlockPosUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TimberedArea {
    private World world;
    private BlockPos posA, posB;
    private boolean isZAxis = false;

    TimberedClayState[][] area;
    int width = 0, height = 0;

    public TimberedArea(World world, BlockPos posA, BlockPos posB){
        this.world = world;
        this.posA = BlockPosUtil.getMin(posA, posB);
        this.posB = BlockPosUtil.getMax(posA, posB);
        this.isZAxis = this.posA.getX() == this.posB.getX();
        this.width = (isZAxis ? this.posB.getZ() - this.posA.getZ() : this.posB.getX() - this.posA.getX()) + 1;
        this.height = this.posB.getY() - this.posA.getY() + 1;

        this.area = new TimberedClayState[width][height];
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(this.posA.getX(), this.posA.getY(), this.posA.getZ());
        for(int i = 0; i < width; i++){
            for(int y = 0; y < height; y++){
                BlockState state = world.getBlockState(pos);
                if(state.getBlock() == MedievalBlocks.TIMBERED_CLAY.get()){
                    area[i][y] =  new TimberedClayState();
                }
                pos.move(Direction.UP);
            }
            pos.move(this.isZAxis ? 0 : 1, -this.height, this.isZAxis ? 1 : 0);
        }
    }

    public static class TimberedClayState{
        private TimberedClayVariant primary = null;
        private TimberedClayVariant secondary = null;

        public void addType(TimberedClayVariant type){
            if(type.isEdge()){
                this.secondary = type;
            }else{
                this.primary = type;
            }
        }

        public TimberedClayVariant getPrimary(){
            return this.primary;
        }

        public boolean hasPrimary(){
            return this.primary != null;
        }

        public TimberedClayVariant getSecondary(){
            return this.secondary;
        }

        public boolean hasSecondary(){
            return this.secondary != null;
        }

        public void clear(){
            this.primary = null;
            this.secondary = null;
        }

        public static final ResourceLocation TYPES = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/timbered_clay/types.png");
        public static void setupTexture(){
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getTextureManager().bindTexture(TYPES);
        }
    }
}
