package com.github.servb.antonbird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.servb.antonbird.AbGame;

public final class DesktopLauncher {

    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Anton Bird";
        config.width = 272;
        config.height = 408;
        new LwjglApplication(new AbGame(), config);
    }
}
