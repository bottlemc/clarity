package com.github.glassmc.clarity.v1_8_9;

import com.github.glassmc.loader.GlassLoader;
import com.github.glassmc.loader.Listener;
import com.github.glassmc.loader.util.Identifier;

public class ClarityInitializeListener implements Listener {

    @Override
    public void run() {
        GlassLoader.getInstance().registerTransformer(ClarityTransformer.class);
        GlassLoader.getInstance().registerReloadClass(Identifier.parse("net/minecraft/client/MinecraftClient").getClassName());
    }

}
