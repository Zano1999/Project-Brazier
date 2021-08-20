package net.dark_roleplay.projectbrazier.experimental_features.zipline;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ProjectBrazier.MODID)
public class ZiplineEventHandler {

	@SubscribeEvent
	public static void renderPlayer(RenderPlayerEvent.Pre event){
		if(event.getPlayer().getRidingEntity() instanceof ZiplineEntity){
			PlayerModel model = event.getRenderer().getEntityModel();
			model.bipedLeftArm.rotateAngleX = 0;
			model.bipedLeftArm.rotateAngleY = 0;
			model.bipedLeftArm.rotateAngleZ = 0;
		}
	}
}
