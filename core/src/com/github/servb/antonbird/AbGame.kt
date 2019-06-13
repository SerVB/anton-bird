package com.github.servb.antonbird

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.github.servb.antonbird.helper.AssetLoader
import com.github.servb.antonbird.screen.GameScreen

class AbGame : Game() {

    override fun create() {
        Gdx.app.log(TAG, "Created")
        AssetLoader.load()
        setScreen(GameScreen())
    }

    override fun dispose() {
        super.dispose()
        AssetLoader.dispose()
    }

    companion object {
        private const val TAG = "AbGame"
    }
}
