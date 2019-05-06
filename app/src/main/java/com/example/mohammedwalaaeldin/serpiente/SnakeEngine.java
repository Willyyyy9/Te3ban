package com.example.mohammedwalaaeldin.serpiente;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.provider.SyncStateContract.Helpers.update;

public class SnakeEngine extends SurfaceView implements Runnable {
    private Thread thread = null;
    private Context context;
    public enum Heading {UP, RIGHT, DOWN, LEFT}
    private Heading heading = Heading.RIGHT;
    private int screenX;
    private int screenY;
    private int snakeLength;
    private int bobX;
    private int bobY;
    private int blockSize;
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;
    private long nextFrameTime;
    private final long FPS = 10;
    private final long MILLIS_PER_SECOND = 1000;
    private int score;
    private int[] snakeXs;
    private int[] snakeYs;
    private volatile boolean isPlaying;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int rectY;
    private int rect;
    private Paint paint;
    public SnakeEngine(Context context , Point size) {
        super(context);
        context = context;

        screenX = size.x; //720
        screenY = size.y; //1184

        // Work out how many pixels each block is
        blockSize = screenX / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        numBlocksHigh = screenY / blockSize;
        rectY = (screenY/20);



        // Set the sound up



        // Initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // If you score 200 you are rewarded with a crash achievement!
        snakeXs = new int[200];
        snakeYs = new int[200];

        // Start the game
        newGame();

    }

    @Override
    public void run() {
        while (isPlaying) {

            // Update 10 times a second
            if( updateRequired()) {
                update();
                draw();
            }

        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }
    public void newGame() {
        // Start with a single snake segment
        snakeLength = 1;
        snakeXs[0] = NUM_BLOCKS_WIDE / 2;
        snakeYs[0] = numBlocksHigh / 2;

        // Get Bob ready for dinner
        spawnBob();

        // Reset the score
        score = 0;

        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }
    public void spawnBob() {
        Random random = new Random();
        bobX = random.nextInt(NUM_BLOCKS_WIDE-4-5+1) + 5;
        bobY = random.nextInt(numBlocksHigh-4-5+1) + 5;
    }


    private void eatBob() {
        //  Got him!
        // Increase the size of the snake
        snakeLength++;
        //replace Bob
        // This reminds me of Edge of Tomorrow. Oneday Bob will be ready!
        spawnBob();
        //add to the score
        score = score + 1;
    }
    private void moveSnake(){
        // Move the body
        for (int i = snakeLength; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];

            // Exclude the head because
            // the head has nothing in front of it
        }

        // Move the head in the appropriate heading
        switch (heading) {
            case UP:
                snakeYs[0]--;
                break;

            case RIGHT:
                snakeXs[0]++;
                break;

            case DOWN:
                snakeYs[0]++;
                break;

            case LEFT:
                snakeXs[0]--;
                break;
        }
    }
    private boolean detectDeath(){
        // Has the snake died?
        boolean dead = false;


        // Hit the screen edge
        if (snakeXs[0] == 3) dead = true;
        if (snakeXs[0] >= NUM_BLOCKS_WIDE-3) dead = true;
        if (snakeYs[0] == 3) dead = true;
        if (snakeYs[0] == numBlocksHigh-3) dead = true;

        // Eaten itself?
        for (int i = snakeLength - 1; i > 0; i--) {
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                dead = true;
            }
        }

        return dead;
    }
    public void update() {
        // Did the head of the snake eat Bob?
        if (snakeXs[0] == bobX && snakeYs[0] == bobY) {
            eatBob();
        }

        moveSnake();

        if (detectDeath()) {

            //start again
                    newGame();




        }
    }
    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Fill the screen with Game Code School blue
            canvas.drawColor(Color.argb(255, 255, 255, 255));

            // Set the color of the paint to draw the snake white
            paint.setColor(Color.rgb(86,201,255));

            // Draw the snake one block at a time
            for (int i = 0; i < snakeLength; i++) {
                canvas.drawRect(snakeXs[i] * blockSize,
                        (snakeYs[i] * blockSize),
                        (snakeXs[i] * blockSize) + blockSize,
                        (snakeYs[i] * blockSize) + blockSize,
                        paint);
            }
            paint.setColor(Color.rgb(6, 27, 61));
            canvas.drawRect(0,0,screenX,(blockSize*3),paint);
            canvas.drawRect(0,0,(blockSize*3),screenY,paint);
            canvas.drawRect(0,screenY-(blockSize*3),screenX,screenY,paint);
            canvas.drawRect(screenX-(blockSize*3),0,screenX,screenY,paint);
            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(45);
            canvas.drawText("Score: " + score, 20, 40, paint);

            // Set the color of the paint to draw Bob red
            paint.setColor(Color.argb(255, 255, 0, 0));

            // Draw Bob
            canvas.drawRect(bobX * blockSize,
                    (bobY * blockSize),
                    (bobX * blockSize) + blockSize,
                    (bobY * blockSize) + blockSize,
                    paint);
            paint.setColor(Color.rgb(255,255,255));
            canvas.drawRect(25,25,25,25,paint);



            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    public boolean updateRequired() {

        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenX / 2) {
                    switch(heading){
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch(heading){
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }

}

