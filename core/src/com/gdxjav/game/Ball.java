package com.gdxjav.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int x;
    int y;
    int size;
    int xSpeed;
    int ySpeed;

    public Ball(int x, int y, int size, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public void update() {
        x += xSpeed;
        y += ySpeed;
        if(x > Gdx.graphics.getWidth() - 50 || x < 50)
            xSpeed *= -1;
        if(y > Gdx.graphics.getHeight() - 50 || y < 50)
            ySpeed *= -1;
    }
    public void draw(ShapeRenderer shape) {
        shape.circle(x, y, size);
    }
}