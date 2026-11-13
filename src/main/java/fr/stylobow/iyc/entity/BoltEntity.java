package fr.stylobow.iyc.entity;

import fr.stylobow.iyc.block.entity.ModEntities;
import fr.stylobow.iyc.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BoltEntity extends AbstractArrow {
    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(BoltEntity.class, EntityDataSerializers.INT);

    public BoltEntity(EntityType<? extends BoltEntity> type, Level level) {
        super(type, level);
    }

    public BoltEntity(Level level, LivingEntity shooter, ItemStack pickupStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.BOLT.get(), shooter, level, pickupStack, firedFromWeapon);
        this.updateColor();
    }

    public BoltEntity(Level level, LivingEntity shooter, ItemStack pickupStack) {
        this(level, shooter, pickupStack, null);
    }

    private PotionContents getPotionContents() {
        return this.getPickupItemStackOrigin().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    private void setPotionContents(PotionContents potionContents) {
        this.getPickupItemStackOrigin().set(DataComponents.POTION_CONTENTS, potionContents);
        this.updateColor();
    }

    @Override
    protected void setPickupItemStack(ItemStack pickupItemStack) {
        super.setPickupItemStack(pickupItemStack);
        this.updateColor();
    }

    private void updateColor() {
        PotionContents potionContents = this.getPotionContents();
        this.entityData.set(ID_EFFECT_COLOR, potionContents.equals(PotionContents.EMPTY) ? -1 : potionContents.getColor());
    }

    public int getColor() {
        return this.entityData.get(ID_EFFECT_COLOR);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_EFFECT_COLOR, -1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
        }
    }

    private void makeParticle(int particleAmount) {
        int color = this.getColor();
        if (color != -1 && particleAmount > 0) {
            for (int j = 0; j < particleAmount; j++) {
                this.level().addParticle(
                        ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, color),
                        this.getRandomX(0.5D),
                        this.getRandomY(),
                        this.getRandomZ(0.5D),
                        0.0D,
                        0.0D,
                        0.0D
                );
            }
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        Entity effectSource = this.getEffectSource();
        PotionContents potionContents = this.getPotionContents();

        if (potionContents.potion().isPresent()) {
            for (MobEffectInstance instance : potionContents.potion().get().value().getEffects()) {
                living.addEffect(
                        new MobEffectInstance(
                                instance.getEffect(),
                                Math.max(instance.mapDuration(d -> d / 8), 1),
                                instance.getAmplifier(),
                                instance.isAmbient(),
                                instance.isVisible()
                        ),
                        effectSource
                );
            }
        }

        for (MobEffectInstance customEffect : potionContents.customEffects()) {
            living.addEffect(customEffect, effectSource);
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.BOLT.get());
    }
}
