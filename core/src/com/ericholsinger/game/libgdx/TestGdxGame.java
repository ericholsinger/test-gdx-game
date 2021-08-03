package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

	private ArrayList<Sound> blockSounds;

	private Random r = new Random();

	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;

	private Ball ball;
	private Paddle paddle;
	private ArrayList<Block> blocks;
	private Lives lives = new Lives(3);

	@Override
	public void create () {
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.LIGHT_GRAY);

		blockSounds = createSounds();
		ball = createBall();
		paddle = createPaddle();
		blocks = createBlocks();

		Gdx.audio.newSound(Gdx.files.internal(String.format("data/background.wav"))).loop(.25f);
	}

	private ArrayList<Block> createBlocks() {
		ArrayList<Block> blocks = new ArrayList<>();
		Color color;

		int top = Gdx.graphics.getHeight() - 20;

		for (int row = 0; row < BLOCK_ROWS; ++row) {
			for (int col = 0; col < BLOCK_COLS; ++col) {
				color = ROW_COLORS.get(row);
				blocks.add(new Block(row, color, BLOCK_SPACE + (col * BLOCK_WIDTH + col * BLOCK_SPACE), top - (row * BLOCK_HEIGHT + row * BLOCK_SPACE), BLOCK_WIDTH, BLOCK_HEIGHT, blockSounds.get(row)));
			}
		}
		return blocks;
	}

	private Paddle createPaddle() {
		return new Paddle(0, Color.LIME, Gdx.graphics.getWidth() / 2, 50, PADDLE_WIDEST, PADDLE_HEIGHT);
	}

	private ArrayList<Sound> createSounds() {
		ArrayList<Sound> sounds = new ArrayList<>();

		for (int i = 0; i < BLOCK_ROWS; ++i) {
			sounds.add(Gdx.audio.newSound(Gdx.files.internal(String.format("data/block%d.wav", i))));
		}
		return sounds;
	}

	private Ball createBall() {
		int x, y, radius, xSpeed, ySpeed;

		radius = BALL_RADIUS;
		x = 200;
		y = Gdx.graphics.getHeight() - 200;
		xSpeed = BALL_SPEED;
		ySpeed = -BALL_SPEED;
		return new Ball(0, Color.LIME, x, y, radius, xSpeed, ySpeed);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeRenderer.ShapeType.Filled);

		if (!ball.destroyed) {
			ball.update();
			ball.draw(shape);
			if (ball.destroyed) {
				lives.setTotal(lives.getTotal() - 1);
			}
		} else {
			if (lives.getTotal() > 0) {
				ball.x = 200;
				ball.y = Gdx.graphics.getHeight() - 200;
				ball.xSpeed = BALL_SPEED;
				ball.ySpeed = -BALL_SPEED;
				ball.destroyed = false;
			}
		}

		ball.checkCollision(paddle);
		lives.draw(shape);

		for (Block block : blocks) {
			if (!block.destroyed) {
				ball.checkCollision(block);
				if (block.destroyed) {
					score += 10 * BLOCK_ROWS - (block.row * 10);
					blockSounds.get(block.row).play();
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
