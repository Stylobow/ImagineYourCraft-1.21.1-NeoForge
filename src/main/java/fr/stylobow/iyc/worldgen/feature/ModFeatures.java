package fr.stylobow.iyc.worldgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, "iyc");

    public static final DeferredHolder<Feature<?>, LigniteFeature> LIGNITE_BLOB =
            FEATURES.register("lignite_blob", () -> new LigniteFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, SteelFeature> STEEL_BLOB =
            FEATURES.register("steel_blob", () -> new SteelFeature(NoneFeatureConfiguration.CODEC));
}