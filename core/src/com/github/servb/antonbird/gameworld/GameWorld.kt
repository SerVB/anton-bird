package com.github.servb.antonbird.gameworld

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.github.servb.antonbird.gameobject.Anton
import com.github.servb.antonbird.gameobject.ScrollHandler
import com.github.servb.antonbird.helper.AssetLoader

class GameWorld(midPointY: Int) {

    val bird: Anton = Anton(33f, midPointY - 5f, 17, 12)
    val scroller: ScrollHandler = ScrollHandler(this, midPointY + 66f)

    private val ground: Rectangle = Rectangle(0f, midPointY + 66f, 136f, 11f)

    var score = 0
        private set

    fun update(delta: Float) {
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
        }
    }

    fun addScore(increment: Int) {
        score += increment
    }
}
