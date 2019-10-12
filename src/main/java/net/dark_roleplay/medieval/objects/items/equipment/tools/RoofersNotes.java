package net.dark_roleplay.medieval.objects.items.equipment.tools;

import net.dark_roleplay.medieval.objects.blocks.utility.roofers_table.Roof;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class RoofersNotes extends Item {
    public RoofersNotes(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getItem();
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();

        CompoundNBT tag = stack.hasTag() ? stack.getTag() : new CompoundNBT();
        ListNBT positionsList = tag.contains("positions") ? tag.getList("positions", Constants.NBT.TAG_INT_ARRAY) : new ListNBT();

        positionsList.add(new IntArrayNBT(new int[]{pos.getX(), pos.getY(), pos.getZ()}));

        tag.put("positions", positionsList);
        stack.setTag(tag);

        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.hasTag()) return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
        CompoundNBT tag = stack.getTag();

        if(!tag.contains("positions")) return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));

        ListNBT positionsList = tag.getList("positions", Constants.NBT.TAG_INT_ARRAY);

        BlockPos[] positions = new BlockPos[positionsList.size()];
        for(int i = 0; i < positionsList.size(); i++){
            positions[i] = posFromArray(positionsList.getIntArray(i));

        }

        Roof roof = new Roof(positions);
        roof.place(world,4);

        return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if(!flag.isAdvanced() || !stack.hasTag()) return;
        CompoundNBT tag = stack.getTag();
        if(tag.contains("positions")) {
            ListNBT positionsList = tag.getList("positions", Constants.NBT.TAG_INT_ARRAY);
            for(int i = 0; i < positionsList.size(); i++){
                int[] pos = positionsList.getIntArray(i);
                tooltip.add(new StringTextComponent("Pos " + i + ": " + pos[0] + ", " + pos[1] + ", " + pos[2]));

            }
        }
    }

    private BlockPos posFromArray(int[] data){
        return new BlockPos(data[0], data[1], data[2]);
    }
}
