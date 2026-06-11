package fr.stylobow.iyc.screen;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, "iyc");

    public static final Supplier<MenuType<BarrelMenu>> BARREL_MENU =
            MENUS.register("barrel_menu", () -> IMenuTypeExtension.create(BarrelMenu::new));
}