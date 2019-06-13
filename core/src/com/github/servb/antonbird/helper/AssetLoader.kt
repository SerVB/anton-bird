package com.github.servb.antonbird.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion

object AssetLoader {

    private const val DIE_SOUND_COUNT = 4

    private lateinit var texture: Texture
    lateinit var bg: TextureRegion
    lateinit var grass: TextureRegion

    lateinit var birdAnimation: Animation<TextureRegion>
    lateinit var bird: TextureRegion
    lateinit var birdDown: TextureRegion
    lateinit var birdUp: TextureRegion

    lateinit var skullUp: TextureRegion
    lateinit var skullDown: TextureRegion
    lateinit var bar: TextureRegion

    lateinit var dead: Array<Sound>
    lateinit var flap: Sound
    lateinit var coin: Sound

    lateinit var font: BitmapFont
    lateinit var shadow: BitmapFont

    fun load() {
        texture = Texture(Gdx.files.internal("data/texture.png")).apply {
            setFilter(TextureFilter.Nearest, TextureFilter.Nearest)
        }

        bg = TextureRegion(texture, 0, 0, 136, 43).apply {
            flip(false, true)
        }

        grass = TextureRegion(texture, 0, 43, 143, 11).apply {
            flip(false, true)
        }

        birdDown = TextureRegion(texture, 136, 0, 17, 12).apply {
            flip(false, true)
        }

        bird = TextureRegion(texture, 153, 0, 17, 12).apply {
            flip(false, true)
        }

        birdUp = TextureRegion(texture, 170, 0, 17, 12).apply {
            flip(false, true)
        }

        birdAnimation = Animation(0.06f, birdDown, bird, birdUp).apply {
            playMode = Animation.PlayMode.LOOP_PINGPONG
        }

        skullUp = TextureRegion(texture, 192 + 24, 0, 24, 14).apply {
            flip(false, true)
        }
        skullDown = TextureRegion(texture, 192, 0, 24, 14).apply {
            flip(false, true)
        }

        bar = TextureRegion(texture, 136, 16, 22, 3).apply {
            flip(false, true)
        }

        dead = Array(DIE_SOUND_COUNT) { i -> Gdx.audio.newSound(Gdx.files.internal("data/die$i.wav")) }
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"))
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"))

        font = BitmapFont(Gdx.files.internal("data/text.fnt")).apply {
            data.setScale(0.25f, -0.25f)
        }
        shadow = BitmapFont(Gdx.files.internal("data/shadow.fnt")).apply {
            data.setScale(0.25f, -0.25f)
        }
    }

    fun dispose() {
        texture.dispose()


        dead.forEach { it.dispose() }
        flap.dispose()
        coin.dispose()

        font.dispose()
        shadow.dispose()
    }
}
