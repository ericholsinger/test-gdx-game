package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Block {
    int value;
    Color color;
    int x;
    int y;
    int w;
    int h;
    boolean destroyed = false;

    public Block(int id, Color color, int x, int y, int w, int h) {
        this.value = id;
        this.color = color;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(color);
            shape.rect(x, y, w, h);
        }
    }
}
