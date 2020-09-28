package net.dark_roleplay.projectbrazier.mixin;

import com.mojang.brigadier.suggestion.Suggestion;
import net.dark_roleplay.projectbrazier.clientcommands.ClientCommands;
import net.minecraft.client.gui.CommandSuggestionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(CommandSuggestionHelper.class)
public class ClientCommandMixin {

	@Inject(method="Lnet/minecraft/client/gui/CommandSuggestionHelper;getSuggestions(Lcom/mojang/brigadier/suggestion/Suggestions;)Ljava/util/List;", locals = LocalCapture.CAPTURE_FAILSOFT, at=@At(value="INVOKE_ASSIGN", ordinal = 2))
	public void getSuggestions(com.mojang.brigadier.suggestion.Suggestions suggestions, CallbackInfoReturnable<List<Suggestion>> info, String s) {
		suggestions = ClientCommands.getSuggestions(s, suggestions);
	}
}
