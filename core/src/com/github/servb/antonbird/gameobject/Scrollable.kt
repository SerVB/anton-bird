package com.github.servb.antonbird.gameobject

import com.badlogic.gdx.math.Vector2

open class Scrollable(x: Float, y: Float, width: Int, height: Int, scrollSpeed: Float) {

    protected val position: Vector2 = Vector2(x, y)
    protected val velocity: Vector2 = Vector2(scrollSpeed, 0f)
    var width: Int = width
        protected set
    var height: Int = height
        protected set
    var isScrolledLeft: Boolean = false
        protected set

    val tailX: Float
        get() = position.x + width

    val x: Float
        get() = position.x

    val y: Float
        get() = position.y

    open fun update(delta: Float) {
        position.add(velocity.cpy().scl(delta))

        if (position.x + width < 0) {
            isScrolledLeft = true
        }
    }

    open fun reset(newX: Float) {
        position.x = newX
        isScrolledLeft = false
    }

    fun stop() {
        velocity.x = 0f
    }
}
