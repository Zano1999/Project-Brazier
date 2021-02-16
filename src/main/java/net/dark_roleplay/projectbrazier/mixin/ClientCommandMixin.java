package net.dark_roleplay.projectbrazier.mixin;

import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import net.dark_roleplay.projectbrazier.experiments.clientcommands.ClientCommands;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Locale;

@Mixin(CommandSuggestionHelper.class)
public abstract class ClientCommandMixin {

	@Shadow
	TextFieldWidget inputField;
	@Shadow
	private static int getLastWhitespace(String text){return 1;}

	//Lnet/minecraft/client/multiplayer/ClientSuggestionProvider;handleResponse(ILcom/mojang/brigadier/suggestion/Suggestions;)V

	@Inject(
			method="Lnet/minecraft/client/gui/CommandSuggestionHelper;getSuggestions(Lcom/mojang/brigadier/suggestion/Suggestions;)Ljava/util/List;",
			locals = LocalCapture.CAPTURE_FAILSOFT,
			at=@At(value="RETURN"),
			cancellable = true
	)
	public void getSuggestions(Suggestions suggestions, CallbackInfoReturnable<List<Suggestion>> info) {
		String s = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
		int i = getLastWhitespace(s);
		String s1 = s.substring(i).toLowerCase(Locale.ROOT);

		suggestions = ClientCommands.getSuggestions(s1, suggestions);

		List<Suggestion> list = info.getReturnValue();

		for(Suggestion suggestion : suggestions.getList())
			list.add(suggestion);

		info.setReturnValue(list);
	}
}
