package com.example.morham20.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by CallumMorham on 3/18/18.
 */

public class PongAnimator implements Animator {

    private Random gen = new Random();//random generator

    // TODO: Random Ball Starting position and direction, but must start in middle like actual game
    // TODO: Random Ball Start speed and decaying ball speed
    private int ballStartX = 0; // starting X position of Ball
    private int ballStartY = 0; // starting Y position of Ball
    private int ballDirection;

    private boolean UpLeft = true;
    private boolean UpRight = false;
    private boolean DownLeft = false;
    private boolean DownRight = false;

    private boolean ballOut = false;

    private Paddle humanPaddle;
    private Ball ball;

    private int radius = 40;
    private int ballSpd = 100;//gen.nextInt(30) + 10;



    /**
     * Interval between animation frames: .01 seconds
     *
     * @return the time interval between frames, in milliseconds.
     */
    @Override
    public int interval() {
        return 10;
    }

    /**
     * The background color: Black.
     *
     * @return the background color onto which we will draw the image.
     */
    @Override
    public int backgroundColor() {
        return Color.rgb(0, 0, 0);
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    @Override
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    @Override
    public boolean doQuit() {
        return false;
    }

    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    @Override
    public void tick(Canvas g) {

        int screenHeight = g.getHeight();//gets height or board
        int screenWidth = g.getWidth();//gets width of board
        //Makes points for the player Paddle
        int humanPaddleTopLeftX = screenWidth - 50;
        int humanPaddleTopLeftY = (screenHeight /2) - 200;
        int humanPaddleBottomRightX = screenWidth - 10;
        int humanPaddleBottomRightY = (screenHeight /2) + 200;

        // Multiplying countX or countY changes the speed of the ball in its respected X or Y direction
        // % by the board dimensions loops the ball back into the board.
        // Don't need the % to loop the ball back anymore.
        int ballX = (ballStartX*ballSpd);    //%screenWidth;
        int ballY = (ballStartY*ballSpd);    //%screenHeight;


        // Determines how the ball moves depending on its direction
        if (UpLeft) {ballStartX--;ballStartY--;}
        if (UpRight) {ballStartX++;ballStartY--;}
        if (DownLeft) {ballStartX--;ballStartY++;}
        if (DownRight) {ballStartX++;ballStartY++;}


        // if (ballX < 0) ballX += screenWidth;
        // if (ballY < 0) ballY += screenHeight;

        // This causes a slight bug in the sense that the ball will be past the board's dimensions but proceeds
        // bounce back once it's y coordinates matches.
        // I should check for the dimensions to be on the screen. If they aren't then the game should stop until
        // a new ball is somehow introduced.
        // TODO: Once ball disappears a new ball should be randomly generated to replace it.


        if ((ballX-60) > screenWidth) {
            ballOut = true;
        }
        else if (((ballX + 60) >= humanPaddleTopLeftX) && (ballY >= humanPaddleTopLeftY) && (ballY <= humanPaddleBottomRightY)) {
            if (UpRight) {
                UpLeft = true;
                UpRight = false;
                DownLeft = false;
                DownRight = false;
            }
            if (DownRight) {
                UpLeft = false;
                UpRight = false;
                DownLeft = true;
                DownRight = false;
            }
        }

        else if ((ballX - 60) <= 50) { // hits left side
            if (UpLeft) {
                UpLeft = false;
                UpRight = true;
                DownLeft = false;
                DownRight = false;
            }
            if (DownLeft) {
                UpLeft = false;
                UpRight = false;
                DownLeft = false;
                DownRight = true;
            }
        }
        else if ((ballY + 60) >= screenHeight - 50) { // hits bottom
            if (DownLeft) {
                UpLeft = true;
                UpRight = false;
                DownLeft = false;
                DownRight = false;
            }
            if (DownRight) {
                UpLeft = false;
                UpRight = true;
                DownLeft = false;
                DownRight = false;
            }
        }
        else if ((ballY - 60) <= 50) { // hits top
            if (UpLeft) {
                UpLeft = false;
                UpRight = false;
                DownLeft = true;
                DownRight = false;
            }
            if (UpRight) {
                UpLeft = false;
                UpRight = false;
                DownLeft = false;
                DownRight = true;
            }
        }

        if (ballOut == true) {
            ballOut = false;
            ballStartX = 100;
            ballStartY = 100;
            ballSpd = 20;
            ballDirection = gen.nextInt(3);
            if (ballDirection == 0) {
                UpLeft = true;
                UpRight = false;
                DownLeft = false;
                DownRight = false;
            }
            else if (ballDirection == 1) {
                UpLeft = false;
                UpRight = true;
                DownLeft = false;
                DownRight = false;
            }
            else if (ballDirection == 2) {
                UpLeft = false;
                UpRight = false;
                DownLeft = true;
                DownRight = false;
            }
            else {
                UpLeft = false;
                UpRight = false;
                DownLeft = false;
                DownRight = true;
            }
        }

        Paint whitePaint = new Paint();//paint for three items
        whitePaint.setColor(Color.WHITE);
        //creates walls
        g.drawRect(0f, 0f, screenWidth, 50f, whitePaint); // top
        g.drawRect(0f, 0f, 50f, screenHeight, whitePaint); // right
        g.drawRect(0f, (screenHeight - 50), screenWidth, screenHeight, whitePaint); // bottom
        //Create White Paddle
        humanPaddle = new Paddle(humanPaddleTopLeftX, humanPaddleTopLeftY, humanPaddleBottomRightX, humanPaddleBottomRightY, whitePaint);
        g.drawRect(humanPaddle.paddleBounds, humanPaddle.paddleColor);
        //Creates White Ball
        ball = new Ball(ballX, ballY, radius, whitePaint);
        g.drawCircle(ball.ballX, ball.ballY, ball.ballRadius, ball.ballColor);


    }

    /**
     *
     */
    @Override
    public void onTouch(MotionEvent event) {

    }
}
