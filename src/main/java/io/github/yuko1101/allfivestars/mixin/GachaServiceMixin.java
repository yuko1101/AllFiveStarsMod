package io.github.yuko1101.allfivestars.mixin;

import emu.lunarcore.game.gacha.GachaBanner;
import emu.lunarcore.game.gacha.GachaService;
import io.github.yuko1101.allfivestars.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = GachaService.class, remap = false)
public class GachaServiceMixin {
    @Redirect(method = "doPulls", at = @At(value = "INVOKE", target = "Lemu/lunarcore/game/gacha/GachaBanner;getEventChance()I"))
    private int getEventChance(GachaBanner instance) {
        if (!ConfigManager.isEnabled()) return instance.getEventChance();
        int pickupChance = ConfigManager.fiveStars.getPickupChance();
        return pickupChance == -1 ? instance.getEventChance() : pickupChance;
    }
    @ModifyConstant(method = "doPulls", constant = @Constant(intValue = 60))
    private int getFiveStarsChance(int original) {
        if (!ConfigManager.isEnabled()) return original;
        int fiveStarsChance = ConfigManager.fiveStars.getBaseChance();
        return fiveStarsChance == -1 ? original : fiveStarsChance;
    }
    @ModifyConstant(method = "doPulls", constant = @Constant(intValue = 50))
    private int getFourStarsPickupChance(int original) {
        if (!ConfigManager.isEnabled()) return original;
        int pickupChance = ConfigManager.fourStars.getPickupChance();
        return pickupChance == -1 ? original : 100 - pickupChance;
    }

    @ModifyConstant(method = "doPulls", constant = @Constant(intValue = 510))
    private int getFourStarsChance(int original) {
        if (!ConfigManager.isEnabled()) return original;
        int fourStarsChance = ConfigManager.fourStars.getBaseChance();
        return fourStarsChance == -1 ? original : fourStarsChance;
    }
}