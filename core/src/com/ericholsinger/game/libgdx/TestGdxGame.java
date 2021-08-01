package com.ericholsinger.game.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestGdxGame extends ApplicationAdapter {
	ShapeRenderer shape;
	ArrayList<Ball> balls = new ArrayList<>();
	Random r = new Random();
	ArrayList<Color> colors = new ArrayList<>(
			Arrays.asList(Color.CORAL,
					Color.WHITE,
					Color.BLACK,
					Color.LIGHT_GRAY,
					Color.LIME,
					Color.ORANGE,
					Color.YELLOW,
					Color.BROWN,
					Color.SALMON,
					Color.FIREBRICK,
					Color.MAGENTA));
	
	@Override
	public void create () {
		shape = new ShapeRenderer();
		int x, y, radius, xSpeed, ySpeed;
		for (int i = 0; i < 10; i++) {
			radius = r.nextInt(99) + 1;
			x = radius + r.nextInt(Gdx.graphics.getWidth() - radius);
			y = radius + r.nextInt(Gdx.graphics.getHeight() - radius);
			xSpeed = r.nextInt(15) * (x > (Gdx.graphics.getWidth() / 2) ? -1 : 1);
			ySpeed = r.nextInt(15) * (y > (Gdx.graphics.getHeight() / 2) ? -1 : 1);
			balls.add(new Ball(i, colors.get(i), x, y, radius, xSpeed, ySpeed));
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		for (Ball ball : balls) {
			ball.update();
			ball.draw(shape);
		}
		shape.end();
	}
	
	@Override
	public void dispose () {
		shape.dispose();
	}
}
