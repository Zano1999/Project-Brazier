package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.marg.api.materials.MaterialRequirement;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.building.jail_lattice.JailLatticeBlock;
import net.dark_roleplay.medieval.objects.blocks.building.platforms.WoodPlatformBlock;
import net.dark_roleplay.medieval.objects.blocks.building.platforms.WoodPlatformStairsBlock;
import net.dark_roleplay.medieval.objects.blocks.building.roofs.RoofBlock;
import net.dark_roleplay.medieval.objects.blocks.building.timbered_clay.TimberedClay;
import net.dark_roleplay.medieval.objects.blocks.building.wood_stairs.SimpleWoodStairs;
import net.dark_roleplay.medieval.objects.blocks.building.wooden_window.WoodenWindowBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.advent_wreath.AdventWreathBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.benches.BenchBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.candles.Candles;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.*;
import net.dark_roleplay.medieval.objects.blocks.decoration.light_sources.TorchHolderBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSign;
import net.dark_roleplay.medieval.objects.blocks.decoration.tables.AxisTable;
import net.dark_roleplay.medieval.objects.blocks.decoration.tables.BarrelTable;
import net.dark_roleplay.medieval.objects.blocks.decoration.tables.SimpleTable;
import net.dark_roleplay.medieval.objects.blocks.decoration.wall_brazier.WallBrazierBlock;
import net.dark_roleplay.medieval.objects.blocks.utility.*;
import net.dark_roleplay.medieval.objects.blocks.utility.barrel.BarrelBlock;
import net.dark_roleplay.medieval.objects.blocks.utility.chopping_block.ChoppingBlock;
import net.dark_roleplay.medieval.objects.enums.TimberedClayEnums.TimberedClayType;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MedievalBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DarkRoleplayMedieval.MODID);


    private static final Block.Properties woodBaseProperties = Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD);
    private static final Block.Properties woodDecoProperties = Block.Properties.create(Material.LEAVES).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD);

    private static final Block.Properties clothBaseProperties = Block.Properties.create(Material.WOOL).hardnessAndResistance(0.8F).sound(SoundType.CLOTH);
    private static final Block.Properties stoneBaseProperties = Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE);
    private static final Block.Properties metalBaseProperties = Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL);
    private static final Block.Properties PLACEHOLDER = Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL);

    private static final MaterialRequirement logMat = new MaterialRequirement("wood", "log", "log_top");

    private static final MaterialRequirement logMat2 = new MaterialRequirement("wood", "log", "log_top", "stripped_log");
    private static final MaterialRequirement plankMat = new MaterialRequirement("wood", "planks");
    private static final MaterialRequirement logPlankMat = new MaterialRequirement("wood", "log", "log_top", "planks");

    private static final Map<net.dark_roleplay.marg.api.materials.Material, Block.Properties> woodDeco = new HashMap<net.dark_roleplay.marg.api.materials.Material, Block.Properties>(){{
        put(net.dark_roleplay.marg.api.materials.Material.getMaterialForName("oak"), Block.Properties.create(new Material.Builder(MaterialColor.WOOD).build()));
    }};


    public static final RegistryObject<Block>
        ANDESITE_PILLAR             = BLOCKS.register("andesite_pillar", () -> new RotatedPillarBlock(stoneBaseProperties)),
        DIORITE_PILLAR              = BLOCKS.register("diorite_pillar", () -> new RotatedPillarBlock(stoneBaseProperties)),
        GRANITE_PILLAR              = BLOCKS.register("granite_pillar", () -> new RotatedPillarBlock(stoneBaseProperties)),
        ANDESITE_BRICKS             = BLOCKS.register("andesite_bricks", () -> new Block(stoneBaseProperties)),
        DIORITE_BRICKS              = BLOCKS.register("diorite_bricks", () -> new Block(stoneBaseProperties)),
        GRANITE_BRICKS              = BLOCKS.register("granite_bricks", () -> new Block(stoneBaseProperties)),
        SNOW_BRICKS                 = BLOCKS.register("snow_bricks", () -> new Block(PLACEHOLDER)),
        PACKED_ICE_BRICKS           = BLOCKS.register("packed_ice_bricks", () -> new Block(PLACEHOLDER)),

        TORCH_HOLDER                = BLOCKS.register("torch_holder", () -> new TorchHolderBlock(metalBaseProperties)),
        WALL_BRAZIER                = BLOCKS.register("wall_brazier", () -> new WallBrazierBlock(metalBaseProperties)),
        JAIL_LATTICE                = BLOCKS.register("jail_lattice", () -> new JailLatticeBlock(metalBaseProperties)),
        ROPE_ANCHOR                 = BLOCKS.register("rope_anchor", () -> new RopeAnchor(woodBaseProperties)),
        ROPE                        = BLOCKS.register("rope", () -> new Rope(clothBaseProperties)),
        OAK_ROPE_LADDER_ANCHOR      = BLOCKS.register("oak_rope_ladder_anchor", () -> new RopeLadderAnchor(woodBaseProperties)),
        OAK_ROPE_LADDER             = BLOCKS.register("oak_rope_ladder", () -> new RopeLadder(clothBaseProperties)),
        RIVERSTONE                  = BLOCKS.register("riverstone", () -> new Block(stoneBaseProperties)),
        LARGE_RIVERSTONE            = BLOCKS.register("large_riverstone", () -> new Block(stoneBaseProperties)),
        DARK_LARGE_RIVERSTONE       = BLOCKS.register("dark_large_riverstone", () -> new Block(stoneBaseProperties)),
        COLORFUL_COBBLESTONE        = BLOCKS.register("colorful_cobblestone", () -> new Block(stoneBaseProperties)),
        PALE_COLORFUL_COBBLESTONE   = BLOCKS.register("pale_colorful_cobblestone", () -> new Block(stoneBaseProperties)),
        BEESWAX_CANDLE              = BLOCKS.register("beeswax_candle", () -> new Candles(woodBaseProperties)),
        ADVENT_WREATH               = BLOCKS.register("advent_wreath", () -> new AdventWreathBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.PLANT))),
        TIMBERED_CLAY               = BLOCKS.register("clean_timbered_clay", () -> new Block(PLACEHOLDER)),
        SHINGLE_ROOF                = BLOCKS.register("clay_shingle_roof", () -> new RoofBlock(stoneBaseProperties));

    public static final Map<DyeColor, RegistryObject<Block>>
        COLORED_SHINGLE_ROOFS       = colorRegister("%s_clay_shingle_roof", () -> new RoofBlock(stoneBaseProperties));

    public static final Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Block>>
        BARREL                      = materialRegister(plankMat, "${material}_barrel", material -> () -> new BarrelBlock(woodDecoProperties, material)),
        WOOD_SHINGLE_ROOF           = materialRegister(plankMat, "${material}_shingle_roof", () -> new RoofBlock(woodBaseProperties)),
        //SHINGLE_ROOF_RIM            = materialRegister(plankMat, "${material}_shingle_roof_rim", () -> new RoofRim(woodBaseProperties)),
        CHOPPING_BLOCK              = materialRegister(logMat, "${material}_chopping_block", () -> new ChoppingBlock(woodBaseProperties)),
        SIMPLE_WOOD_STAIRS          = materialRegister(logPlankMat, "simple_${material}_stairs", () -> new SimpleWoodStairs(woodBaseProperties)),
        VERTICAL_WOOD_WINDOW        = materialRegister(plankMat, "vertical_${material}_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        DENSE_VERTICAL_WOOD_WINDOW  = materialRegister(plankMat, "dense_vertical_${material}_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        CROSS_WOOD_WINDOW           = materialRegister(plankMat, "cross_${material}_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        GRID_WOOD_WINDOW            = materialRegister(plankMat, "grid_${material}_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        DIAMOND_WOOD_WINDOW         = materialRegister(plankMat, "diamond_${material}_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        BARREL_TABLE                = materialRegister(plankMat, "${material}_barrel_table", () -> new BarrelTable(woodBaseProperties)),
        PLANK_WOOD_TABLE            = materialRegister(plankMat, "${material}_plank_table", () -> new AxisTable(woodBaseProperties)),
        SOLID_WOOD_TABLE            = materialRegister(plankMat, "solid_${material}_table", () -> new SimpleTable(woodBaseProperties)),
        LOG_CHAIR                   = materialRegister(logMat2, "${material}_log_chair", () -> new LogChairBlock(woodBaseProperties)), //TODO FIx hidbox
        BARREL_CHAIR                = materialRegister(plankMat, "${material}_barrel_chair", () -> new BarrelChairBlock(woodBaseProperties)),
        WOOD_PLANK_CHAIR            = materialRegister(plankMat, "${material}_plank_chair", () -> new PlankChairBlock(woodBaseProperties)),
        WOOD_SOLID_CHAIR            = materialRegister(plankMat, "${material}_solid_chair", () -> new SolidChairBlock(woodBaseProperties)),
        WOOD_SOLID_BENCH            = materialRegister(plankMat, "${material}_solid_bench", () -> new BenchBlock(woodBaseProperties)),
        WOOD_PLATFORM               = materialRegister(plankMat, "${material}_platform", () -> new WoodPlatformBlock(woodBaseProperties)),
        WOOD_PLATFORM_STAIRS        = materialRegister(plankMat, "${material}_platform_stairs", () -> new WoodPlatformStairsBlock(woodBaseProperties)),
        WOOD_SIGN_POST              = materialRegister(plankMat, "${material}_sign_post", () -> new RoadSign(woodBaseProperties)), //Needs TE
        WOOD_SOLID_ARMREST_CHAIR    = materialRegister(plankMat, "${material}_solid_chair_armrest", () -> new SolidChairArmrestBlock(woodBaseProperties)), //Needs TE
        CLEAN_TIMBERED_CLAY         = materialRegister(plankMat, "clean_${material}_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.CLEAN)),
        VERTICAL_TIMBERED_CLAY      = materialRegister(plankMat, "${material}_vertical_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.VERTICAL)),
        HORIZONTAL_TIMBERED_CLAY    = materialRegister(plankMat, "${material}_horizontal_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.HORIZONTAL)),
        DIAGONAL_BT_TIMBERED_CLAY   = materialRegister(plankMat, "${material}_diagonal_bt_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DIAGONAL_BT)),
        DIAGONAL_TB_TIMBERED_CLAY   = materialRegister(plankMat, "${material}_diagonal_tb_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DIAGONAL_TB)),
        CROSS_TIMBERED_CLAY         = materialRegister(plankMat, "${material}_cross_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.CROSS)),
        ARROW_BOTTOM_TIMBERED_CLAY  = materialRegister(plankMat, "${material}_arrow_bottom_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.ARROW_BOTTOM)),
        ARROW_TOP_TIMBERED_CLAY     = materialRegister(plankMat, "${material}_arrow_top_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.ARROW_TOP)),
        ARROW_RIGHT_TIMBERED_CLAY   = materialRegister(plankMat, "${material}_arrow_right_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.ARROW_RIGHT)),
        ARROW_LEFT_TIMBERED_CLAY    = materialRegister(plankMat, "${material}_arrow_left_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.ARROW_LEFT)),
        STRAIGHT_CROSS_TIMBERED_CLAY = materialRegister(plankMat, "${material}_straight_cross_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.STRAIGHT_CROSS)),
        DOUBLE_DIAGONAL_T_BT_TIMBERED_CLAY = materialRegister(plankMat, "${material}_double_diagonal_t_bt_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_T_BT)),
        DOUBLE_DIAGONAL_B_BT_TIMBERED_CLAY = materialRegister(plankMat, "${material}_double_diagonal_b_bt_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_B_BT)),
        DOUBLE_DIAGONAL_T_TB_TIMBERED_CLAY = materialRegister(plankMat, "${material}_double_diagonal_t_tb_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_T_TB)),
        DOUBLE_DIAGONAL_B_TB_TIMBERED_CLAY = materialRegister(plankMat, "${material}_double_diagonal_b_tb_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_B_TB)),
        DOUBLE_DIAGONAL_L_LR_TIMBERED_CLAY = materialRegister(plankMat, "${material}_double_diagonal_l_lr_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_L_LR)),
        DOUBLE_DIAGONAL_R_LR_TIMBERED_CLAY = materialRegister(plankMat, "${material}_double_diagonal_r_lr_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_R_LR)),
        DOUBLE_DIAGONAL_L_RL_TIMBERED_CLAY = materialRegister(plankMat, "${material}_double_diagonal_l_rl_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_L_RL)),
        DOUBLE_DIAGONAL_R_RL_TIMBERED_CLAY = materialRegister(plankMat, "${material}_double_diagonal_r_rl_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_R_RL));

    private static Map<DyeColor, RegistryObject<Block>> colorRegister(String name, Supplier<Block> suplier){
        Map<DyeColor, RegistryObject<Block>> blocks = new EnumMap<>(DyeColor.class);

        for(DyeColor color : DyeColor.values()){
            blocks.put(color, BLOCKS.register(String.format(name, color.getName()), suplier));
        }

        return blocks;
    }

    private static Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Block>> materialRegister(MaterialRequirement matGetter, String name, Function<net.dark_roleplay.marg.api.materials.Material, Supplier<Block>> func){
        Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Block>> blocks = new HashMap<>();

        matGetter.execute(material -> {
            blocks.put(material, BLOCKS.register(material.getTextProv().searchAndReplace(name), func.apply(material)));
        });

        return blocks;
    }


    private static Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Block>> materialRegister(MaterialRequirement matGetter, String name, Supplier<Block> suplier){
        Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Block>> blocks = new HashMap<>();

        matGetter.execute(material -> {
            blocks.put(material, BLOCKS.register(material.getTextProv().searchAndReplace(name), suplier));
        });

        return blocks;
    }


}
