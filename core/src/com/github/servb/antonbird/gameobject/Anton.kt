package com.github.servb.antonbird.gameobject

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.github.servb.antonbird.helper.AssetLoader

class Anton(x: Float, y: Float, val width: Int, val height: Int) {

    private val position: Vector2 = Vector2(x, y)
    private val velocity: Vector2 = Vector2(0f, 0f)
    private val acceleration: Vector2 = Vector2(0f, 460f)

    var rotation: Float = 0f
        private set

    val boundingCircle: Circle = Circle()

    var isAlive: Boolean = true
        private set

    val isFalling: Boolean
        get() = velocity.y > 110

    val x: Float
        get() = position.x

    val y: Float
        get() = position.y

    fun shouldNotFlap(): Boolean = velocity.y > 70 || !isAlive

    fun update(delta: Float) {
        velocity.add(acceleration.cpy().scl(delta))

        velocity.y = minOf(velocity.y, 200f)

        position.add(velocity.cpy().scl(delta))
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f)

        if (velocity.y < 0) {
            rotation -= 600 * delta

            if (rotation < -20) {
                rotation = -20f
            }
        }

        if (isFalling || !isAlive) {
            rotation += 480 * delta

            rotation = minOf(rotation, 80f)
        }
    }

    fun onClick() {
        if (isAlive) {
            AssetLoader.flap.play()
            velocity.y = -140f
        }
    }

    fun die() {
        isAlive = false
        velocity.y = 0f
    }

    fun decelerate() {
        acceleration.y = 0f
    }
}
