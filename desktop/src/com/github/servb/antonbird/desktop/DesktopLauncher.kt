package com.github.servb.antonbird.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.github.servb.antonbird.AbGame

object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            title = "Anton Bird"
            width = 272
            height = 408
        }
        LwjglApplication(AbGame(), config)
    }
}
