package net.dark_roleplay.projectbrazier.experimental_features.immersive_screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierEntities;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.phys.Vec3;

public abstract class ImmersiveScreen extends Screen {

	protected Vec3 cameraPos;
	protected Vector3f cameraRotation;
	protected Pair<Vec3, Vec3> raytrace;

	protected ImmersiveScreen(TextComponent title, Vec3 cameraPos, Vector3f cameraRotation) {
		super(title);

		this.cameraPos = cameraPos;
		this.cameraRotation = cameraRotation;
		CameraEntity cameraEntity = BrazierEntities.CAMERA.get().create(Minecraft.getInstance().level);
		cameraEntity.setup(cameraPos, cameraRotation);
		Minecraft.getInstance().setCameraEntity(cameraEntity);
	}

	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.raytrace = RenderUtils.screenToWorldSpaceRay(partialTicks);
	}

	public abstract void renderInWorld(LevelRenderer context, PoseStack matrixStack, float partialTicks);

	public Vec3 getCameraPos() {
		return cameraPos;
	}

	public Vector3f getCameraRotation() {
		return cameraRotation;
	}

	public Pair<Vec3, Vec3> getRaytrace() {
		return raytrace;
	}

	@Override
	public void removed() {
		Minecraft.getInstance().setCameraEntity(null);
	}
}
