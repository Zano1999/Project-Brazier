package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.crafter.objects.guis.CraftingScreen;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.dark_roleplay.medieval.objects.guis.fourteen.TimberedArea;
import net.dark_roleplay.medieval.objects.guis.fourteen.TimberedScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class KeybindHandler {

    public static KeyBinding BLOCK_INTERACTOR = new KeyBinding("keybind.drpmedieval.interactor", GLFW.GLFW_KEY_GRAVE_ACCENT, "keybind.category.drpmedieval");

    @SubscribeEvent
    public static void KeyInput(InputEvent.KeyInputEvent event) {
        if (BLOCK_INTERACTOR.isPressed()) {
            //Minecraft.getInstance().displayGuiScreen(new CraftingScreen());
            //Minecraft.getInstance().displayGuiScreen(new TimberedScreen(new TimberedArea(Minecraft.getInstance().world, new BlockPos(-1031, 70, 821), new BlockPos(-1031, 72, 815))));
            RoadSignHelper.INSTANCE.setRight(!RoadSignHelper.INSTANCE.isRight());
        }
    }

}
