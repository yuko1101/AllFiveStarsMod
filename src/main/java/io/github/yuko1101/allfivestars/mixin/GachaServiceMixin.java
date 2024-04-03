package io.github.yuko1101.allfivestars.mixin;

import emu.lunarcore.game.gacha.GachaService;
import emu.lunarcore.game.gacha.PlayerGachaBannerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GachaService.class, remap = false)
public class GachaServiceMixin {
    @Redirect(method = "doPulls", at = @At(value = "INVOKE", target = "Lemu/lunarcore/game/gacha/PlayerGachaBannerInfo;getPity5()I"))
    private int getPity5(PlayerGachaBannerInfo instance) {
        return 89;
    }
}