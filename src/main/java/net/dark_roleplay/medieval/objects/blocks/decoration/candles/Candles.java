package net.dark_roleplay.medieval.objects.blocks.decoration.candles;

import net.dark_roleplay.medieval.handler_2.MedievalItems;
import net.dark_roleplay.medieval.util.blocks.VoxelShapeHelper;
import net.dark_roleplay.modelz.locator_loader.LocatorModelLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class Candles extends Block {

    protected static final IntegerProperty COUNT = IntegerProperty.create("count", 1, 4);

    private static VoxelShape[][] SHAPES = new VoxelShape[4][];

    static{
        VoxelShape[] shapes = new VoxelShape[]{
            Block.makeCuboidShape(6.5, 0, 6.5, 9.5, 6, 9.5),
            VoxelShapes.combineAndSimplify(Block.makeCuboidShape(8.5, 0, 4.5, 11.5, 6, 7.5), Block.makeCuboidShape(5.6, 0, 9.2, 8, 4.8, 11.6), IBooleanFunction.OR),
            Stream.of(
                    Block.makeCuboidShape(9.5, 0, 3.5, 12.5, 6, 6.5),
                    Block.makeCuboidShape(7.6, 0, 9.2, 10, 4.8, 11.6),
                    Block.makeCuboidShape(5.3, 0, 5.3, 8, 5.4, 8)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            Stream.of(
                    Block.makeCuboidShape(9.5, 0, 3.5, 12.5, 6, 6.5),
                    Block.makeCuboidShape(10.6, 0, 9.2, 13, 4.8, 11.6),
                    Block.makeCuboidShape(5.3, 0, 10.3, 8, 5.4, 13),
                    Block.makeCuboidShape(3.44, 0, 4.26, 6.02, 5.16, 6.84)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
        };
        SHAPES[0] = shapes;
        SHAPES[1] = new VoxelShape[]{
            VoxelShapeHelper.rotateShape(shapes[0], Direction.EAST),
            VoxelShapeHelper.rotateShape(shapes[1], Direction.EAST),
            VoxelShapeHelper.rotateShape(shapes[2], Direction.EAST),
            VoxelShapeHelper.rotateShape(shapes[3], Direction.EAST)
        };
        SHAPES[2] = new VoxelShape[]{
            VoxelShapeHelper.rotateShape(shapes[0], Direction.SOUTH),
            VoxelShapeHelper.rotateShape(shapes[1], Direction.SOUTH),
            VoxelShapeHelper.rotateShape(shapes[2], Direction.SOUTH),
            VoxelShapeHelper.rotateShape(shapes[3], Direction.SOUTH)
        };
        SHAPES[3] = new VoxelShape[]{
            VoxelShapeHelper.rotateShape(shapes[0], Direction.WEST),
            VoxelShapeHelper.rotateShape(shapes[1], Direction.WEST),
            VoxelShapeHelper.rotateShape(shapes[2], Direction.WEST),
            VoxelShapeHelper.rotateShape(shapes[3], Direction.WEST)
        };
    }

    public Candles(Properties properties) {
        super(properties);
    }


    @Override
    @Deprecated
    public int getLightValue(BlockState state) {
        return 10 + state.get(COUNT);
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
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Vec3d offset = state.getOffset(world, pos);
        WeightedRandom.Item dir = WeightedRandom.getRandomItem(weights, Math.abs((int)new Random(state.getPositionRandom(pos)).nextLong()) % 4);

        return SHAPES[dir == rndN ? 0 : dir == rndE ? 1 : dir == rndS ? 2 : 3][state.get(COUNT) - 1].withOffset(offset.x, offset.y, offset.z);
    }

    @Override
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(player.getHeldItem(hand).getItem() == MedievalItems.BEESWAX_CANDLE.get()){
            if(state.get(COUNT) < 4) {
                world.setBlockState(pos, state.with(COUNT, state.get(COUNT) + 1));

                if (!player.isCreative())
                    player.getHeldItem(hand).shrink(1);

                return true;
            }
        }
        return false;
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
