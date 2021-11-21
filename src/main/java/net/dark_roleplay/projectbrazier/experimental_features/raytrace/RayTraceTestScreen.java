package net.dark_roleplay.projectbrazier.experimental_features.raytrace;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

public class RayTraceTestScreen extends Screen {

	protected BlockRayTraceResult traceResult;
	public static Vector3d hitPoint;

	public RayTraceTestScreen() {
		super(new StringTextComponent("Debuuuug"));
		Vector3d source = Minecraft.getInstance().player.position();
		hitPoint = new Vector3d(source.x, source.y, source.z);
	}

	public void init(){
		this.addWidget(new IGuiEventListener() {
			@Override
			public boolean mouseReleased(double mouseX, double mouseY, int button) {
				return clicked(button, traceResult);
			}
		});
	}

	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
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

		Vector3d cameraCenter = RenderUtils.getCameraPos();

		Pair<Vector3d, Vector3d> ray = RenderUtils.screenToWorldSpaceRay(partialTicks);

		RayTraceContext rtc = new RayTraceContext(
				cameraCenter.add(ray.getFirst()),
				cameraCenter.add(ray.getFirst().add(ray.getSecond().multiply(15, 15, 15))),
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, null);

		traceResult = Minecraft.getInstance().level.clip(rtc);

		hitPoint = traceResult.getLocation();
	}

	public boolean clicked(int mouseButton, BlockRayTraceResult hit){ return false; }
}
