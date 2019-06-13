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

class GameRenderer(private val myWorld: GameWorld, private val gameHeight: Int, private val midPointY: Int) {
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
            setToOrtho(true, 137f, 204f)
        }

        batcher = SpriteBatch().apply {
            projectionMatrix = cam.combined
        }

        shapeRenderer = ShapeRenderer().apply {
            projectionMatrix = cam.combined
        }

        bird = myWorld.bird
        scroller = myWorld.scroller
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
        batcher.draw(bg, 0f, (midPointY + 23).toFloat(), 136f, 43f)

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

        val score = myWorld.score.toString()
        AssetLoader.shadow.draw(batcher, score, (136 / 2 - 3 * score.length).toFloat(), 12f)
        AssetLoader.font.draw(batcher, score, (136 / 2 - (3 * score.length - 1)).toFloat(), 11f)

        batcher.end()

        /*shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(1f, 0f, 0f, 0.5f);
        shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y, bird.getBoundingCircle().radius);

        shapeRenderer.rect(pipe1.getBarUp().x, pipe1.getBarUp().y,
                pipe1.getBarUp().width, pipe1.getBarUp().height);
        shapeRenderer.rect(pipe2.getBarUp().x, pipe2.getBarUp().y,
                pipe2.getBarUp().width, pipe2.getBarUp().height);
        shapeRenderer.rect(pipe3.getBarUp().x, pipe3.getBarUp().y,
                pipe3.getBarUp().width, pipe3.getBarUp().height);

        shapeRenderer.rect(pipe1.getBarDown().x, pipe1.getBarDown().y,
                pipe1.getBarDown().width, pipe1.getBarDown().height);
        shapeRenderer.rect(pipe2.getBarDown().x, pipe2.getBarDown().y,
                pipe2.getBarDown().width, pipe2.getBarDown().height);
        shapeRenderer.rect(pipe3.getBarDown().x, pipe3.getBarDown().y,
                pipe3.getBarDown().width, pipe3.getBarDown().height);

        shapeRenderer.rect(pipe1.getSkullUp().x, pipe1.getSkullUp().y,
                pipe1.getSkullUp().width, pipe1.getSkullUp().height);
        shapeRenderer.rect(pipe2.getSkullUp().x, pipe2.getSkullUp().y,
                pipe2.getSkullUp().width, pipe2.getSkullUp().height);
        shapeRenderer.rect(pipe3.getSkullUp().x, pipe3.getSkullUp().y,
                pipe3.getSkullUp().width, pipe3.getSkullUp().height);

        shapeRenderer.rect(pipe1.getSkullDown().x, pipe1.getSkullDown().y,
                pipe1.getSkullDown().width, pipe1.getSkullDown().height);
        shapeRenderer.rect(pipe2.getSkullDown().x, pipe2.getSkullDown().y,
                pipe2.getSkullDown().width, pipe2.getSkullDown().height);
        shapeRenderer.rect(pipe3.getSkullDown().x, pipe3.getSkullDown().y,
                pipe3.getSkullDown().width, pipe3.getSkullDown().height);

        shapeRenderer.end();*/
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
