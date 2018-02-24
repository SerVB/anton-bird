/*
 * Copyright 2018 SerVB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.servb.antonbird.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.github.servb.antonbird.gameworld.GameRenderer;
import com.github.servb.antonbird.gameworld.GameWorld;
import com.github.servb.antonbird.helper.InputHandler;

/**
 *
 * @author SerVB
 */
public final class GameScreen implements Screen {

    private final GameWorld world;
    private final GameRenderer renderer;

    private float runTime = 0;

    public GameScreen() {
        final float screenWidth = Gdx.graphics.getWidth();
        final float screenHeight = Gdx.graphics.getHeight();
        final float gameWidth = 136;
        final float gameHeight = screenHeight * (gameWidth / screenWidth);

        final int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointY);
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);

        Gdx.input.setInputProcessor(new InputHandler(world.getBird()));
    }

    @Override
    public final void show() {
        Gdx.app.log("GameScreen", "Show called");
    }

    @Override
    public final void render(final float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public final void resize(final int width, final int height) {

    }

    @Override
    public final void pause() {
        Gdx.app.log("GameScreen", "Pause called");
    }

    @Override
    public final void resume() {
        Gdx.app.log("GameScreen", "Resume called");
    }

    @Override
    public final void hide() {
        Gdx.app.log("GameScreen", "Hide called");
    }

    @Override
    public final void dispose() {

    }

}
