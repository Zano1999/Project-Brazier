package net.dark_roleplay.projectbrazier.feature.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dark_roleplay.projectbrazier.util.sitting.SittingUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Collection;

public class SitdownCommand {

	public static final LiteralArgumentBuilder<CommandSource> SITDOWN =
			Commands.literal("sitdown")
					.then(Commands.argument("target", EntityArgument.entities())
							.requires(source -> source.hasPermissionLevel(2))
							.then(Commands.argument("position", Vec3Argument.vec3())
									.executes((context) -> sitDown(context, true, true, false))
							).executes((context) -> sitDown(context, true, false, false))
					).executes(context -> sitDown(context, false, false, false));

	private static int sitDown(CommandContext<CommandSource> context, boolean hasEntity, boolean hasPos, boolean hasDirection) throws CommandSyntaxException {
		Collection<? extends Entity> targets = hasEntity ? EntityArgument.getEntities(context, "target") : null;
		Vector3d targetPos = hasPos ? Vec3Argument.getVec3(context, "position") : null;

		if (targets != null)
			for (Entity entity : targets)
				if(!entity.isPassenger())
					SittingUtil.sitDownEntity(entity.getEntityWorld(), targetPos == null ? entity.getPositionVec() : targetPos, entity, null, null, 0, null);

		if(!hasEntity){
			PlayerEntity player = context.getSource().asPlayer();
			if(!player.isPassenger())
				SittingUtil.sitDownEntity(player.getEntityWorld(), targetPos == null ? player.getPositionVec() : targetPos, player, null, null, 0, null);
		}

		return 1;
	}
}
