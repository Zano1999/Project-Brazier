package net.dark_roleplay.projectbrazier.experimental_features.screen_lib;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

import java.util.List;

public class NestedWidget{// extends AbstractWidget implements Widget, NarratableEntry {
	protected List<GuiEventListener> listeners = Lists.newArrayList();
	protected List<Widget> renderers = Lists.newArrayList();

	protected int posX, posY;
	protected int width, height;

	public NestedWidget(int x, int y, int width, int height){
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
	}

	public List<? extends GuiEventListener> children() {
		return listeners;
	}

	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		for(Widget child : renderers){
			child.render(matrixStack, mouseX, mouseY, partialTicks);
		}
	}

	public <T extends Widget & GuiEventListener> void addChild(T child){
		this.listeners.add(child);
		this.renderers.add(child);
	}

	public <T extends Widget & GuiEventListener> void removeChild(T child){
		this.listeners.remove(child);
		this.renderers.remove(child);
	}

	public boolean isMouseOver(double mouseX, double mouseY) {
		return mouseX > this.posX && mouseY > this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height;
	}
}
