package fr.stylobow.iyc.worldgen.tree;

import fr.stylobow.iyc.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;
import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower CHERRY = new TreeGrower(
            "cherry",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.CHERRY_KEY),
            Optional.empty()
    );
}