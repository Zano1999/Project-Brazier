package net.dark_roleplay.projectbrazier.experimental_features.walking_gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.experimental_features.raytrace.RayTraceTestScreen;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class PassiveScreen  extends RayTraceTestScreen {

	public static Vector3d hitPoint;

	public PassiveScreen(){
		this.passEvents = true;
	}

	public void init() {
		super.init();
		KeyBinding.updateKeyBindState();
	}

	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
//		if(!BrazierKeybinds.EXP_PASSIVE_SCREEN.isKeyDown())
//			PassiveScreen.this.closeScreen();
	}


	public boolean isPauseScreen() {
		return false;
	}


	public boolean clicked(int mouseButton, BlockRayTraceResult hit){
		if(hit.getType() == RayTraceResult.Type.BLOCK){
			return true;
		}
		return false;
	}
}
