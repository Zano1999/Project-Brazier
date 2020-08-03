package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.projectbrazier.features.blocks.drawbridge.DrawbridgeAnchorTileEntityRenderer;
import net.dark_roleplay.projectbrazier.handler.MedievalBlocks;
import net.dark_roleplay.projectbrazier.handler.MedievalEntities;
import net.dark_roleplay.projectbrazier.handler.MedievalTileEntities;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntity;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.Map;

public class ProjectBrazierClient {

	public static void registerModelLoaders(){
	}

	public static void registerRenderLayers(){
		RenderTypeLookup.setRenderLayer(MedievalBlocks.HANGING_HORN.get(), RenderType.getCutout());

		//TODO Move to TER registration event?
		ClientRegistry.bindTileEntityRenderer(MedievalTileEntities.DRAWBRODGE_ANCHOR.get(), DrawbridgeAnchorTileEntityRenderer::new);

		RenderingRegistry.<SittableEntity>registerEntityRenderingHandler(MedievalEntities.SITTABLE.get(), SittableEntityRenderer::new);
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
//		MedievalItems.TIMBERING_NOTES.get().addPropertyOverride(new ResourceLocation(ProjectBrazier.MODID, "positions"), (stack, world, entity) -> {
//			CompoundNBT nbt = stack.getOrCreateTag();
//			float res = 0;
//			if (!nbt.contains("TimberingData")) return 0;
//			res += nbt.getCompound("TimberingData").contains("Start") ? 0.3 : 0;
//			res += nbt.getCompound("TimberingData").contains("Target") ? 0.6 : 0;
//			return res;
//		});
	}

}
