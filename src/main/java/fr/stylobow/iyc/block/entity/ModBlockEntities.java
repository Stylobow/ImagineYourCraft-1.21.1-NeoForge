package fr.stylobow.iyc.block.entity;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.block.ModBlocks;
import fr.stylobow.iyc.block.entity.custom.BarrelBlockEntity;
import fr.stylobow.iyc.client.renderer.IronFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ImagineYourCraft.MOD_ID);

    public static final Supplier<BlockEntityType<BarrelBlockEntity>> BARREL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("barrel", () -> BlockEntityType.Builder.of(
                    BarrelBlockEntity::new,
                    ModBlocks.BARREL.get()
            ).build(null));

    public static final Supplier<BlockEntityType<IronFurnaceBlockEntity>> IRON_FURNACE_BE = BLOCK_ENTITIES.register("iron_furnace",
            () -> BlockEntityType.Builder.of(IronFurnaceBlockEntity::new, ModBlocks.IRON_FURNACE.get()).build(null));

    public static final Supplier<BlockEntityType<SteelBrewingStandBlockEntity>> STEEL_BREWING_STAND_BE = BLOCK_ENTITIES.register("steel_brewing_stand",
            () -> BlockEntityType.Builder.of(SteelBrewingStandBlockEntity::new, ModBlocks.STEEL_BREWING_STAND.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HiddenDoorBlockEntity>> HIDDEN_DOOR = BLOCK_ENTITIES.register("hidden_door",
            () -> BlockEntityType.Builder.of(HiddenDoorBlockEntity::new, ModBlocks.HIDDEN_DOOR.get()).build(null)
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}