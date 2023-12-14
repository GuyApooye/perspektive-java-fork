package de.royzer.perspektive.imported.mixinsj;

import de.royzer.perspektive.imported.Perspektive;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class EntityMixinJ {
    public static void blockPlayerRotation(double deltaX , double deltaY , CallbackInfo ci ) {
        if (Perspektive.getFreeLookEnabled()) {
            Perspektive.pitch = Mth.clamp((float) (Perspektive.pitch + (deltaY * 0.15)), -90F, 90F);
            Perspektive.yaw += (float) (deltaX*0.15);
            ci.cancel();
        }
    }
}
