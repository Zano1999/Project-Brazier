package net.dark_roleplay.projectbrazier.mixin;

import net.dark_roleplay.projectbrazier.mixin_helper.ICustomOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.AbstractBlockState.class)
public class BlockStateMixin {

	@Inject(method="getOffset(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/vector/Vector3d;", at=@At("INVOKE"), cancellable = true)
	public void getOffset(BlockGetter access, BlockPos pos, CallbackInfoReturnable<Vec3> info) {
		BlockState state = access.getBlockState(pos);
		if(state.getBlock() instanceof ICustomOffset)
			info.setReturnValue(((ICustomOffset) state.getBlock()).getOffset(state, access, pos));
	}
}
