package net.dark_roleplay.projectbrazier.experimental_features.walking_gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dark_roleplay.projectbrazier.experimental_features.raytrace.RayTraceTestScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PassiveScreen  extends RayTraceTestScreen {

	public static Vec3 hitPoint;

	public PassiveScreen(){
		this.passEvents = true;
	}

	public void init() {
		super.init();
		KeyMapping.setAll();
	}

	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
//		if(!BrazierKeybinds.EXP_PASSIVE_SCREEN.isKeyDown())
//			PassiveScreen.this.closeScreen();
	}


	public boolean isPauseScreen() {
		return false;
	}


	public boolean clicked(int mouseButton, BlockHitResult hit){
		if(hit.getType() == HitResult.Type.BLOCK){
			return true;
		}
		return false;
	}
}