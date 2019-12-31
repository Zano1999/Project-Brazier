package net.dark_roleplay.medieval.objects.blocks.building.roofs;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.IStringSerializable;

public class RoofRim extends HorizontalBlock {

    public static final EnumProperty<Variant> VARIANT = EnumProperty.create("variant", Variant.class);
    public static final EnumProperty<Placement> PLACEMENT = EnumProperty.create("placement", Placement.class);

    public RoofRim(Properties properties) {
        super(properties);
        this.setShapes(Block.makeCuboidShape(1, 1, 1, 15, 15,15));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(PLACEMENT);
        builder.add(VARIANT);
    }


    public enum Variant implements IStringSerializable {
        NORMAL,
        STEEP_T,
        STEEP_B,
        SHALLOW_T,
        SHALLOW_B;

        @Override
        public String getName() {
            return this.toString().toLowerCase();
        }
    }

    public enum Placement implements IStringSerializable {
        BOTTOM,
        LEFT,
        RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        BOTTOM_OUTER,
        BOTTOM_INNER;

        @Override
        public String getName() {
            return this.toString().toLowerCase();
        }
    }
}
