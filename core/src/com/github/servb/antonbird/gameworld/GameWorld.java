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

    private boolean isAlive = true;

    public GameWorld(final int midPointY) {
        bird = new Anton(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(midPointY + 66);
    }

    public final void update(final float delta) {
        bird.update(delta);
        scroller.update(delta);

        if (isAlive && scroller.collides(bird)) {
            scroller.stop();
            AssetLoader.dead[new Random().nextInt(AssetLoader.DIE_SOUND_COUNT)].play();
            isAlive = false;
        }
    }

    public final Anton getBird() {
        return bird;
    }

    public final ScrollHandler getScroller() {
        return scroller;
    }
}
