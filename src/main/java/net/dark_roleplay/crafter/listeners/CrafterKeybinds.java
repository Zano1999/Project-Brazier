package net.dark_roleplay.crafter.listeners;

import net.dark_roleplay.crafter.objects.guis.v2.CraftingScreen;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class CrafterKeybinds {

      public static KeyBinding OPEN_CRAFTING = new KeyBinding("keybind.craftmans_choice.open", GLFW.GLFW_KEY_C, "keybind.category.craftmans_choice");

    @SubscribeEvent
    public static void KeyInput(InputEvent.KeyInputEvent event) {
        if(OPEN_CRAFTING.isPressed()){
            Minecraft.getInstance().displayGuiScreen(new CraftingScreen());
        }
    }
}
