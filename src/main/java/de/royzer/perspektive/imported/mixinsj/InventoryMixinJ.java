package de.royzer.perspektive.imported.mixinsj;


import de.royzer.perspektive.imported.Perspektive;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.royzer.perspektive.imported.Perspektive.config;
import static net.minecraft.util.Mth.sign;

public class InventoryMixinJ {

    public static void onScroll(double d , CallbackInfo ci ) {
        if (Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) return;
        if (!Perspektive.getFreeLookEnabled() && !config.cameraDistanceAlsoIn3rdPerson) return;
        if (!config.scrollingEnabled) return;
        int i = sign(d);
        double nextDistance = config.cameraDistance - i;
        if (nextDistance < 0.0) config.cameraDistance = 0.0;
        else if (nextDistance > 64.0) config.cameraDistance = 64.0;
        else config.cameraDistance -= i;
        if ((config.blockInventoryScrolling && Perspektive.getFreeLookEnabled()) || (config.cameraDistanceAlsoIn3rdPerson && config.blockInventoryScrolling))
            ci.cancel();
    }
}