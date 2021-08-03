package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Lives {
    private int total;
    private Color color;
    private final int START_X = 20;
    private final int START_Y = 20;
    private final int OFFSET_X = 15;
    private final int RADIUS = 5;

    public Lives(int total) {
        this.total = total;
        color = Color.LIME;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        for (int i = 0; i < total; ++i) {
            shape.circle(START_X + OFFSET_X * i, START_Y, RADIUS);
        }
    }
}
