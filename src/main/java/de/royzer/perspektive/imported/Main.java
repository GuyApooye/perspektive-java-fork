package de.royzer.perspektive.imported;

import com.mojang.logging.LogUtils;
import de.royzer.perspektive.imported.settings.PerspektiveSettingsFile;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        Perspektive.init();
        LogUtils.getLogger().info("Perspektive initialized");

    }
}
