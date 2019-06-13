package com.github.servb.antonbird.gameworld

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.github.servb.antonbird.gameobject.Anton
import com.github.servb.antonbird.gameobject.Grass
import com.github.servb.antonbird.gameobject.Pipe
import com.github.servb.antonbird.gameobject.ScrollHandler
import com.github.servb.antonbird.helper.AssetLoader

class GameRenderer(private val world: GameWorld, private val gameHeight: Int, private val midPointY: Int) {
    // Game Objects
    private val bird: Anton
    private val scroller: ScrollHandler
    private val frontGrass: Grass
    private val backGrass: Grass
    private val pipe1: Pipe
    private val pipe2: Pipe
    private val pipe3: Pipe

    // Game Assets
    private val bg: TextureRegion
    private val grass: TextureRegion
    private val birdAnimation: Animation<TextureRegion>
    private val birdMid: TextureRegion
    private val birdDown: TextureRegion
    private val birdUp: TextureRegion
    private val skullUp: TextureRegion
    private val skullDown: TextureRegion
    private val bar: TextureRegion
    private val cam: OrthographicCamera
    private val shapeRenderer: ShapeRenderer

    private val batcher: SpriteBatch

    init {
        cam = OrthographicCamera().apply {
            setToOrtho(true, 136f, 204f)
        }

        batcher = SpriteBatch().apply {
            projectionMatrix = cam.combined
        }

        shapeRenderer = ShapeRenderer().apply {
            projectionMatrix = cam.combined
        }

        bird = world.bird
        scroller = world.scroller
        frontGrass = scroller.grass1
        backGrass = scroller.grass2
        pipe1 = scroller.pipe1
        pipe2 = scroller.pipe2
        pipe3 = scroller.pipe3

        bg = AssetLoader.bg
        grass = AssetLoader.grass
        birdAnimation = AssetLoader.birdAnimation
        birdMid = AssetLoader.bird
        birdDown = AssetLoader.birdDown
        birdUp = AssetLoader.birdUp
        skullUp = AssetLoader.skullUp
        skullDown = AssetLoader.skullDown
        bar = AssetLoader.bar
    }

    fun render(runTime: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.begin(ShapeType.Filled)

        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1f)
        shapeRenderer.rect(0f, 0f, 136f, (midPointY + 66).toFloat())

        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1f)
        shapeRenderer.rect(0f, (midPointY + 66).toFloat(), 136f, 11f)

        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1f)
        shapeRenderer.rect(0f, (midPointY + 77).toFloat(), 136f, 52f)

        shapeRenderer.end()

        batcher.begin()
        batcher.disableBlending()
//        batcher.draw(bg, 0f, (midPointY + 23).toFloat(), 136f, 43f)

        drawGrass()

        drawPipes()
        batcher.enableBlending()

        drawSkulls()

        if (bird.shouldNotFlap()) {
            batcher.draw(birdMid, bird.x, bird.y,
                    bird.width / 2.0f, bird.height / 2.0f,
                    bird.width.toFloat(), bird.height.toFloat(), 1f, 1f, bird.rotation)

        } else {
            batcher.draw(birdAnimation.getKeyFrame(runTime), bird.x,
                    bird.y, bird.width / 2.0f,
                    bird.height / 2.0f, bird.width.toFloat(), bird.height.toFloat(),
                    1f, 1f, bird.rotation)
        }

        if (world.isReady) {
            AssetLoader.shadow.draw(batcher, "Touch me", (136 / 2) - 42f, 76f)
            AssetLoader.font.draw(batcher, "Touch me", (136 / 2) - (42f - 1f), 75f)
        } else {
            if (world.isGameOver || world.isHighScore) {
                if (world.isGameOver) {
                    AssetLoader.shadow.draw(batcher, "Game Over", 25f, 56f)
                    AssetLoader.font.draw(batcher, "Game Over", 24f, 55f)

                    AssetLoader.shadow.draw(batcher, "High Score:", 23f, 106f)
                    AssetLoader.font.draw(batcher, "High Score:", 22f, 105f)

                    val highScore = AssetLoader.highScore.toString()

                    // Draw shadow first
                    AssetLoader.shadow.draw(batcher, highScore, 136 / 2 - 3f * highScore.length, 128f)
                    // Draw text
                    AssetLoader.font.draw(batcher, highScore, 136 / 2 - (3f * highScore.length - 1), 127f)
                } else {
                    AssetLoader.shadow.draw(batcher, "High Score!", 19f, 56f)
                    AssetLoader.font.draw(batcher, "High Score!", 18f, 55f)
                }

                AssetLoader.shadow.draw(batcher, "Try again?", 23f, 76f)
                AssetLoader.font.draw(batcher, "Try again?", 24f, 75f)
            }

            val score = world.score.toString()
            AssetLoader.shadow.draw(batcher, score, (136 / 2 - 3 * score.length).toFloat(), 12f)
            AssetLoader.font.draw(batcher, score, (136 / 2 - (3 * score.length - 1)).toFloat(), 11f)
        }

        batcher.end()
    }

    private fun drawGrass() {
        batcher.draw(grass, frontGrass.x, frontGrass.y,
                frontGrass.width.toFloat(), frontGrass.height.toFloat())
        batcher.draw(grass, backGrass.x, backGrass.y,
                backGrass.width.toFloat(), backGrass.height.toFloat())
    }

    private fun drawSkulls() {
        batcher.draw(skullUp, pipe1.x - 1,
                pipe1.y + pipe1.height - 14, 24f, 14f)
        batcher.draw(skullDown, pipe1.x - 1,
                pipe1.y + pipe1.height.toFloat() + 45f, 24f, 14f)

        batcher.draw(skullUp, pipe2.x - 1,
                pipe2.y + pipe2.height - 14, 24f, 14f)
        batcher.draw(skullDown, pipe2.x - 1,
                pipe2.y + pipe2.height.toFloat() + 45f, 24f, 14f)

        batcher.draw(skullUp, pipe3.x - 1,
                pipe3.y + pipe3.height - 14, 24f, 14f)
        batcher.draw(skullDown, pipe3.x - 1,
                pipe3.y + pipe3.height.toFloat() + 45f, 24f, 14f)
    }

    private fun drawPipes() {
        batcher.draw(bar, pipe1.x, pipe1.y, pipe1.width.toFloat(),
                pipe1.height.toFloat())
        batcher.draw(bar, pipe1.x, pipe1.y + pipe1.height.toFloat() + 45f,
                pipe1.width.toFloat(), (midPointY + 66 - (pipe1.height + 45)).toFloat())

        batcher.draw(bar, pipe2.x, pipe2.y, pipe2.width.toFloat(),
                pipe2.height.toFloat())
        batcher.draw(bar, pipe2.x, pipe2.y + pipe2.height.toFloat() + 45f,
                pipe2.width.toFloat(), (midPointY + 66 - (pipe2.height + 45)).toFloat())

        batcher.draw(bar, pipe3.x, pipe3.y, pipe3.width.toFloat(),
                pipe3.height.toFloat())
        batcher.draw(bar, pipe3.x, pipe3.y + pipe3.height.toFloat() + 45f,
                pipe3.width.toFloat(), (midPointY + 66 - (pipe3.height + 45)).toFloat())
    }
}
