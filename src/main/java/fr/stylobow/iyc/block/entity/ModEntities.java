package fr.stylobow.iyc.block.entity;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.block.entity.custom.ChairEntity;
import fr.stylobow.iyc.entity.BoltEntity;
import fr.stylobow.iyc.entity.PrimedBombe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ImagineYourCraft.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "iyc");

    public static final Supplier<EntityType<ChairEntity>> CHAIR_ENTITY =
            ENTITY_TYPES.register("chair_entity", () -> EntityType.Builder.<ChairEntity>of(ChairEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.55f)
                    .build("chair_entity"));

    public static final DeferredHolder<EntityType<?>, EntityType<BoltEntity>> BOLT = ENTITIES.register("bolt",
            () -> EntityType.Builder.<BoltEntity>of(BoltEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("bolt"));

    public static final DeferredHolder<EntityType<?>, EntityType<PrimedBombe>> BOMBE = ENTITIES.register("bombe",
            () -> EntityType.Builder.<PrimedBombe>of(PrimedBombe::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.15F)
                    .clientTrackingRange(10)
                    .updateInterval(10)
                    .build("bombe")
    );

}