package com.github.servb.antonbird.gameobject

class Grass(x: Float, y: Float, width: Int, height: Int, scrollSpeed: Float) :
        Scrollable(x, y, width, height, scrollSpeed) {

    fun onRestart(x: Float, scrollSpeed: Float) {
        position.x = x
        velocity.x = scrollSpeed
    }
}
