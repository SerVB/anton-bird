package com.github.servb.antonbird.gameobject

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import java.util.*

class Pipe(x: Float, y: Float, width: Int, height: Int,
           scrollSpeed: Float, private val groundY: Float) : Scrollable(x, y, width, height, scrollSpeed) {

    private val r: Random = Random()

    private val skullUp: Rectangle = Rectangle()
    private val skullDown: Rectangle = Rectangle()
    private val barUp: Rectangle = Rectangle()
    private val barDown: Rectangle = Rectangle()

    var isScored = false

    override fun reset(newX: Float) {
        super.reset(newX)
        height = r.nextInt(90) + 15
        isScored = false
    }

    override fun update(delta: Float) {
        super.update(delta)

        barUp.set(position.x, position.y, width.toFloat(), height.toFloat())
        barDown.set(position.x, position.y + height.toFloat() + VERTICAL_GAP.toFloat(), width.toFloat(),
                groundY - (position.y + height.toFloat() + VERTICAL_GAP.toFloat()))

        skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height - SKULL_HEIGHT,
                SKULL_WIDTH.toFloat(), SKULL_HEIGHT.toFloat())
        skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
                SKULL_WIDTH.toFloat(), SKULL_HEIGHT.toFloat())

    }

    fun collides(bird: Anton): Boolean {
        return if (position.x < bird.x + bird.width) {
            Intersector.overlaps(bird.boundingCircle, barUp) ||
                    Intersector.overlaps(bird.boundingCircle, barDown) ||
                    Intersector.overlaps(bird.boundingCircle, skullUp) ||
                    Intersector.overlaps(bird.boundingCircle, skullDown)
        } else {
            false
        }
    }

    companion object {
        private const val VERTICAL_GAP = 45
        private const val SKULL_WIDTH = 24
        private const val SKULL_HEIGHT = 11
    }
}
