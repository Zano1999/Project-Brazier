package net.dark_roleplay.projectbrazier.experimental_features.screen_lib;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class NestableContainerScreen<T extends Container> extends ContainerScreen<T> {

	protected final List<IRenderable> renderChildren = Lists.newArrayList();

	protected NestableContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init() {
		super.init();
		this.renderChildren.clear();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		for(IRenderable child : renderChildren) child.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	public <T extends IRenderable & IGuiEventListener> void addChild(T child){
		this.children.add(child);
		this.renderChildren.add(child);
	}
}
