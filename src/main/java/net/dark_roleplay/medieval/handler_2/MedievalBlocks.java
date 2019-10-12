package net.dark_roleplay.medieval.handler_2;

import net.dark_roleplay.marg.api.Constants;
import net.dark_roleplay.marg.api.MaterialRequirements;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.building.jail_lattice.JailLatticeBlock;
import net.dark_roleplay.medieval.objects.blocks.building.platforms.WoodPlatformBlock;
import net.dark_roleplay.medieval.objects.blocks.building.platforms.WoodPlatformStairsBlock;
import net.dark_roleplay.medieval.objects.blocks.building.timbered_clay.TimberedClay;
import net.dark_roleplay.medieval.objects.blocks.building.wood_stairs.SimpleWoodStairs;
import net.dark_roleplay.medieval.objects.blocks.building.wooden_window.WoodenWindowBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.advent_wreath.AdventWreathBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.benches.BenchBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.PlankChairBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairArmrestBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.light_sources.TorchHolderBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSign;
import net.dark_roleplay.medieval.objects.blocks.decoration.tables.SimpleTable;
import net.dark_roleplay.medieval.objects.blocks.decoration.wall_brazier.WallBrazierBlock;
import net.dark_roleplay.medieval.objects.blocks.utility.*;
import net.dark_roleplay.medieval.objects.blocks.utility.chopping_block.ChoppingBlock;
import net.dark_roleplay.medieval.objects.enums.TimberedClayEnums.TimberedClayType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MedievalBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DarkRoleplayMedieval.MODID);

    private static final Block.Properties woodBaseProperties = Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD);
    private static final Block.Properties clothBaseProperties = Block.Properties.create(Material.WOOL).hardnessAndResistance(0.8F).sound(SoundType.CLOTH);
    private static final Block.Properties stoneBaseProperties = Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE);
    private static final Block.Properties metalBaseProperties = Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL);
    private static final Block.Properties PLACEHOLDER = Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL);

    private static final MaterialRequirements logMat = new MaterialRequirements(Constants.MAT_WOOD, Constants.MatWood.LOG_SIDE, Constants.MatWood.LOG_TOP);
    private static final MaterialRequirements plankMat = new MaterialRequirements(Constants.MAT_WOOD, "planks");
    private static final MaterialRequirements logPlankMat = new MaterialRequirements(Constants.MAT_WOOD, Constants.MatWood.LOG_SIDE, Constants.MatWood.LOG_TOP, "planks");

    public static final RegistryObject<Block>
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
        ADVENT_WREATH               = BLOCKS.register("advent_wreath", () -> new AdventWreathBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.PLANT))),
        TIMBERED_CLAY               = BLOCKS.register("clean_timbered_clay", () -> new Block(PLACEHOLDER));

    public static final Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Block>>
        CHOPPING_BLOCK              = materialRegister(logMat, "%wood%_chopping_block", () -> new ChoppingBlock(woodBaseProperties)),
        SIMPLE_WOOD_STAIRS          = materialRegister(logPlankMat, "simple_%wood%_stairs", () -> new SimpleWoodStairs(woodBaseProperties)),
        VERTICAL_WOOD_WINDOW        = materialRegister(plankMat, "vertical_%wood%_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        DENSE_VERTICAL_WOOD_WINDOW  = materialRegister(plankMat, "dense_vertical_%wood%_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        CROSS_WOOD_WINDOW           = materialRegister(plankMat, "cross_%wood%_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        GRID_WOOD_WINDOW            = materialRegister(plankMat, "grid_%wood%_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        DIAMOND_WOOD_WINDOW         = materialRegister(plankMat, "diamond_%wood%_window", () -> new WoodenWindowBlock(woodBaseProperties)),
        SOLID_WOOD_TABLE            = materialRegister(plankMat, "solid_%wood%_table", () -> new SimpleTable(woodBaseProperties)),
        WOOD_PLANK_CHAIR            = materialRegister(plankMat, "%wood%_plank_chair", () -> new PlankChairBlock(woodBaseProperties)),
        WOOD_SOLID_CHAIR            = materialRegister(plankMat, "%wood%_solid_chair", () -> new SolidChairBlock(woodBaseProperties)),
        WOOD_SOLID_BENCH            = materialRegister(plankMat, "%wood%_solid_bench", () -> new BenchBlock(woodBaseProperties)),
        WOOD_PLATFORM               = materialRegister(plankMat, "%wood%_platform", () -> new WoodPlatformBlock(woodBaseProperties)),
        WOOD_PLATFORM_STAIRS        = materialRegister(plankMat, "%wood%_platform_stairs", () -> new WoodPlatformStairsBlock(woodBaseProperties)),
        WOOD_ROAD_SIGN              = materialRegister(plankMat, "%wood%_road_sign", () -> new RoadSign(woodBaseProperties)), //Needs TE
        WOOD_SOLID_ARMREST_CHAIR    = materialRegister(plankMat, "%wood%_solid_chair_armrest", () -> new SolidChairArmrestBlock(woodBaseProperties)), //Needs TE
        CLEAN_TIMBERED_CLAY         = materialRegister(plankMat, "clean_%wood%_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.CLEAN)),
        VERTICAL_TIMBERED_CLAY      = materialRegister(plankMat, "%wood%_vertical_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.VERTICAL)),
        HORIZONTAL_TIMBERED_CLAY    = materialRegister(plankMat, "%wood%_horizontal_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.HORIZONTAL)),
        DIAGONAL_BT_TIMBERED_CLAY   = materialRegister(plankMat, "%wood%_diagonal_bt_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DIAGONAL_BT)),
        DIAGONAL_TB_TIMBERED_CLAY   = materialRegister(plankMat, "%wood%_diagonal_tb_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DIAGONAL_TB)),
        CROSS_TIMBERED_CLAY         = materialRegister(plankMat, "%wood%_cross_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.CROSS)),
        ARROW_BOTTOM_TIMBERED_CLAY  = materialRegister(plankMat, "%wood%_arrow_bottom_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.ARROW_BOTTOM)),
        ARROW_TOP_TIMBERED_CLAY     = materialRegister(plankMat, "%wood%_arrow_top_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.ARROW_TOP)),
        ARROW_RIGHT_TIMBERED_CLAY   = materialRegister(plankMat, "%wood%_arrow_right_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.ARROW_RIGHT)),
        ARROW_LEFT_TIMBERED_CLAY    = materialRegister(plankMat, "%wood%_arrow_left_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.ARROW_LEFT)),
        STRAIGHT_CROSS_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_straight_cross_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.STRAIGHT_CROSS)),
        DOUBLE_DIAGONAL_T_BT_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_double_diagonal_t_bt_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_T_BT)),
        DOUBLE_DIAGONAL_B_BT_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_double_diagonal_b_bt_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_B_BT)),
        DOUBLE_DIAGONAL_T_TB_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_double_diagonal_t_tb_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_T_TB)),
        DOUBLE_DIAGONAL_B_TB_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_double_diagonal_b_tb_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_B_TB)),
        DOUBLE_DIAGONAL_L_LR_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_double_diagonal_l_lr_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_L_LR)),
        DOUBLE_DIAGONAL_R_LR_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_double_diagonal_r_lr_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_R_LR)),
        DOUBLE_DIAGONAL_L_RL_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_double_diagonal_l_rl_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_L_RL)),
        DOUBLE_DIAGONAL_R_RL_TIMBERED_CLAY = materialRegister(plankMat, "%wood%_double_diagonal_r_rl_timbered_clay", () -> new TimberedClay(woodBaseProperties, TimberedClayType.DOUBLE_DIAGONAL_R_RL));

    private static Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Block>> materialRegister(MaterialRequirements matGetter, String name, Supplier<Block> suplier){
        Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Block>> blocks = new HashMap<>();

        matGetter.execute(material -> {
            blocks.put(material, BLOCKS.register(material.getNamed(name), suplier));
        });

        return blocks;
    }


}
