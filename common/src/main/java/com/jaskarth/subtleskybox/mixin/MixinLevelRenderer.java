package com.jaskarth.subtleskybox.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    @ModifyConstant(method = "drawStars", constant = @Constant(intValue = 1500))
    private int subtleSkybox$starAmt(int v) {
        return 7500;
    }

    @ModifyConstant(method = "drawStars", constant = @Constant(floatValue = 0.15F))
    private float subtleSkybox$baseStarSize(float v) {
        return 0.05F;
    }

    @ModifyConstant(method = "drawStars", constant = @Constant(floatValue = 0.1F))
    private float subtleSkybox$addStarSize(float v) {
        return 0.25F;
    }

    @Redirect(method = "drawStars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextFloat()F", ordinal = 3))
    private float subtleSkybox$trigDistribution(RandomSource random) {
        return random.nextFloat() * random.nextFloat();
    }
}
