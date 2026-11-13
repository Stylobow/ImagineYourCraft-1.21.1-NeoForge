package fr.stylobow.iyc.item.custom;

import fr.stylobow.iyc.entity.BoltEntity;
import fr.stylobow.iyc.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class IYCCrossbowItem extends CrossbowItem {
    public static final Predicate<ItemStack> BOLTS_ONLY = stack -> stack.is(ModItems.BOLT.get()) || stack.is(ModItems.TIPPED_BOLT.get());

    public IYCCrossbowItem(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return BOLTS_ONLY;
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return BOLTS_ONLY;
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        BoltEntity bolt = new BoltEntity(level, shooter, ammo.copyWithCount(1), weapon);

        if (isCrit) {
            bolt.setCritArrow(true);
        }

        return bolt;
    }
}
