package net.dark_roleplay.medieval.objects.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DRPCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		/**
		 * Command Syntax:
		 * {@code /drp}
		 */
		dispatcher.register(Commands.literal("drp")
			.then(Commands.literal("debug")
				.then(Commands.literal("testing_area")
					.then(Commands.literal("generate")
						.requires(source -> requiresPermission(source, "dark-roleplay.medieval.debug.testing_area.generate"))
						.executes(context -> generateDebugArea(context.getSource(), false))
					).then(Commands.literal("clear")
						.requires(source -> requiresPermission(source, "dark-roleplay.medieval.debug.testing_area.clear"))
						.executes(context -> generateDebugArea(context.getSource(), true))
					)
				).then(Commands.literal("gui")
					.requires(source -> requiresPermission(source, "dark-roleplay.medieval.debug.gui"))
					.executes(context -> {
						return 1;
					})
				)
			)
		);
	}
	
	private static int generateDebugArea(CommandSource source, boolean clear) {
		World world = source.getWorld();
		Vec3d pos = source.getPos();
		
		for(int x = -5; x <= 5; x++) { for(int z = -5; z <= 5; z++) {
			world.setBlockState(new BlockPos(pos.x + x, pos.y - 1, pos.z + z), clear ? Blocks.AIR.getDefaultState() : Blocks.STRIPPED_BIRCH_LOG.getDefaultState());
		}}
		
		return 1;
	}
	
	private static boolean requiresPermission(CommandSource source, String permissionNode) {
//		System.out.println(source);
//		try {
			return false;
//			EntityPlayer player = source.asPlayer();
//			return PermissionAPI.hasPermission(player, permissionNode);
//		} catch (CommandSyntaxException e) {
//			return false;
//		}
	}
}
