package net.dark_roleplay.projectbrazier.experimental_features.roofs;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

import net.minecraft.block.AbstractBlock.Properties;

public class RoofCornerBlock extends HFacedDecoBlock {

	public static final EnumProperty<RoofType> SEC_TYPE = EnumProperty.create("sec_type", RoofType.class, type -> type.doesGenerateCorners());

	public RoofCornerBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SEC_TYPE);
	}
}
