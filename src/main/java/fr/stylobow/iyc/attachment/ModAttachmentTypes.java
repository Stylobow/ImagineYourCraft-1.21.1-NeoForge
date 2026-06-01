package fr.stylobow.iyc.attachment;

import fr.stylobow.iyc.ImagineYourCraft;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ImagineYourCraft.MOD_ID);

    public static final java.util.function.Supplier<net.neoforged.neoforge.attachment.AttachmentType<byte[]>> SKIN_DATA = ATTACHMENT_TYPES.register(
            "skin_data",
            () -> net.neoforged.neoforge.attachment.AttachmentType.builder(() -> new byte[0])
                    .serialize(com.mojang.serialization.Codec.BYTE_BUFFER.xmap(java.nio.ByteBuffer::array, java.nio.ByteBuffer::wrap))
                    .build()
    );

    public static final java.util.function.Supplier<net.neoforged.neoforge.attachment.AttachmentType<byte[]>> CAPE_DATA = ATTACHMENT_TYPES.register(
            "cape_data",
            () -> net.neoforged.neoforge.attachment.AttachmentType.builder(() -> new byte[0])
                    .serialize(com.mojang.serialization.Codec.BYTE_BUFFER.xmap(java.nio.ByteBuffer::array, java.nio.ByteBuffer::wrap))
                    .build()
    );

    public static final java.util.function.Supplier<net.neoforged.neoforge.attachment.AttachmentType<byte[]>> HAT_DATA = ATTACHMENT_TYPES.register(
            "hat_data",
            () -> net.neoforged.neoforge.attachment.AttachmentType.builder(() -> new byte[0])
                    .serialize(com.mojang.serialization.Codec.BYTE_BUFFER.xmap(java.nio.ByteBuffer::array, java.nio.ByteBuffer::wrap))
                    .build()
    );

    public static final java.util.function.Supplier<AttachmentType<Boolean>> TARDIS_VISIBLE =
            ATTACHMENT_TYPES.register("tardis_visible", () -> AttachmentType.builder(() -> true).serialize(Codec.BOOL).copyOnDeath().build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}