package com.github.servb.antonbird.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.github.servb.antonbird.gameworld.GameRenderer
import com.github.servb.antonbird.gameworld.GameWorld
import com.github.servb.antonbird.helper.InputHandler

class GameScreen : Screen {

    private val world: GameWorld
    private val renderer: GameRenderer

    private var runTime = 0f

    init {
        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()
        val gameWidth = 136f
        val gameHeight = screenHeight * (gameWidth / screenWidth)

        val midPointY = (gameHeight / 2).toInt()

        world = GameWorld(midPointY)
        renderer = GameRenderer(world, gameHeight.toInt(), midPointY)

        Gdx.input.inputProcessor = InputHandler(world.bird)
    }

    override fun show() {
        Gdx.app.log(TAG, "Show called")
    }

    override fun render(delta: Float) {
        runTime += delta
        world.update(delta)
        renderer.render(runTime)
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
        Gdx.app.log(TAG, "Pause called")
    }

    override fun resume() {
        Gdx.app.log(TAG, "Resume called")
    }

    override fun hide() {
        Gdx.app.log(TAG, "Hide called")
    }

    override fun dispose() {
    }

    companion object {
        private const val TAG = "GameScreen"
    }
}
