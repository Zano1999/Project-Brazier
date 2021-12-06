package net.dark_roleplay.projectbrazier.experimental_features.roofs;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;

public class RoofCornerBlock extends HFacedDecoBlock {

	public static final EnumProperty<RoofType> SEC_TYPE = EnumProperty.create("sec_type", RoofType.class, type -> type.doesGenerateCorners());

	public RoofCornerBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SEC_TYPE);
	}
}
