package com.github.servb.antonbird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.github.servb.antonbird.helper.AssetLoader;
import com.github.servb.antonbird.screen.GameScreen;

public final class AbGame extends Game {

    @Override
    public final void create() {
        Gdx.app.log("AbGame", "Created");
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public final void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
