package net.dark_roleplay.medieval.objects.blocks.decoration.candles;

import net.dark_roleplay.modelz.locator_loader.LocatorModelLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Candles extends Block {

    protected static final IntegerProperty COUNT = IntegerProperty.create("count", 1, 4);

    public Candles(Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(COUNT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (!Block.func_220064_c(context.getWorld(), context.getPos().down()))
            return Blocks.AIR.getDefaultState();

        return this.getDefaultState().with(COUNT, 1);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    //TODO HANDLE HITBOX PROPERLY
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return makeCuboidShape(0, 0, 0, 5, 5, 5);
    }

    @Override
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        Map<String, Vec3d[]> locators = LocatorModelLoader.getLocators(new ResourceLocation("drpmedieval:beeswax_candles_" + state.get(COUNT)));
        Vec3d[] particleLocs = locators.get("particles");
        if(particleLocs == null) return;
        for (Vec3d vec : particleLocs) {
            spawnParticleRotated(world, ParticleTypes.SMOKE, vec, state, pos, true);
            spawnParticleRotated(world, ParticleTypes.FLAME, vec, state, pos, true);
        }
    }

    private static WeightedRandom.Item
            rndN = new WeightedRandom.Item(1),
            rndE = new WeightedRandom.Item(1),
            rndS = new WeightedRandom.Item(1),
            rndW = new WeightedRandom.Item(1);

    private static List<WeightedRandom.Item> weights = new ArrayList(){{add(rndN); add(rndE); add(rndS); add(rndW);}};

    public void spawnParticleRotated(World world, IParticleData particle, Vec3d vec, BlockState state, BlockPos pos, boolean rotate){
        Vec3d offset = state.getOffset(world, pos);
        if(!rotate)
            world.addParticle(particle, pos.getX() + vec.x + offset.x, pos.getY() + vec.y, pos.getZ() + vec.z + offset.z, 0, 0 ,0);
        else{
            WeightedRandom.Item dir = WeightedRandom.getRandomItem(weights, Math.abs((int)new Random(state.getPositionRandom(pos)).nextLong()) % 4);
            if(dir == rndN){
                world.addParticle(particle, pos.getX() + vec.x + offset.x, pos.getY() + vec.y, pos.getZ() + vec.z + offset.z, 0, 0 ,0);
            }else if(dir == rndE){
                world.addParticle(particle, pos.getX() + 1 - vec.z + offset.x, pos.getY() + vec.y, pos.getZ() + vec.x + offset.z, 0, 0 ,0);
            }else if(dir == rndS){
                world.addParticle(particle, pos.getX() + 1 - vec.x + offset.x, pos.getY() + vec.y, pos.getZ() + 1 - vec.z + offset.z, 0, 0 ,0);
            }else if(dir == rndW){
                world.addParticle(particle, pos.getX() + vec.z + offset.x, pos.getY() + vec.y, pos.getZ() + 1 - vec.x + offset.z, 0, 0 ,0);
            }
        }
    }
}
