package net.dark_roleplay.projectbrazier.experimental_features.raytrace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.text.StringTextComponent;

public class RayTraceTestScreen extends Screen {

	protected BlockHitResult traceResult;
	public static Vec3 hitPoint;

	public RayTraceTestScreen() {
		super(new StringTextComponent("Debuuuug"));
		Vec3 source = Minecraft.getInstance().player.position();
		hitPoint = new Vec3(source.x, source.y, source.z);
	}

	public void init(){
		this.addWidget(new IGuiEventListener() {
			@Override
			public boolean mouseReleased(double mouseX, double mouseY, int button) {
				return clicked(button, traceResult);
			}
		});
	}

	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//		Vector2f testPos = RenderUtils.worldToScreenSpace(new Vector3d(187, 6, -299), partialTicks);
////		Vector2f testPos = RayTraceWorldRender.screenPoint;
//
//		float revertGuiScale = (float) (1/Minecraft.getInstance().getMainWindow().getGuiScaleFactor());
//
//		matrixStack.scale(revertGuiScale, revertGuiScale, revertGuiScale);
//
//		int screenWidth = Minecraft.getInstance().getMainWindow().getWidth() / 2;
//		int screenHeight = Minecraft.getInstance().getMainWindow().getHeight() / 2;
//
//		int posX = (int) testPos.x + screenWidth;
//		int posY = (int) -testPos.y + screenHeight;
//		fill(matrixStack, posX, posY, posX + 10, posY + 10, 0xFFFFFFFF);
//		fill(matrixStack, 0, 0, 1, 1, 0xFFFFFFFF);

		Vec3 cameraCenter = RenderUtils.getCameraPos();

		Pair<Vec3, Vec3> ray = RenderUtils.screenToWorldSpaceRay(partialTicks);

		RayTraceContext rtc = new RayTraceContext(
				cameraCenter.add(ray.getFirst()),
				cameraCenter.add(ray.getFirst().add(ray.getSecond().multiply(15, 15, 15))),
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, null);

		traceResult = Minecraft.getInstance().level.clip(rtc);

		hitPoint = traceResult.getLocation();
	}

	public boolean clicked(int mouseButton, BlockHitResult hit){ return false; }
}
