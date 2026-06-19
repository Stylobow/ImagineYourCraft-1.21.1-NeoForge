package fr.stylobow.iyc.event;

import fr.stylobow.iyc.block.ModBlocks;
import fr.stylobow.iyc.block.entity.HiddenDoorBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorEvents {

    public static final int C_WHITE      = 0xCFD5D6 | 0xFF000000;
    public static final int C_ORANGE     = 0xE06100 | 0xFF000000;
    public static final int C_MAGENTA    = 0xA9309F | 0xFF000000;
    public static final int C_LIGHT_BLUE = 0x2389C7 | 0xFF000000;
    public static final int C_YELLOW     = 0xF1AF15 | 0xFF000000;
    public static final int C_LIME       = 0x5EA818 | 0xFF000000;
    public static final int C_PINK       = 0xD4657F | 0xFF000000;
    public static final int C_GRAY       = 0x3A3A3A | 0xFF000000;
    public static final int C_LIGHT_GRAY = 0x7D7D73 | 0xFF000000;
    public static final int C_CYAN       = 0x157788 | 0xFF000000;
    public static final int C_PURPLE     = 0x641F9C | 0xFF000000;
    public static final int C_BLUE       = 0x2D2F8F | 0xFF000000;
    public static final int C_BROWN      = 0x4D3224 | 0xFF000000;
    public static final int C_GREEN      = 0x495B24 | 0xFF000000;
    public static final int C_RED        = 0x8E2020 | 0xFF000000;
    public static final int C_BLACK      = 0x080A0F | 0xFF000000;

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_IRON_BLOCK.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_IRON_BLOCK.get());

        event.register((state, level, pos, tintIndex) -> C_WHITE, ModBlocks.WHITE_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_LIGHT.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_LIGHT.get());

        event.register((state, level, pos, tintIndex) -> C_WHITE, ModBlocks.WHITE_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_REDSTONE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_REDSTONE_LAMP.get());

        event.register((state, level, pos, tintIndex) -> C_WHITE, ModBlocks.WHITE_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_SAND.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_SAND.get());

        event.register((state, level, pos, tintIndex) -> C_WHITE, ModBlocks.WHITE_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_CLOUD.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_CLOUD.get());

        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_IRON_FENCE.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_IRON_FENCE.get());

        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_ROD.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_ROD.get());

        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_LAMP.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_LAMP.get());

        event.register((state, level, pos, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_LIME, ModBlocks.LIME_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_PINK, ModBlocks.PINK_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_GRAY, ModBlocks.GRAY_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_CYAN, ModBlocks.CYAN_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_BLUE, ModBlocks.BLUE_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_BROWN, ModBlocks.BROWN_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_GREEN, ModBlocks.GREEN_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_RED, ModBlocks.RED_BLOB.get());
        event.register((state, level, pos, tintIndex) -> C_BLACK, ModBlocks.BLACK_BLOB.get());

        event.register((state, level, pos, tintIndex) -> {
            if (level != null && pos != null) {
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof HiddenDoorBlockEntity doorBe) {
                    return doorBe.getMimicColor(level, pos, tintIndex);
                }
            }
            return -1;
        }, ModBlocks.HIDDEN_DOOR.get());
    }

    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_IRON_BLOCK.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_IRON_BLOCK.get());

        event.register((stack, tintIndex) -> C_WHITE, ModBlocks.WHITE_LIGHT.get());
        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_LIGHT.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_LIGHT.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_LIGHT.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_LIGHT.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_LIGHT.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_LIGHT.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_LIGHT.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_LIGHT.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_LIGHT.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_LIGHT.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_LIGHT.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_LIGHT.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_LIGHT.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_LIGHT.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_LIGHT.get());

        event.register((stack, tintIndex) -> C_WHITE, ModBlocks.WHITE_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_REDSTONE_LAMP.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_REDSTONE_LAMP.get());

        event.register((stack, tintIndex) -> C_WHITE, ModBlocks.WHITE_SAND.get());
        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_SAND.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_SAND.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_SAND.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_SAND.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_SAND.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_SAND.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_SAND.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_SAND.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_SAND.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_SAND.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_SAND.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_SAND.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_SAND.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_SAND.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_SAND.get());

        event.register((stack, tintIndex) -> C_WHITE, ModBlocks.WHITE_CLOUD.get());
        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_CLOUD.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_CLOUD.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_CLOUD.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_CLOUD.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_CLOUD.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_CLOUD.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_CLOUD.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_CLOUD.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_CLOUD.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_CLOUD.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_CLOUD.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_CLOUD.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_CLOUD.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_CLOUD.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_CLOUD.get());

        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_IRON_FENCE.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_IRON_FENCE.get());

        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_ROD.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_ROD.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_ROD.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_ROD.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_ROD.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_ROD.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_ROD.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_ROD.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_ROD.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_ROD.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_ROD.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_ROD.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_ROD.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_ROD.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_ROD.get());

        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_LAMP.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_LAMP.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_LAMP.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_LAMP.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_LAMP.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_LAMP.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_LAMP.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_LAMP.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_LAMP.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_LAMP.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_LAMP.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_LAMP.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_LAMP.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_LAMP.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_LAMP.get());

        event.register((stack, tintIndex) -> C_ORANGE, ModBlocks.ORANGE_BLOB.get());
        event.register((stack, tintIndex) -> C_MAGENTA, ModBlocks.MAGENTA_BLOB.get());
        event.register((stack, tintIndex) -> C_LIGHT_BLUE, ModBlocks.LIGHT_BLUE_BLOB.get());
        event.register((stack, tintIndex) -> C_YELLOW, ModBlocks.YELLOW_BLOB.get());
        event.register((stack, tintIndex) -> C_LIME, ModBlocks.LIME_BLOB.get());
        event.register((stack, tintIndex) -> C_PINK, ModBlocks.PINK_BLOB.get());
        event.register((stack, tintIndex) -> C_GRAY, ModBlocks.GRAY_BLOB.get());
        event.register((stack, tintIndex) -> C_LIGHT_GRAY, ModBlocks.LIGHT_GRAY_BLOB.get());
        event.register((stack, tintIndex) -> C_CYAN, ModBlocks.CYAN_BLOB.get());
        event.register((stack, tintIndex) -> C_PURPLE, ModBlocks.PURPLE_BLOB.get());
        event.register((stack, tintIndex) -> C_BLUE, ModBlocks.BLUE_BLOB.get());
        event.register((stack, tintIndex) -> C_BROWN, ModBlocks.BROWN_BLOB.get());
        event.register((stack, tintIndex) -> C_GREEN, ModBlocks.GREEN_BLOB.get());
        event.register((stack, tintIndex) -> C_RED, ModBlocks.RED_BLOB.get());
        event.register((stack, tintIndex) -> C_BLACK, ModBlocks.BLACK_BLOB.get());
    }

    @SafeVarargs
    private static void registerColorGroup(RegisterColorHandlersEvent.Block event, int color, Supplier<? extends Block>... blocks) {
        Block[] blockArray = new Block[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            blockArray[i] = blocks[i].get();
        }
        event.register((state, level, pos, tintIndex) -> color, blockArray);
    }

    @SafeVarargs
    private static void registerColorGroup(RegisterColorHandlersEvent.Item event, int color, Supplier<? extends Block>... blocks) {
        Block[] blockArray = new Block[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            blockArray[i] = blocks[i].get();
        }
        event.register((stack, tintIndex) -> color, blockArray);
    }
}