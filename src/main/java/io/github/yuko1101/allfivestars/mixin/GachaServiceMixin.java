package io.github.yuko1101.allfivestars.mixin;

import emu.lunarcore.game.gacha.GachaBanner;
import emu.lunarcore.game.gacha.GachaService;
import io.github.yuko1101.allfivestars.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GachaService.class, remap = false)
public class GachaServiceMixin {
    @Redirect(method = "doPulls", at = @At(value = "INVOKE", target = "Lemu/lunarcore/game/gacha/GachaBanner;getEventChance()I"))
    private int getEventChance(GachaBanner instance) {
        if (!ConfigManager.isEnabled()) return instance.getEventChance();
        int pickupChance = ConfigManager.getPickupChance();
        return pickupChance == -1 ? instance.getEventChance() : pickupChance;
    }
    @ModifyConstant(method = "doPulls", constant = @Constant(intValue = 60))
    private int getFiveStarsChance(int original) {
        if (!ConfigManager.isEnabled()) return original;
        int fiveStarsChance = ConfigManager.getFiveStarsChance();
        return fiveStarsChance == -1 ? original : fiveStarsChance;
    }
}