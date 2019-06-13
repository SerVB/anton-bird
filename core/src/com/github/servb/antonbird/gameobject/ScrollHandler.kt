package com.github.servb.antonbird.gameobject

import com.github.servb.antonbird.gameworld.GameWorld
import com.github.servb.antonbird.helper.AssetLoader

class ScrollHandler(private val gameWorld: GameWorld, yPos: Float) {

    val grass1: Grass = Grass(0f, yPos, 143, 11, SCROLL_SPEED.toFloat())
    val grass2: Grass = Grass(grass1.tailX, yPos, 143, 11, SCROLL_SPEED.toFloat())
    val pipe1: Pipe = Pipe(210f, 0f, 22, 60, SCROLL_SPEED.toFloat(), yPos)
    val pipe2: Pipe = Pipe(pipe1.tailX + PIPE_GAP, 0f, 22, 70, SCROLL_SPEED.toFloat(), yPos)
    val pipe3: Pipe = Pipe(pipe2.tailX + PIPE_GAP, 0f, 22, 60, SCROLL_SPEED.toFloat(), yPos)

    fun update(delta: Float) {
        grass1.update(delta)
        grass2.update(delta)
        pipe1.update(delta)
        pipe2.update(delta)
        pipe3.update(delta)

        when {
            pipe1.isScrolledLeft -> pipe1.reset(pipe3.tailX + PIPE_GAP)
            pipe2.isScrolledLeft -> pipe2.reset(pipe1.tailX + PIPE_GAP)
            pipe3.isScrolledLeft -> pipe3.reset(pipe2.tailX + PIPE_GAP)
        }

        when {
            grass1.isScrolledLeft -> grass1.reset(grass2.tailX)
            grass2.isScrolledLeft -> grass2.reset(grass1.tailX)
        }
    }

    fun stop() {
        grass1.stop()
        grass2.stop()
        pipe1.stop()
        pipe2.stop()
        pipe3.stop()
    }

    fun collides(bird: Anton): Boolean {
        if (!pipe1.isScored && pipe1.x + pipe1.width / 2 < bird.x + bird.width) {
            addScore(INCREMENT)
            pipe1.isScored = true
            AssetLoader.coin.play()
        } else if (!pipe2.isScored && pipe2.x + pipe2.width / 2 < bird.x + bird.width) {
            addScore(INCREMENT)
            pipe2.isScored = true
            AssetLoader.coin.play()
        } else if (!pipe3.isScored && pipe3.x + pipe3.width / 2 < bird.x + bird.width) {
            addScore(INCREMENT)
            pipe3.isScored = true
            AssetLoader.coin.play()
        }

        return pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird)
    }

    private fun addScore(increment: Int) {
        gameWorld.addScore(increment)
    }

    companion object {
        private const val INCREMENT = 1
        private const val SCROLL_SPEED = -59
        private const val PIPE_GAP = 49
    }
}
