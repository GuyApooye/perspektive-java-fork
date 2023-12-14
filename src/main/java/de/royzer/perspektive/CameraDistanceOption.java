package de.royzer.perspektive;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

import static de.royzer.perspektive.imported.Perspektive.config;

/*
I have to do the OptionInstance in java for now since there is a remapping issue which prevents me from doing so in kotlin
 */
public class CameraDistanceOption {
    public static OptionInstance<Integer> cameraDistanceOption = new OptionInstance<>(
            "perspektive.distance",
            OptionInstance.noTooltip(),
            (optionText, value) -> Options.genericValueLabel(optionText, Component.literal(String.valueOf(config.cameraDistance))),
            new OptionInstance.IntRange(0, 640), (int) (config.cameraDistance * 10),
            integer -> config.cameraDistance = Double.parseDouble(integer.toString()) / 10);
}