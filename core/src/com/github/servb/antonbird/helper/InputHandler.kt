package com.github.servb.antonbird.helper

import com.badlogic.gdx.InputProcessor
import com.github.servb.antonbird.gameworld.GameWorld

class InputHandler(private val world: GameWorld) : InputProcessor {

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (world.isReady) {
            world.start()
        }

        world.bird.onClick()

        if (world.isGameOver || world.isHighScore) {
            world.restart()
        }

        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

}
