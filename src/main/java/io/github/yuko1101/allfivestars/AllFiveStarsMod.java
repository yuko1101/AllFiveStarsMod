package io.github.yuko1101.allfivestars;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

public class AllFiveStarsMod implements ModInitializer {

    public static final String MOD_NAME = "AllFiveStarsMod";

    public static final LogCategory LOG_CATEGORY = LogCategory.create(MOD_NAME);

    @Override
    public void onInitialize() {
        Log.info(LOG_CATEGORY, "Initializing " + MOD_NAME);

    }
}
