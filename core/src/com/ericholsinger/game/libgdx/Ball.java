package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int id;
    Color color;
    int x;
    int y;
    int r;
    int xSpeed;
    int ySpeed;
    Sound bounceSound;
    Sound offscreenSound;
    boolean destroyed = false;

    public Ball(int id, Color color, int x, int y, int r, int xSpeed, int ySpeed) {
        this.id = id;
        this.color = color;

        this.x = x;
        this.y = y;
        this.r = r;

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        bounceSound = Gdx.audio.newSound(Gdx.files.internal("data/wall.wav"));
        offscreenSound = Gdx.audio.newSound(Gdx.files.internal("data/offscreen.wav"));
    }

    public void reset() {
        destroyed = false;
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;

        boolean bounce = false;
        if (x > (Gdx.graphics.getWidth() - r) || x < r) {
            xSpeed = -xSpeed;
            bounce = true;
        }
        if (y >= (Gdx.graphics.getHeight() - r)) {
            ySpeed = -ySpeed;
            bounce = true;
        }
        if (bounce) {
            bounceSound.play();
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
        // bottom border
        if (y <= r) {
            destroyed = true;
            offscreenSound.play();
            xSpeed = 0;
            ySpeed = 0;
        }
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, r);
    }

    public void checkCollision(Paddle paddle) {
        if (collidesWith(paddle)){
            ySpeed = -ySpeed;
            color = Color.WHITE;
            paddle.playSound();
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

    public void checkCollision(Block block) {
        if(collidesWith(block)){
            ySpeed = -ySpeed;
            color = Color.WHITE;
            block.destroyed = true;
        }
    }

    private boolean collidesWith(Block block) {
        if(block.x < x + r && block.x + block.w > x - r ) {
            if (y + r > block.y && y - r < block.y + block.h) {
                return true;
            }
        }
        return false;
    }
}
