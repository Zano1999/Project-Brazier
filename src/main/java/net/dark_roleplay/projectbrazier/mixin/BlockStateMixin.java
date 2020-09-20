package net.dark_roleplay.projectbrazier.mixin;

import net.dark_roleplay.projectbrazier.mixin_helper.ICustomOffset;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class BlockStateMixin {

	@Inject(method="getOffset(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/vector/Vector3d;", at=@At("INVOKE"), cancellable = true)
	public void getOffset(IBlockReader access, BlockPos pos, CallbackInfoReturnable<Vector3d> info) {
		BlockState state = access.getBlockState(pos);
		if(state.getBlock() instanceof ICustomOffset)
			info.setReturnValue(((ICustomOffset) state.getBlock()).getOffset(state, access, pos));
	}
}
