package io.github.yuko1101.allfivestars.mixin;

import emu.lunarcore.game.gacha.GachaBanner;
import emu.lunarcore.game.gacha.GachaService;
import emu.lunarcore.game.gacha.PlayerGachaBannerInfo;
import io.github.yuko1101.allfivestars.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GachaService.class, remap = false)
public class GachaServiceMixin {
    @Redirect(method = "doPulls", at = @At(value = "INVOKE", target = "Lemu/lunarcore/game/gacha/PlayerGachaBannerInfo;getPity5()I"))
    private int getPity5(PlayerGachaBannerInfo instance) {
        return ConfigManager.isEnabled() ? 89 : instance.getPity5();
    }

    @Redirect(method = "doPulls", at = @At(value = "INVOKE", target = "Lemu/lunarcore/game/gacha/GachaBanner;getEventChance()I"))
    private int getEventChance(GachaBanner instance) {
        if (!ConfigManager.isEnabled()) return instance.getEventChance();
        int pickupChance = ConfigManager.getPickupChance();
        return pickupChance == -1 ? instance.getEventChance() : pickupChance;
    }
}