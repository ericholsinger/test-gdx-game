package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestGdxGame extends ApplicationAdapter {
	private final int BALL_SPEED = 5;
	private final int BALL_RADIUS = 5;

	private final int BLOCK_ROWS = 5;
	private final int BLOCK_COLS = 14;
	private final int BLOCK_HEIGHT = 10;
	private final int BLOCK_WIDTH = 45;
	private final int BLOCK_SPACE = 4;

	private final int PADDLE_WIDEST = 100;
	private final int PADDLE_NORMAL = 50;
	private final int PADDLE_NARROW = 25;
	private final int PADDLE_HEIGHT = 5;

	private int score = 0;

	private final ArrayList<Color> ROW_COLORS = new ArrayList<>(
			Arrays.asList(Color.RED,
					Color.ORANGE,
					Color.YELLOW,
					Color.GREEN,
					Color.BLUE));

	private Random r = new Random();

	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;

	private Ball ball;
	private Paddle paddle;
	private ArrayList<Block> blocks;

	@Override
	public void create () {
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.LIGHT_GRAY);

		ball = createBall();
		paddle = createPaddle();
		blocks = createBlocks();
	}

	private ArrayList<Block> createBlocks() {
		ArrayList<Block> blocks = new ArrayList<>();
		Color color;

		int top = Gdx.graphics.getHeight() - 20;

		for (int row = 0; row < BLOCK_ROWS; ++row) {
			for (int col = 0; col < BLOCK_COLS; ++col) {
				color = ROW_COLORS.get(row);
				blocks.add(new Block(10 * BLOCK_ROWS - (row * 10), color, BLOCK_SPACE + (col * BLOCK_WIDTH + col * BLOCK_SPACE), top - (row * BLOCK_HEIGHT + row * BLOCK_SPACE), BLOCK_WIDTH, BLOCK_HEIGHT));
			}
		}
		return blocks;
	}

	private Paddle createPaddle() {
		return new Paddle(0, Color.LIME, Gdx.graphics.getWidth() / 2, 50, PADDLE_WIDEST, PADDLE_HEIGHT);
	}

	private Ball createBall() {
		int x, y, radius, xSpeed, ySpeed;

		radius = BALL_RADIUS;
		x = 200;
		y = Gdx.graphics.getHeight() - 200;
		xSpeed = BALL_SPEED * (x > (Gdx.graphics.getWidth() / 2) ? -1 : 1);
		ySpeed = BALL_SPEED * (y > (Gdx.graphics.getHeight() / 2) ? -1 : 1);
		return new Ball(0, Color.LIME, x, y, radius, xSpeed, ySpeed);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		ball.update();
		ball.draw(shape);

		ball.checkCollision(paddle);
		for (Block block: blocks) {
			if (!block.destroyed) {
				ball.checkCollision(block);
				if (block.destroyed) {
					score += block.value;
				}
				block.draw(shape);
			}
		}

		paddle.update();
		paddle.draw(shape);
		shape.end();

		batch.begin();
		font.draw(batch, String.format("%05d", score), Gdx.graphics.getWidth() - 50, 20 );
		batch.end();
	}
	
	@Override
	public void dispose () {
		shape.dispose();
	}
}
