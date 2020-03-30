package net.dark_roleplay.medieval;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.medieval.handler.MedievalBlocks;
import net.dark_roleplay.medieval.handler.MedievalItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.Map;

public class DarkRoleplayMedievalClient {

	public static void registerRenderLayers(){
		setRenderLayer(RenderType.getCutout(), MedievalBlocks.ADVENT_WREATH, MedievalBlocks.BEESWAX_CANDLE);

		setRenderLayer(RenderType.getCutoutMipped(), MedievalBlocks.BARREL);
		setRenderLayer(RenderType.getCutoutMipped(),
				MedievalBlocks.ARROW_BOTTOM_TIMBERED_CLAY,
				MedievalBlocks.ARROW_LEFT_TIMBERED_CLAY,
				MedievalBlocks.ARROW_RIGHT_TIMBERED_CLAY,
				MedievalBlocks.ARROW_TOP_TIMBERED_CLAY,
				MedievalBlocks.BIRD_FOOT_BOTTOM_TIMBERED_CLAY,
				MedievalBlocks.BIRD_FOOT_LEFT_TIMBERED_CLAY,
				MedievalBlocks.BIRD_FOOT_RIGHT_TIMBERED_CLAY,
				MedievalBlocks.BIRD_FOOT_TOP_TIMBERED_CLAY,
				MedievalBlocks.CLEAN_TIMBERED_CLAY,
				MedievalBlocks.CROSS_TIMBERED_CLAY,
				MedievalBlocks.DIAGONAL_BT_TIMBERED_CLAY,
				MedievalBlocks.DIAGONAL_TB_TIMBERED_CLAY,
				MedievalBlocks.HORIZONTAL_TIMBERED_CLAY,
				MedievalBlocks.STRAIGHT_CROSS_TIMBERED_CLAY,
				MedievalBlocks.VERTICAL_TIMBERED_CLAY,

				MedievalBlocks.DOUBLE_DIAGONAL_B_BT_TIMBERED_CLAY,
				MedievalBlocks.DOUBLE_DIAGONAL_B_TB_TIMBERED_CLAY,
				MedievalBlocks.DOUBLE_DIAGONAL_L_LR_TIMBERED_CLAY,
				MedievalBlocks.DOUBLE_DIAGONAL_L_RL_TIMBERED_CLAY,
				MedievalBlocks.DOUBLE_DIAGONAL_R_LR_TIMBERED_CLAY,
				MedievalBlocks.DOUBLE_DIAGONAL_R_RL_TIMBERED_CLAY,
				MedievalBlocks.DOUBLE_DIAGONAL_T_BT_TIMBERED_CLAY,
				MedievalBlocks.DOUBLE_DIAGONAL_T_TB_TIMBERED_CLAY
		);

	}

	private static void setRenderLayer(RenderType type, Map<IMaterial, RegistryObject<Block>>... materialBlockObjects){
		for(Map<IMaterial, RegistryObject<Block>> materialBlocks : materialBlockObjects){
			for(RegistryObject<Block> blockObj : materialBlocks.values()){
				RenderTypeLookup.setRenderLayer(blockObj.get(), type);
			}
		}
	}

	private static void setRenderLayer(RenderType type, RegistryObject<Block>... blockObjects){
		for(RegistryObject<Block> blockObj : blockObjects){
			RenderTypeLookup.setRenderLayer(blockObj.get(), type);
		}
	}

	public static void registerItemOverrides(){
		MedievalItems.TIMBERING_NOTES.get().addPropertyOverride(new ResourceLocation(DarkRoleplayMedieval.MODID, "positions"), (stack, world, entity) -> {
			CompoundNBT nbt = stack.getOrCreateTag();
			float res = 0;
			if (!nbt.contains("TimberingData")) return 0;
			res += nbt.getCompound("TimberingData").contains("Start") ? 0.3 : 0;
			res += nbt.getCompound("TimberingData").contains("Target") ? 0.6 : 0;
			return res;
		});
	}

}
