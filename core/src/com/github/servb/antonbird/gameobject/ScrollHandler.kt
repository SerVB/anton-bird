package com.github.servb.antonbird.gameobject

import com.github.servb.antonbird.gameworld.GameWorld
import com.github.servb.antonbird.helper.AssetLoader

class ScrollHandler(private val gameWorld: GameWorld, yPos: Float) {

    val grass1: Grass = Grass(0f, yPos, 143, 11, SCROLL_SPEED)
    val grass2: Grass = Grass(grass1.tailX, yPos, 143, 11, SCROLL_SPEED)

    val pipe1: Pipe = Pipe(210f, 0f, 22, 60, SCROLL_SPEED, yPos)
    val pipe2: Pipe = Pipe(pipe1.tailX + PIPE_GAP, 0f, 22, 70, SCROLL_SPEED, yPos)
    val pipe3: Pipe = Pipe(pipe2.tailX + PIPE_GAP, 0f, 22, 60, SCROLL_SPEED, yPos)

    private val pipes = listOf(pipe1, pipe2, pipe3)

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

    fun onRestart() {
        grass1.onRestart(0f, SCROLL_SPEED)
        grass2.onRestart(grass1.tailX, SCROLL_SPEED)

        pipe1.onRestart(210f, SCROLL_SPEED)
        pipe2.onRestart(pipe1.tailX + PIPE_GAP, SCROLL_SPEED)
        pipe3.onRestart(pipe2.tailX + PIPE_GAP, SCROLL_SPEED)
    }

    fun stop() {
        grass1.stop()
        grass2.stop()

        pipes.forEach { it.stop() }
    }

    fun collides(bird: Anton): Boolean {
        for (pipe in pipes) {
            if (!pipe.isScored && pipe.x + pipe.width / 2 < bird.x + bird.width) {
                gameWorld.addScore(INCREMENT)
                pipe.isScored = true

                AssetLoader.coin.play()

                break
            }
        }

        return pipes.any { it.collides(bird) }
    }

    companion object {
        private const val INCREMENT = 1
        private const val SCROLL_SPEED = -59f
        private const val PIPE_GAP = 49f
    }
}
