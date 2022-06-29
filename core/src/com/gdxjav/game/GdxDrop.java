package com.gdxjav.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GdxDrop extends ApplicationAdapter {

    //images are textures
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture drop, bucket;
    Music rainMusic;
    Sound dropSound;

    private Array<Rectangle> raindrops;

    private long lastDropTime;//nanoseconds

    Rectangle bucketRect;


    private void spawnRainDrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    @Override
    public void create(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        bucketRect = new Rectangle();
        bucketRect.x = 800/2 - 64/2;//bucket is 64*64
        bucketRect.y = 20;//rendering starts from the bottom left
        bucketRect.width = 64;
        bucketRect.height = 64;



        //assets
        drop = new Texture(Gdx.files.internal("drop.png"));
        bucket = new Texture(Gdx.files.internal("bucket.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        raindrops = new Array<>();
        spawnRainDrop();

        rainMusic.setLooping(true);
        rainMusic.setVolume(1);
        rainMusic.play();

    }

    @Override
    public void render(){

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRainDrop();//every second, a new raindrop is added

        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {//makes raindrops move and remove them
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) iter.remove();

            if(raindrop.overlaps(bucketRect)) {
                dropSound.play();
                iter.remove();
            }

        }



        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucketRect.x -= 200 * Gdx.graphics.getDeltaTime();//now the bucket moves at a speed of 200 pixels per second
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucketRect.x += 200 * Gdx.graphics.getDeltaTime();

        if(bucketRect.x < 0) bucketRect.x = 0;
        if(bucketRect.x > 800 - 64) bucketRect.x = 800 - 64;


        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bucket, bucketRect.x, bucketRect.y);
        for(Rectangle rectangle : raindrops){
            batch.draw(drop, rectangle.x, rectangle.y);
        }
        batch.end();
    }

    @Override
    public void dispose(){
        rainMusic.dispose();
        batch.dispose();
        drop.dispose();
        bucket.dispose();
        dropSound.dispose();
    }
}