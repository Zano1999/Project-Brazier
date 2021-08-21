package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.experimental_features.immersive_screen.ImmersiveScreen;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

public class DecorPlacementScreen extends ImmersiveScreen {
	protected DecorPlacementScreen(ITextComponent title, Vector3d cameraPos, Vector3f cameraRotation) {
		super(title, cameraPos, cameraRotation);
	}

	@Override
	public void renderInWorld(WorldRenderer context, MatrixStack matrixStack, float partialTicks) {

	}
}
