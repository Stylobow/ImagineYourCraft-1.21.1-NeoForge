package fr.stylobow.iyc.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

import java.util.List;
import java.util.Optional;

public class TippedBoltItem extends ArrowItem {
    public TippedBoltItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.POISON));
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents != null) {
            contents.addPotionTooltip(tooltipComponents::add, 0.125F, context.tickRate());
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents != null) {
            Optional<Holder<Potion>> potionOpt = contents.potion();
            if (potionOpt.isPresent()) {
                String potionKey = Potion.getName(potionOpt, "item.iyc.tipped_bolt.effect.");
                return Component.translatable(potionKey);
            }
        }
        return super.getName(stack);
    }
}
