package net.dark_roleplay.medieval.handler_2;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.building.jail_lattice.JailLatticeBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.advent_wreath.AdventWreathBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.light_sources.TorchHolderBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.wall_brazier.WallBrazierBlock;
import net.dark_roleplay.medieval.objects.blocks.utility.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DarkRoleplayMedieval.MODID);

    private static final Block.Properties woodBaseProperties = Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD);
    private static final Block.Properties clothBaseProperties = Block.Properties.create(Material.WOOL).hardnessAndResistance(0.8F).sound(SoundType.CLOTH);
    private static final Block.Properties stoneBaseProperties = Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE);
    private static final Block.Properties metalBaseProperties = Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL);
    private static final Block.Properties PLACEHOLDER = Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL);

    public static final RegistryObject<Block>
        TORCH_HOLDER = BLOCKS.register("torch_holder", () -> new TorchHolderBlock(metalBaseProperties)),
        WALL_BRAZIER = BLOCKS.register("wall_brazier", () -> new WallBrazierBlock(metalBaseProperties)),
        JAIL_LATTICE = BLOCKS.register("jail_lattice", () -> new JailLatticeBlock(metalBaseProperties)),

        ROPE_ANCHOR = BLOCKS.register("rope_anchor", () -> new RopeAnchor(woodBaseProperties)),
        ROPE = BLOCKS.register("rope", () -> new Rope(clothBaseProperties)),
        OAK_ROPE_LADDER_ANCHOR = BLOCKS.register("oak_rope_ladder_anchor", () -> new RopeLadderAnchor(woodBaseProperties)),
        OAK_ROPE_LADDER = BLOCKS.register("oak_rope_ladder", () -> new RopeLadder(clothBaseProperties)),

        RIVERSTONE = BLOCKS.register("riverstone", () -> new Block(stoneBaseProperties)),
        LARGE_RIVERSTONE = BLOCKS.register("large_riverstone", () -> new Block(stoneBaseProperties)),
        LARGE_DARK_RIFVERSTONE= BLOCKS.register("dark_large_riverstone", () -> new Block(stoneBaseProperties)),
        COLORFUL_COBBLESTONE = BLOCKS.register("colorful_cobblestone", () -> new Block(stoneBaseProperties)),
        PALE_COLORFUL_COBBLESTONE = BLOCKS.register("pale_colorful_cobblestone", () -> new Block(stoneBaseProperties)),

        ADVENT_WREATH = BLOCKS.register("advent_wreath", () -> new AdventWreathBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.PLANT))),
        TIMBERED_CLAY = BLOCKS.register("clean_timbered_clay", () -> new Block(PLACEHOLDER));


}
