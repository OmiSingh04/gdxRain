package com.gdxjav.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {

	ShapeRenderer renderer;
	Ball ball;
	@Override
	public void create () {
		ball  = new Ball(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2 , 50, 3, 3);
		renderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);
		ball.update();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		ball.draw(renderer);
		renderer.end();
	}
	
	@Override
	public void dispose () {
		renderer.dispose();
	}
}
