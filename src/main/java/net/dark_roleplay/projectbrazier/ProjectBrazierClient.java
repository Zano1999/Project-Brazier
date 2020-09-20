package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.projectbrazier.features.blocks.drawbridge.DrawbridgeAnchorTileEntityRenderer;
import net.dark_roleplay.projectbrazier.features.model_loaders.axis_connected_models.AxisConnectedModel;
import net.dark_roleplay.projectbrazier.features.model_loaders.emissive.EmissiveModel;
import net.dark_roleplay.projectbrazier.features.model_loaders.simple_pane_conneted_model.SimplePaneConnectedModel;
import net.dark_roleplay.projectbrazier.handler.MedievalBlocks;
import net.dark_roleplay.projectbrazier.handler.MedievalEntities;
import net.dark_roleplay.projectbrazier.handler.MedievalKeybinds;
import net.dark_roleplay.projectbrazier.handler.MedievalTileEntities;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntity;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

public class ProjectBrazierClient {

	public static void modConstructor(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::setupClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::registerModelLoaders);
	}

	public static void setupClientStuff(FMLClientSetupEvent event) {
		MedievalKeybinds.registerKeybinds(event);
		ProjectBrazierClient.registerRenderLayers();
	}

	public static void registerModelLoaders(ModelRegistryEvent event){
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "simple_pane_connected_model"), new SimplePaneConnectedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "axis_connected_model"), new AxisConnectedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "emissive"), new EmissiveModel.Loader());
	}

	public static void registerRenderLayers(){
		RenderTypeLookup.setRenderLayer(MedievalBlocks.HANGING_HORN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(MedievalBlocks.IRON_BRAZIER_COAL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());

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
