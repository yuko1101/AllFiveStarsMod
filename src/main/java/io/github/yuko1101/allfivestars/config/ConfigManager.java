package io.github.yuko1101.allfivestars.config;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    public static final ConfigFile configFile;

    static {
        try {
            configFile = new ConfigFile(new File("data/allfivestars.json"), new JsonObject()).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getPickupChance() {
        if (!configFile.has("pickupChance")) {
            setPickupChance(-1);
        }
        return configFile.getValue("pickupChance").getAsInt();
    }
    public static void setPickupChance(int pickupChance) {
        try {
            configFile.set("pickupChance", pickupChance);
            configFile.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getFiveStarsChance() {
        if (!configFile.has("fiveStarsChance")) {
            setFiveStarsChance(10000);
        }
        return configFile.getValue("fiveStarsChance").getAsInt();
    }
    public static void setFiveStarsChance(int fiveStarsChance) {
        try {
            configFile.set("fiveStarsChance", fiveStarsChance);
            configFile.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}
