package de.royzer.perspektive.imported.settings;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import org.jetbrains.annotations.ApiStatus;

@Config(
        name = "perspektive"
)
@ApiStatus.Internal
public class PerspektiveSettingsFile implements ConfigData {
    public boolean shouldReturnToFirstPerson;
    public double cameraDistance;
    public boolean blockInventoryScrolling;
    public boolean scrollingEnabled;
    public boolean cameraDistanceAlsoIn3rdPerson;

    public PerspektiveSettingsFile() {
        this.shouldReturnToFirstPerson = false;
        this.cameraDistance = 0.0;
        this.blockInventoryScrolling = true;
        this.scrollingEnabled = true;
        this.cameraDistanceAlsoIn3rdPerson = false;
    }
}


