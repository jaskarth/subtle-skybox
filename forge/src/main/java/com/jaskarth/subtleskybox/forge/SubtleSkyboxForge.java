package com.jaskarth.subtleskybox.forge;

import com.jaskarth.subtleskybox.SubtleSkybox;
import net.minecraftforge.fml.common.Mod;

@Mod(SubtleSkybox.MOD_ID)
public final class SubtleSkyboxForge {
    public SubtleSkyboxForge() {

        // Run our common setup.
        SubtleSkybox.init();
    }
}
