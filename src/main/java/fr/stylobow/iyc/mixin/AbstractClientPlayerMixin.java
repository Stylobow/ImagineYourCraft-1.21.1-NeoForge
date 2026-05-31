package fr.stylobow.iyc.mixin;

import fr.stylobow.iyc.client.SkinRenderManager;
import fr.stylobow.iyc.client.skin.CustomSkinManager;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Inject(method = "getSkinTextureLocation", at = @At("HEAD"), cancellable = true)
    private void injectCustomSkin(CallbackInfoReturnable<ResourceLocation> info) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        ResourceLocation customSkin = SkinRenderManager.getPlayerSkin(player.getUUID());

        if (customSkin != null) {
            info.setReturnValue(customSkin);
        }
    }

    @Inject(method = "getCapeTextureLocation", at = @At("HEAD"), cancellable = true)
    private void injectCustomCape(CallbackInfoReturnable<ResourceLocation> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        ResourceLocation customCape = CustomSkinManager.getCapeTexture(player.getUUID());

        if (customCape != null) {
            cir.setReturnValue(customCape);
        }
    }
}