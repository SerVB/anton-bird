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
package com.github.servb.antonbird.gameobject;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author SerVB
 */
public final class Anton {

    private final Vector2 position;
    private final Vector2 velocity;
    private final Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    private Circle boundingCircle;

    public Anton(final float x, final float y, final int width, final int height) {
        this.width = width;
        this.height = height;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);

        boundingCircle = new Circle();
    }

    public final boolean isFalling() {
        return velocity.y > 110;
    }

    public final boolean shouldntFlap() {
        return velocity.y > 70;
    }

    public final void update(final float delta) {
        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        position.add(velocity.cpy().scl(delta));
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        if (isFalling()) {
            rotation += 480 * delta;

            if (rotation > 80) {
                rotation = 80;
            }
        }
    }

    public final void onClick() {
        velocity.y = -140;
    }

    public final float getX() {
        return position.x;
    }

    public final float getY() {
        return position.y;
    }

    public final float getWidth() {
        return width;
    }

    public final float getHeight() {
        return height;
    }

    public final float getRotation() {
        return rotation;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }
}
