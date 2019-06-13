package com.github.servb.antonbird.gameworld

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.github.servb.antonbird.gameobject.Anton
import com.github.servb.antonbird.gameobject.ScrollHandler
import com.github.servb.antonbird.helper.AssetLoader

class GameWorld(private val midPointY: Int) {

    val bird: Anton = Anton(33f, midPointY - 5f, 17, 12)
    val scroller: ScrollHandler = ScrollHandler(this, midPointY + 66f)

    private val ground: Rectangle = Rectangle(0f, midPointY + 66f, 136f, 11f)

    var score = 0
        private set

    private var currentState = GameState.READY

    val isReady: Boolean get() = currentState == GameState.READY
    val isGameOver: Boolean get() = currentState == GameState.GAME_OVER
    val isHighScore: Boolean get() = currentState == GameState.HIGH_SCORE

    fun start() {
        currentState = GameState.RUNNING
    }

    fun restart() {
        currentState = GameState.READY
        score = 0
        bird.onRestart(midPointY - 5f)
        scroller.onRestart()
    }

    fun update(delta: Float) = when (currentState) {
        GameState.READY -> updateReady(delta)
        GameState.RUNNING -> updateRunning(delta)
        else -> Unit
    }

    private fun updateReady(delta: Float) {
    }

    private fun updateRunning(delta: Float) {
        @Suppress("NAME_SHADOWING") val delta = minOf(delta, 0.15f)

        bird.update(delta)
        scroller.update(delta)

        if (scroller.collides(bird) && bird.isAlive) {
            scroller.stop()
            bird.die()
            AssetLoader.dead.random().play()
        }

        if (Intersector.overlaps(bird.boundingCircle, ground)) {
            scroller.stop()
            bird.die()
            bird.decelerate()

            if (score > AssetLoader.highScore) {
                AssetLoader.highScore = score
                currentState = GameState.HIGH_SCORE
            } else {
                currentState = GameState.GAME_OVER
            }
        }
    }

    fun addScore(increment: Int) {
        score += increment
    }

    enum class GameState {
        READY,
        RUNNING,
        GAME_OVER,
        HIGH_SCORE,
    }
}
