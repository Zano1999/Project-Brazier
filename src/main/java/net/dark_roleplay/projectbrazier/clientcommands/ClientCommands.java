package net.dark_roleplay.projectbrazier.clientcommands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.network.play.server.STabCompletePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT)
public class ClientCommands {

	private static CommandDispatcher<CommandSource> commands = new CommandDispatcher();

	static{
		commands.register(Commands.literal("foo").then(Commands.literal("bar").executes(ctx -> {
			ctx.getSource().sendFeedback(new StringTextComponent("Test"), false);
			return 1;
		})));
	}

	@SubscribeEvent
	public static void playerChat(ClientChatEvent event){
		if(event.getMessage().startsWith("/")){
			try{
				ParseResults<CommandSource> parse = commands.parse(event.getMessage().substring(1), Minecraft.getInstance().player.getCommandSource());
				if(parse.getContext().getNodes().size() > 0){
					event.setCanceled(true);
					commands.execute(parse);
				}
			} catch (CommandSyntaxException e) {}
		}
	}

	public static Suggestions getSuggestions(String input, Suggestions suggestions){
		try{
			ParseResults<CommandSource> parseresults = commands.parse(input.replaceFirst("/", ""), Minecraft.getInstance().player.getCommandSource());
			return commands.getCompletionSuggestions(parseresults).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}