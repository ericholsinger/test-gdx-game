package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {
    int id;
    Color color;
    int x;
    int y;
    int w;
    int h;

    public Paddle(int id, Color color, int x, int y, int w, int h) {
        this.id = id;
        this.color = color;

        this.x = x;
        this.y = y;

        this.w = w;
        this.h = h;
    }

    public void update() {
        x = Gdx.input.getX();
        //y = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    public void draw(ShapeRenderer shape, Batch batch, BitmapFont font) {
        shape.setColor(color);
        shape.rect(x, y, w, h);
    }
}