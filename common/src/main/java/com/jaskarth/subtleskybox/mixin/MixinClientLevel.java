package com.jaskarth.subtleskybox.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel extends Level {
    protected MixinClientLevel(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void subtleSkybox$ChangeColor(Vec3 vec3, float f, CallbackInfoReturnable<Vec3> cir) {
        if (this.dimension() != OVERWORLD) {
            return;
        }
        Vec3 color = cir.getReturnValue();

        double red = color.x;
        double green = color.y;
        double blue = color.z;


        long time = this.dayTime();
        time += 2000;
        time %= 24000;
        double delta = time / 24000.0;
        double lerp = Mth.clampedMap(delta, 0.5, 0.55, 0, 1);
        lerp *= Mth.clampedMap(delta, 0.95, 1, 1, 0);

        if (delta > 0.5) {
            red += 0.05 * lerp;
            green += 0.025 * lerp;
            blue += 0.1 * lerp;
        }

        cir.setReturnValue(new Vec3(red, green, blue));
    }
}
