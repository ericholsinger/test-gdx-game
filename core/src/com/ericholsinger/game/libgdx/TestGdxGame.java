package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class TestGdxGame extends ApplicationAdapter {
	private final int SPEED = 5;
	private final int RADIUS = 5;

	private final int PADDLE_WIDEST = 100;
	private final int PADDLE_NORMAL = 50;
	private final int PADDLE_NARROW = 25;

	private final int PADDLE_HEIGHT = 5;

	private Random r = new Random();

	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;

	private Ball ball;
	private Paddle paddle;
	
	@Override
	public void create () {
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.LIGHT_GRAY);

		ball = createBall();
		paddle = createPaddle();
	}

	private Paddle createPaddle() {
		return new Paddle(0, Color.LIME, Gdx.graphics.getWidth() / 2, 50, PADDLE_WIDEST, PADDLE_HEIGHT);
	}

	private Ball createBall() {
		int x, y, radius, xSpeed, ySpeed;

		radius = RADIUS;
		x = 200;
		y = Gdx.graphics.getHeight() - 200;
		xSpeed = SPEED * (x > (Gdx.graphics.getWidth() / 2) ? -1 : 1);
		ySpeed = SPEED * (y > (Gdx.graphics.getHeight() / 2) ? -1 : 1);
		return new Ball(0, Color.LIME, x, y, radius, xSpeed, ySpeed);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		ball.update();
		ball.draw(shape, batch, font);

		ball.checkCollision(paddle);

		paddle.update();
		paddle.draw(shape, batch, font);
		shape.end();

//		batch.begin();
//		font.draw(batch, String.format("paddle (%d, %d, %d, %d)", paddle.x, paddle.y, paddle.w, paddle.h), 200, 200);
//		font.draw(batch, String.format("ball   (%d, %d, %d)", ball.x, ball.y, ball.r), 200, 220);
//		batch.end();
	}
	
	@Override
	public void dispose () {
		shape.dispose();
	}
}
