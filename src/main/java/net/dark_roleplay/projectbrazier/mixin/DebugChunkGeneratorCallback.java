package net.dark_roleplay.projectbrazier.mixin;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Collectors;

@Mixin(DebugChunkGenerator.class)
public class DebugChunkGeneratorCallback {

	@Inject(method = "initValidStates", at = @At("RETURN"), remap=false)
	private static void onInitValidStates(CallbackInfo ci) {
		DebugChunkGenerator.ALL_VALID_STATES.clear();
		DebugChunkGenerator.ALL_VALID_STATES.addAll(ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals(ProjectBrazier.MODID)).flatMap((p_236067_0_) -> {
			return p_236067_0_.getStateContainer().getValidStates().stream();
		}).collect(Collectors.toList()));
	}
}
