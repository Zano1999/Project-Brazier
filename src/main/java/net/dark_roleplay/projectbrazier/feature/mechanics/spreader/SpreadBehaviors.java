package net.dark_roleplay.projectbrazier.feature.mechanics.spreader;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SpreadBehaviors {

	private static final Logger LOGGER = LogManager.getLogger();
	private static final Map<Block, Map<SpreaderType, ISpreadingBehavior>> SPREADERS = new HashMap<>();

	public static void addSpreaderBehavior(Block block, SpreaderType type, ISpreadingBehavior behavior){
		Map<SpreaderType, ISpreadingBehavior> blockSpecificSpreaders = SPREADERS.computeIfAbsent(block, key -> new HashMap<>());
		if(blockSpecificSpreaders.containsKey(type))
			LOGGER.info("Replacing spreading behavior for block '{}' and spreader type '{}'", block.getRegistryName(), type.getName());
		blockSpecificSpreaders.put(type, behavior);
	}

	public static boolean canSpread(BlockState state, SpreaderType type){
		return getSpreadingBehavior(state.getBlock(), type) != null;
	}

	public static BlockState getSpreadState(BlockState state, Level level, BlockPos pos, SpreaderType type){
		ISpreadingBehavior behavior = getSpreadingBehavior(state.getBlock(), type);
		if(behavior == null) return state;
		return behavior.getSpreadingState(state, level, pos);
	}

	@Nullable
	private static ISpreadingBehavior getSpreadingBehavior(Block block, SpreaderType type){
		return SPREADERS.containsKey(block) ? SPREADERS.get(block).get(type) : null;
	}
}
