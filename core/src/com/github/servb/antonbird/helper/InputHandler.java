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
package com.github.servb.antonbird.helper;

import com.badlogic.gdx.InputProcessor;
import com.github.servb.antonbird.gameobject.Anton;

/**
 *
 * @author SerVB
 */
public final class InputHandler implements InputProcessor {

    private final Anton myBird;

    public InputHandler(final Anton bird) {
        myBird = bird;
    }

    @Override
    public final boolean keyDown(final int keycode) {
        return false;
    }

    @Override
    public final boolean keyUp(final int keycode) {
        return false;
    }

    @Override
    public final boolean keyTyped(final char character) {
        return false;
    }

    @Override
    public final boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        myBird.onClick();
        return true;
    }

    @Override
    public final boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public final boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return false;
    }

    @Override
    public final boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public final boolean scrolled(final int amount) {
        return false;
    }

}
