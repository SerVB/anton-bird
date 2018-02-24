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
package com.github.servb.antonbird.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.github.servb.antonbird.gameobject.Anton;
import com.github.servb.antonbird.gameobject.ScrollHandler;
import com.github.servb.antonbird.helper.AssetLoader;
import java.util.Random;

/**
 *
 * @author SerVB
 */
public final class GameWorld {

    private final Anton bird;
    private final ScrollHandler scroller;

    private final Rectangle ground;

    private int score = 0;

    public GameWorld(final int midPointY) {
        bird = new Anton(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 136, 11);
    }

    public final void update(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        bird.update(delta);
        scroller.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            bird.die();
            AssetLoader.dead[new Random().nextInt(AssetLoader.DIE_SOUND_COUNT)].play();
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scroller.stop();
            bird.die();
            bird.decelerate();
        }
    }

    public final Anton getBird() {
        return bird;
    }

    public final ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(final int increment) {
        score += increment;
    }
}
