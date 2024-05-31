package io.github.yuko1101.allfivestars.config;

import com.google.gson.JsonObject;
import emu.lunarcore.LunarCore;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    public static final ConfigFile configFile;
    public static final ChanceConfig fiveStars;
    public static final ChanceConfig fourStars;

    static {
        try {
            configFile = new ConfigFile(new File(LunarCore.getConfig().getDataDir(),"AllFiveStars.json"), new JsonObject()).load();
            fiveStars = new ChanceConfig(5);
            fourStars = new ChanceConfig(4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ChanceConfig {
        public int stars;
        public ConfigFile config;

        protected ChanceConfig(int stars) throws IOException {
            this.stars = stars;
            var key = stars + "stars";
            this.config = configFile.get(key);
            if (!config.exists()) {
                configFile.set(key, new JsonObject()).save();
            }
        }

        public int getPickupChance() {
            if (!config.has("pickupChance")) {
                setPickupChance(-1);
            }
            return config.getValue("pickupChance").getAsInt();
        }
        public void setPickupChance(int pickupChance) {
            try {
                config.set("pickupChance", pickupChance).save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public int getBaseChance() {
            if (!config.has("baseChance")) {
                setBaseChance(10000);
            }
            return config.getValue("baseChance").getAsInt();
        }
        public void setBaseChance(int baseChance) {
            try {
                config.set("baseChance", baseChance).save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isEnabled() {
        if (!configFile.has("isEnabled")) {
            setIsEnabled(true);
        }
        return configFile.getValue("isEnabled").getAsBoolean();
    }
    public static void setIsEnabled(boolean isEnabled) {
        try {
            configFile.set("isEnabled", isEnabled);
            configFile.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChanceConfig getChanceConfig(int stars) {
        return stars == 5 ? fiveStars : fourStars;
    }
}
