package net.dark_roleplay.medieval.objects.items.tools.timbering_notes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class TimberingNotesItem extends Item {

    public TimberingNotesItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        CompoundNBT compound = context.getItem().getOrCreateTag();

        TimberingData data = new TimberingData();
        data.deserializeNBT(compound.getCompound("TimberingData"));

        boolean editB = compound.getBoolean("EditPosB");

        BlockPos existingPos = editB ? data.getPosB() : data.getPosA();
        BlockPos newPos = context.getPos();

        if(existingPos == null || (existingPos.getX() == newPos.getX() || existingPos.getZ() == newPos.getZ())) {
            if (editB)
                data.setPosA(context.getPos());
            else
                data.setPosB(context.getPos());

            context.getPlayer().sendStatusMessage(new TranslationTextComponent("message.drpmedieval.timbering_notes.set_" + (editB ? "a" : "b")), true);
        }else
            context.getPlayer().sendStatusMessage(new TranslationTextComponent("message.drpmedieval.timbering_notes.not2d").setStyle(new Style().setColor(TextFormatting.RED)), true);

        compound.put("TimberingData", data.serializeNBT());

        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if(player.isSneaking()){    //Used to clear TimberingData, replace with crafting in the future?
            CompoundNBT compound = player.getHeldItem(hand).getTag();
            if(compound != null){
                compound.remove("TimberingData");
                return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
            }
        }else{ //Open editing GUI?

        }
        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }
}
