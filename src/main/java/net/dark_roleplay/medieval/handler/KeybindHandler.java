package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.minecraft.client.settings.KeyBinding;
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
            RoadSignHelper.INSTANCE.setRight(!RoadSignHelper.INSTANCE.isRight());
        }
    }

}
