package net.dark_roleplay.medieval.objects.blocks.building.roofs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RoofItem extends BlockItem {
    public RoofItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Nullable
    public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos().offset(context.getFace().getOpposite());

        World world = context.getWorld();
        BlockState blockstate = world.getBlockState(blockpos);
        Block block = this.getBlock();

        if (blockstate.getBlock() == block) {
            Direction direction = blockstate.get(BlockStateProperties.HORIZONTAL_FACING);

            Vec3d hitVec = context.getHitVec();

            BlockPos.Mutable mutablePos = new BlockPos.Mutable(blockpos);
            System.out.println(hitVec.getY());
            if(hitVec.getY() - blockpos.getY() > 0.75){
                mutablePos = mutablePos.move(direction.getOpposite()).move(Direction.UP);
            }else if(direction.getAxis() == Direction.Axis.X){
                float z = (float) (hitVec.getZ() - blockpos.getZ());
                if(z < 0.5){
                    mutablePos = mutablePos.move(Direction.NORTH);
                }else{
                    mutablePos = mutablePos.move(Direction.SOUTH);
                }
            }else if(direction.getAxis() == Direction.Axis.Z){
                float x = (float) (hitVec.getX() - blockpos.getX());
                if(x < 0.5){
                    mutablePos = mutablePos.move(Direction.WEST);
                }else{
                    mutablePos = mutablePos.move(Direction.EAST);
                }
            }

            if (!world.isRemote && !World.isValid(mutablePos)) {
                PlayerEntity playerentity = context.getPlayer();
                int j = world.getHeight();
                if (playerentity instanceof ServerPlayerEntity && mutablePos.getY() >= j) {
                    SChatPacket schatpacket = new SChatPacket((new TranslationTextComponent("build.tooHigh", j)).applyTextStyle(TextFormatting.RED), ChatType.GAME_INFO);
                    ((ServerPlayerEntity)playerentity).connection.sendPacket(schatpacket);
                }
            }

            blockstate = world.getBlockState(mutablePos);
            if (blockstate.getBlock() != this.getBlock()) {
                if (blockstate.isReplaceable(context)) {
                    return BlockItemUseContext.func_221536_a(context, mutablePos, direction);
                }
            }
            return null;
        }
        return super.getBlockItemUseContext(context);
    }
}
