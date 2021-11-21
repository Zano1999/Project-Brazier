package net.dark_roleplay.projectbrazier.experimental_features.immersive_screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierEntities;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

public abstract class ImmersiveScreen extends Screen {

	protected Vector3d cameraPos;
	protected Vector3f cameraRotation;
	protected Pair<Vector3d, Vector3d> raytrace;

	protected ImmersiveScreen(ITextComponent title, Vector3d cameraPos, Vector3f cameraRotation) {
		super(title);

		this.cameraPos = cameraPos;
		this.cameraRotation = cameraRotation;
		CameraEntity cameraEntity = BrazierEntities.CAMERA.get().create(Minecraft.getInstance().level);
		cameraEntity.setup(cameraPos, cameraRotation);
		Minecraft.getInstance().setCameraEntity(cameraEntity);
	}

	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.raytrace = RenderUtils.screenToWorldSpaceRay(partialTicks);
	}

	public abstract void renderInWorld(WorldRenderer context, MatrixStack matrixStack, float partialTicks);

	public Vector3d getCameraPos() {
		return cameraPos;
	}

	public Vector3f getCameraRotation() {
		return cameraRotation;
	}

	public Pair<Vector3d, Vector3d> getRaytrace() {
		return raytrace;
	}

	@Override
	public void removed() {
		Minecraft.getInstance().setCameraEntity(null);
	}
}
