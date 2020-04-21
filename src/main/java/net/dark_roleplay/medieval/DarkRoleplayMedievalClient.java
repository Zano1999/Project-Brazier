package net.dark_roleplay.medieval;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.medieval.handler.MedievalItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.RegistryObject;

import java.util.Map;

public class DarkRoleplayMedievalClient {

	public static void registerModelLoaders(){
	}

	public static void registerRenderLayers(){

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
//		MedievalItems.TIMBERING_NOTES.get().addPropertyOverride(new ResourceLocation(DarkRoleplayMedieval.MODID, "positions"), (stack, world, entity) -> {
//			CompoundNBT nbt = stack.getOrCreateTag();
//			float res = 0;
//			if (!nbt.contains("TimberingData")) return 0;
//			res += nbt.getCompound("TimberingData").contains("Start") ? 0.3 : 0;
//			res += nbt.getCompound("TimberingData").contains("Target") ? 0.6 : 0;
//			return res;
//		});
	}

}
