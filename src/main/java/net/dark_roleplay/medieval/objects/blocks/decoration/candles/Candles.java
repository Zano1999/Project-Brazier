package net.dark_roleplay.medieval.objects.blocks.decoration.candles;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Candles extends Block {

    public Candles(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return makeCuboidShape(0, 0, 0, 5, 5, 5);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        Vec3d particleMarker = null;//MarkerModelLoader.markers.get("particles");
        if(particleMarker == null) return;
        world.addParticle(ParticleTypes.SMOKE, pos.getX() + particleMarker.x, pos.getY() + particleMarker.y + 0.0625F * 3, pos.getZ() + particleMarker.z, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, pos.getX() + particleMarker.x, pos.getY() + particleMarker.y + 0.0625F * 3, pos.getZ() + particleMarker.z, 0, 0, 0);
    }
}
