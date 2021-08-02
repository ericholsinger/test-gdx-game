package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int id;
    Color color;
    int x;
    int y;
    int r;
    int xSpeed;
    int ySpeed;

    public Ball(int id, Color color, int x, int y, int r, int xSpeed, int ySpeed) {
        this.id = id;
        this.color = color;

        this.x = x;
        this.y = y;
        this.r = r;

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;

        if (x > (Gdx.graphics.getWidth() - r) || x < r) {
            xSpeed = -xSpeed;
        }
        if (y > (Gdx.graphics.getHeight() - r) || y < r) {
            ySpeed = -ySpeed;
        }

        if (x < r) {
            x = r;
        }
        if (y < r ) {
            y = r;
        }
        if (x > Gdx.graphics.getWidth() - r) {
            x = Gdx.graphics.getWidth() - r;
        }
        if (y > Gdx.graphics.getHeight() - r) {
            y = Gdx.graphics.getHeight() - r;
        }
    }

    public void draw(ShapeRenderer shape, SpriteBatch batch, BitmapFont font) {
        shape.setColor(color);
        shape.circle(x, y, r);
    }

    public void checkCollision(Paddle paddle) {
        if (collidesWith(paddle)){
            ySpeed = -ySpeed;
            color = Color.WHITE;
        } else {
            color = Color.LIME;
        }
    }

    private boolean collidesWith(Paddle paddle) {
        if(paddle.x < x + r && paddle.x + paddle.w > x - r ) {
            if (y + r > paddle.y && y - r < paddle.y + paddle.h) {
                return true;
            }
        }
        return false;
    }
}
