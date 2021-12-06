package net.dark_roleplay.projectbrazier.experimental_features.screen_lib;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.List;

public class NestableContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

	protected final List<Widget> renderChildren = Lists.newArrayList();

	protected NestableContainerScreen(T screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init() {
		super.init();
		this.renderChildren.clear();
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		for(Widget child : renderChildren) child.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	public <T extends GuiEventListener & Widget> void addChild(T child){
		this.children().add(child);
		this.renderChildren.add(child);
	}
}
