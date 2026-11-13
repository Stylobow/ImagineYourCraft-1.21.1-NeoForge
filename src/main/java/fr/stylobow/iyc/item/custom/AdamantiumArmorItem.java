package fr.stylobow.iyc.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AdamantiumArmorItem extends ArmorItem {
    public AdamantiumArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player) {
            if (stack.getItem() == player.getItemBySlot(EquipmentSlot.HEAD).getItem()) {
                if (hasFullSet(player)) {
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1, 1, false, false, false));
                }
            }
        }
    }

    private boolean hasFullSet(Player player) {
        for (ItemStack armorStack : player.getArmorSlots()) {
            if (armorStack.isEmpty() || !(armorStack.getItem() instanceof ArmorItem armorItem) ||
                    !armorItem.getMaterial().getRegisteredName().equals("iyc:adamantium")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(
                Component.translatable("iyc.armor.magic_set").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC).
                        append(Component.translatable("effect.minecraft.haste").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC))
        );
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}