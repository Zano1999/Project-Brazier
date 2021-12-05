package net.dark_roleplay.projectbrazier.feature.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dark_roleplay.projectbrazier.util.sitting.SittingUtil;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public class SitdownCommand {

	public static final LiteralArgumentBuilder<CommandSourceStack> SITDOWN =
			Commands.literal("sitdown")
					.then(Commands.argument("target", EntityArgument.entities())
							.requires(source -> source.hasPermission(2))
							.then(Commands.argument("position", Vec3Argument.vec3())
									.executes((context) -> sitDown(context, true, true, false))
							).executes((context) -> sitDown(context, true, false, false))
					).executes(context -> sitDown(context, false, false, false));

	private static int sitDown(CommandContext<CommandSourceStack> context, boolean hasEntity, boolean hasPos, boolean hasDirection) throws CommandSyntaxException {
		Collection<? extends Entity> targets = hasEntity ? EntityArgument.getEntities(context, "target") : null;
		Vec3 targetPos = hasPos ? Vec3Argument.getVec3(context, "position") : null;

		if (targets != null)
			for (Entity entity : targets)
				if(!entity.isPassenger())
					SittingUtil.sitDownEntity(entity.getCommandSenderWorld(), targetPos == null ? entity.position() : targetPos, entity, null, null, 0, null);

		if(!hasEntity){
			Player player = context.getSource().getPlayerOrException();
			if(!player.isPassenger())
				SittingUtil.sitDownEntity(player.getCommandSenderWorld(), targetPos == null ? player.position() : targetPos, player, null, null, 0, null);
		}

		return 1;
	}
}
