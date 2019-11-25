package net.dark_roleplay.medieval.handler_2;

import net.dark_roleplay.bedrock_entities.tester.ModelTesterTileEntity;
import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.building.roofs.RoofTileEntity;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairTileEntity;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntity;
import net.dark_roleplay.medieval.objects.blocks.utility.AnchorTileEntity;
import net.dark_roleplay.medieval.objects.blocks.utility.barrel.BarrelTileEntity;
import net.dark_roleplay.medieval.objects.blocks.utility.chopping_block.ChoppingTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MedievalTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, DarkRoleplayMedieval.MODID);

    public static final RegistryObject<TileEntityType<?>>
        SHINGLE_ROOF            = TILE_ENTITIES.register("shingle_roof", () -> createType(RoofTileEntity::new, RoofTileEntity.class, MedievalBlocks.SHINGLE_ROOF)),
        BARREL                  = TILE_ENTITIES.register("barrel", () -> createType(BarrelTileEntity::new, BarrelTileEntity.class)),
        SOLID_CHAIR             = TILE_ENTITIES.register("solid_chair_armrest", () -> createType(SolidChairTileEntity::new, SolidChairTileEntity.class)),
        ROAD_SIGN               = TILE_ENTITIES.register("road_sign", () -> createType(RoadSignTileEntity::new, RoadSignTileEntity.class, MedievalBlocks.WOOD_SIGN_POST)),
        ANCHOR                  = TILE_ENTITIES.register("anchor", () -> createType(AnchorTileEntity::new, AnchorTileEntity.class)),
        MODEL_TESTER            = TILE_ENTITIES.register("model_tester", () -> createType(ModelTesterTileEntity::new, ModelTesterTileEntity.class)),
        CHOPPING_BLOCK          = TILE_ENTITIES.register("chopping_block", () -> createType(ChoppingTileEntity::new, ChoppingTileEntity.class));


    protected static <T extends TileEntity> TileEntityType createType(Supplier<T> supplier, Class<T> teClass){
        //Set<Block> blocks = teBlocks.get(teClass);
        //if(blocks != null)
        //    return TileEntityType.Builder.create(supplier, blocks.toArray(new Block[blocks.size()])).build(null);
        //else
            return TileEntityType.Builder.create(supplier).build(null);
    }

    protected static <T extends TileEntity> TileEntityType createType(Supplier<T> supplier, Class<T> teClass, Map<Material, RegistryObject<Block>> blocks){
        return TileEntityType.Builder.create(supplier, blocks.values().stream().map(ro -> (Block)ro.get()).collect(Collectors.toList()).toArray(new Block[blocks.size()])).build(null);
    }
}
