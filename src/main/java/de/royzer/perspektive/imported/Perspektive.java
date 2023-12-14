package de.royzer.perspektive.imported;

import com.mojang.blaze3d.platform.InputConstants;
import de.royzer.perspektive.imported.settings.PerspektiveSettingsFile;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;


public class Perspektive {
    // the pitch and yaw of the player when in freecam mode
    public static Float pitch = 0F;
    public static Float yaw = 0F;
    private static boolean freeLookEnabled = false;
    public static boolean getFreeLookEnabled() {
        return freeLookEnabled;
    }
    public static boolean getFreeLookToggled() {
        return freeLookToggled;
    }
    private static boolean freeLookToggled = false;
    private static CameraType perspectiveBefore = CameraType.FIRST_PERSON;


    private static KeyMapping useKeybind = KeyBindingHelper.registerKeyBinding(
            new KeyMapping("Freelook", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, "Perspektive")
    );
    private static KeyMapping toggleKeybind = KeyBindingHelper.registerKeyBinding(
            new KeyMapping("Toggle Freelook", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, "Perspektive")
    );
    public static PerspektiveSettingsFile config;

    public static void init() {
        AutoConfig.register(PerspektiveSettingsFile.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(PerspektiveSettingsFile.class).getConfig();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKeybind.consumeClick()) {
                freeLookEnabled = true;
                if (!freeLookToggled) perspectiveBefore = Minecraft.getInstance().options.getCameraType();
                Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                freeLookToggled = !freeLookToggled;
            }
            if (useKeybind.isDown()) {
                if (!freeLookToggled){
                    if (!freeLookEnabled)
                        perspectiveBefore = Minecraft.getInstance().options.getCameraType();
                    freeLookEnabled = true;
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            } else if (freeLookEnabled && !freeLookToggled) {
                freeLookEnabled = false;
                Minecraft.getInstance().options.setCameraType(config.shouldReturnToFirstPerson ? CameraType.FIRST_PERSON : perspectiveBefore);
            }
        });
    }
}