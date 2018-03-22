package com.example.morham20.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by CallumMorham on 3/18/18.
 * The aniimator for the pong game, this also has all the logic inside it
 * calls two other classes "ball and paddle" just to create the image
 *
 * bounces the ball around in the box and makes it reflect in a different direction with
 * nuetonian physics
 *
 * flashes color each time it hits a wall
 *
 * ball starts in random location with random speed each time.
 * speed is higher than usuall for simplistic testing
 *
 */

public class PongAnimator implements Animator {

    //random generator
    private Random gen = new Random();

    //for ball direction and if it's touching a wall or out of play
    private boolean UpLeft = true;
    private boolean UpRight = false;
    private boolean DownLeft = false;
    private boolean DownRight = false;
    private boolean ballWall = false;
    private boolean ballOut = false;

    //all Int's
    private int ballStartX = 100;
    private int ballStartY = 100;
    private int ballDirection;
    private int radius = 40;
    private int ballSpd = gen.nextInt(30) + 50;
    int ballColorGen = 0;

    //creates paddle and ball
    private Paddle humanPaddle;
    private Ball ball;

    /*
     * Interval between animation frames: .01 seconds
     * @return the time interval between frames, in milliseconds.
     */
    @Override
    public int interval() {
        return 10;
    }
    /**
     * The background color: Black.
     * @return the background color onto which we will draw the image.
     */
    @Override
    public int backgroundColor() {
        return Color.rgb(0, 0, 0);
    }
    /**
     * Tells that we never pause.
     * @return indication of whether to pause
     */
    @Override
    public boolean doPause() {
        return false;
    }
    /**
     * Tells that we never stop the animation.
     * @return indication of whether to quit.
     */
    @Override
    public boolean doQuit() {
        return false;
    }
    /**
     * Action to perform on clock tick
     * @param g the graphics object on which to draw
     */
    @Override
    public void tick(Canvas g) {

        int screenHeight = g.getHeight();
        int screenWidth = g.getWidth();

        //Makes points for the player Paddle
        int humanPaddleTopLeftX = screenWidth - 50;
        int humanPaddleTopLeftY = (screenHeight / 2) - 200;
        int humanPaddleBottomRightX = screenWidth - 10;
        int humanPaddleBottomRightY = (screenHeight / 2) + 200;

        //determines the ball's direction and how it moves
        if (UpLeft) {ballStartX--;ballStartY--;}
        if (UpRight) {ballStartX++;ballStartY--;}
        if (DownLeft) {ballStartX--;ballStartY++;}
        if (DownRight) {ballStartX++;ballStartY++;}

        /**
         External Citation
         Date: 21 March 2018
         Problem: Ball positions weren't working properly

         Resource: ClassMate Anthony Lieu

         Solution: Create a new constant variable and multiply it by speed
         */
        int ballX = (ballStartX * ballSpd);
        int ballY = (ballStartY * ballSpd);

        //checks directionality then sets the direction in motion to true
        //also sets ballWall to true if it touches a wall
        if ((ballX - 60) > screenWidth) {
            ballOut = true;
        }
        else if (((ballX + 60) >= humanPaddleTopLeftX) && (ballY >= humanPaddleTopLeftY) && (ballY <= humanPaddleBottomRightY)) {
            if (UpRight) {//Hit's paddle
                UpRight = false;
                DownLeft = false;
                DownRight = false;
                UpLeft = true;
            }
            if (DownRight) {
                UpLeft = false;
                UpRight = false;
                DownRight = false;
                DownLeft = true;
            }
        }
        else if ((ballY - 60) <= 50) { // hits top
            if (UpLeft) {
                UpLeft = false;
                UpRight = false;
                DownRight = false;
                DownLeft = true;
                ballWall = true;
            }
            if (UpRight) {
                UpLeft = false;
                UpRight = false;
                DownLeft = false;
                DownRight = true;
                ballWall = true;
            }
        }
            else if ((ballX - 60) <= 50) { // hits left side
                if (UpLeft) {
                    UpLeft = false;
                    DownLeft = false;
                    DownRight = false;
                    UpRight = true;
                    ballWall = true;
                }
                if (DownLeft) {
                    UpLeft = false;
                    UpRight = false;
                    DownLeft = false;
                    DownRight = true;
                    ballWall = true;
                }
            } else if ((ballY + 60) >= screenHeight - 50) { // hits bottom
                if (DownLeft) {
                    UpRight = false;
                    DownLeft = false;
                    DownRight = false;
                    UpLeft = true;
                    ballWall = true;
                }
                if (DownRight) {
                    UpLeft = false;
                    DownLeft = false;
                    DownRight = false;
                    UpRight = true;
                    ballWall = true;
                }
            }


        if (ballOut == true) {
            ballOut = false;//set to false so method doesnt instantly reacll next tick

            /*
             External Citation
             Date: 21 March 2018
             Problem: Random ball position wont work

             Resource: ClassMate Anthony Lieu

             Solution: set it to random from 0-100 which would break on screens of
             different sizes but fixes for tablets of pixels like nexus9 and PixelC
             */
            ballStartX = gen.nextInt(30);//random starting location of the ball
            ballStartY = gen.nextInt(30);//gen.nextInt(screenHeight);//TODO Should work? figure out why it fails
            ballSpd = gen.nextInt(30) + 50;//randomly sets speed
            ballX = ballStartX;//recalculates location
            ballY = ballStartY;

            ballDirection = gen.nextInt(3);//randomly chooses a direction
            UpLeft = false;//sets all directions to false
            UpRight = false;
            DownLeft = false;
            DownRight = false;

            if (ballDirection == 0) {UpRight = true;}//sets the correct direction to true appropriatly
            else if (ballDirection == 1) {DownLeft = true;}
            else if (ballDirection == 2) {UpLeft = true;}
            else {DownRight = true;}
        }

        Paint whitePaint = new Paint();//paint for three items
        whitePaint.setColor(Color.WHITE);

        /*
         External Citation
         Date: 21 March 2018
         Problem: how to change colors between ticks

         Resource: ClassMate Chris Sebrects

         Solution: Randomly craete statements to change the definition of white
         paint even though it wont be white
         */
        if (ballWall == true)//flashes all drawn pieces each time it hits a wall
        {
            ballColorGen = gen.nextInt(5);
            if (ballColorGen == 1) {whitePaint.setColor(Color.MAGENTA);}
            else if (ballColorGen == 2) {whitePaint.setColor(Color.GREEN);}
            else if (ballColorGen == 3) {whitePaint.setColor(Color.RED);}
            else if (ballColorGen == 0) {whitePaint.setColor(Color.BLUE);}
            else if (ballColorGen == 4) {whitePaint.setColor(Color.YELLOW);}
            else {whitePaint.setColor(Color.CYAN);}
        }
        ballWall = false;//sets ballwall to false so it doesnt change every tick

        //Creates White Ball
        ball = new Ball(ballX, ballY, radius, whitePaint);
        g.drawCircle(ball.ballX, ball.ballY, ball.ballRadius, ball.ballColor);
        //creates walls
        g.drawRect(0f, 0f, screenWidth, 50f, whitePaint); // top
        g.drawRect(0f, 0f, 50f, screenHeight, whitePaint); // side
        g.drawRect(0f, (screenHeight - 50), screenWidth, screenHeight, whitePaint); // bottom
        //Create White Paddle
        humanPaddle = new Paddle(humanPaddleTopLeftX, humanPaddleTopLeftY, humanPaddleBottomRightX, humanPaddleBottomRightY, whitePaint);
        g.drawRect(humanPaddle.paddleBounds, humanPaddle.paddleColor);
    }
    @Override
    public void onTouch(MotionEvent event) {}
}
