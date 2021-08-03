package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Block {
    int row;
    Color color;
    int x;
    int y;
    int w;
    int h;
    boolean destroyed = false;
    Sound sound;

    public Block(int row, Color color, int x, int y, int w, int h, Sound sound) {
        this.row = row;
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

    public void playSound() {

    }
}
