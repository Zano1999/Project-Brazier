package net.dark_roleplay.medieval.objects.items.tools.timbering_notes;

import net.dark_roleplay.medieval.objects.guis.timbered_clay_placer.TimberringNotesScreen;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberingData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class TimberingNotesItem extends Item {

    public TimberingNotesItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        context.getPlayer().getCooldownTracker().setCooldown(this, 20);

        CompoundNBT compound = context.getItem().getOrCreateTag();

        TimberingData data = new TimberingData(compound.getCompound("TimberingData"));

        ITextComponent result = data.setPos(compound.getBoolean("EditPosB"), context.getPos());
        context.getPlayer().sendStatusMessage(result, true);

        compound.put("TimberingData", data.serializeNBT());
        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        player.getCooldownTracker().setCooldown(this, 20);
        if(player.isSneaking()){    //Used to clear TimberingData, replace with crafting in the future?
            CompoundNBT compound = player.getHeldItem(hand).getTag();
            if(compound != null){
                compound.remove("TimberingData");
                return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
            }
        }else if(world.isRemote()) {
            CompoundNBT compound = player.getHeldItem(hand).getOrCreateTag();
            if (compound.contains("TimberingData")) {
                TimberingData data = new TimberingData(compound.getCompound("TimberingData"));

                Minecraft.getInstance().displayGuiScreen(new TimberringNotesScreen(data));
            }
        }

        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }
}
