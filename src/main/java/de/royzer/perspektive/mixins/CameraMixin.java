package de.royzer.perspektive.mixins;

import de.royzer.perspektive.imported.Perspektive;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.royzer.perspektive.imported.Perspektive.config;

@Mixin(Camera.class)
public abstract class CameraMixin {
    private boolean firstPress = true;

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Shadow
    protected abstract void move(double x, double y, double z);

    @Shadow
    protected abstract double getMaxZoom(double desiredCameraDistance);

    @Shadow
    private Entity entity;

    @Inject(
            method = "setup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;setRotation(FF)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    public void update(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (Perspektive.getFreeLookEnabled()) {
            if (firstPress) {
                Perspektive.pitch=focusedEntity.getXRot();
                Perspektive.yaw=focusedEntity.getYRot();
            }
            firstPress = false;
            this.setRotation(Perspektive.yaw, Perspektive.pitch);
        } else {
            firstPress = true;
        }
    }

    @Inject(
            method = "setup",
            at = @At("TAIL")
    )
    public void setDistance(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (Perspektive.getFreeLookEnabled() ||
                (config.cameraDistanceAlsoIn3rdPerson
                        && Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON)) {
            this.move(-this.getMaxZoom(config.cameraDistance), 0.0, 0.0);
        }
    }
}
