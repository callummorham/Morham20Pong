package com.example.morham20.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * class that animates a ball repeatedly moving diagonally on
 * simple background
 *
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version February 2016
 */
public class TestAnimator implements Animator {


    // instance variables
    private int count = 0; // counts the number of logical clock ticks
    private boolean goBackwards = false; // whether clock is ticking backwards

    // Mine
    // TODO: Random Ball Starting position and direction, but must start in middle like actual game
    // TODO: Random Ball Start speed and decaying ball speed
    private int countX = 50; // starting X position of Ball
    private int countY = 50; // starting Y position of Ball

    private boolean NW = false;
    private boolean NE = false;
    private boolean SW = false;
    private boolean SE = true;

    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    public int interval() {
        return 30;
    }

    /**
     * The background color: a light blue.
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor() {
        // create/return the background color
        return Color.rgb(180, 200, 255);
    }

    /**
     * Tells the animation whether to go backwards.
     *
     * @param b true iff animation is to go backwards.
     */
    public void goBackwards(boolean b) {
        // set our instance variable
        goBackwards = b;
    }

    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {
        // bump our count either up or down by one, depending on whether
        // we are in "backwards mode".

        //////////////// NEED TO MOVE OVER TO NEW ANIMATOR CLASS THAT I MAKE /////////////////////////////



        int boardHeight = g.getHeight();
        int boardWidth = g.getWidth();
        int midHeight = g.getHeight()/2;
        int midWidth = g.getWidth()/2;


        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        // Three white "walls" of the game.
        g.drawRect(0f, 0f, boardWidth, 30f, whitePaint); // top
        g.drawRect(0f, (boardHeight - 30), boardWidth, boardHeight, whitePaint); // bottom
        g.drawRect(0f, 0f, 30f, boardHeight, whitePaint); // right


		/*
		 * Cite This
		 * https://stackoverflow.com/questions/16528572/draw-dash-line-on-a-canvas
		 */
        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStrokeWidth(10f);
        // Middle segmented yellow line to divide the board in half.
        yellowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        yellowPaint.setPathEffect(new DashPathEffect(new float[] {50,10}, 0));
        g.drawLine(midWidth, 0f, midWidth, boardHeight, yellowPaint);



        // Blue paddle on the left side
        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        g.drawRect((boardWidth - 60), (midHeight - 150), (boardWidth - 10), (midHeight + 150), bluePaint);


        ////////////////////////////////////////////////////////////////////////////////////////////

/*
		if (goBackwards) {
			count--;
		}
		else {
			count++;
		}
*/
        ////////////// Ball hits walls////////////////
        if (goBackwards) {
            if (NW) {
                countX++;
                countY++;
            }
            if (NE) {
                countX--;
                countY++;
            }
            if (SW) {
                countX++;
                countY--;
            }
            if (SE) {
                countX--;
                countY--;
            }
        }
        else {
            if (NW) {
                countX--;
                countY--;
            }
            if (NE) {
                countX++;
                countY--;
            }
            if (SW) {
                countX--;
                countY++;
            }
            if (SE) {
                countX++;
                countY++;
            }
        }



        int ballX = (countX*15)%boardWidth;
        int ballY = (countY*15)%boardHeight;


        if (ballX < 0) ballX += boardWidth;
        if (ballY < 0) ballY += boardHeight;
        // Ball bounces around borders for now
        // TODO: Need to limit the right side wall to only the paddle's position
        // TODO: If paddle's position can be set with a variable then it would be easier when animating paddle
        // TODO: Ball should fly offscreen when misses paddle
        if ((ballX + 60) > boardWidth - 60) { // hits right side
            if (NE) {
                NW = true;
                NE = false;
                SW = false;
                SE = false;
            }
            if (SE) {
                NW = false;
                NE = false;
                SW = true;
                SE = false;
            }
        }
        if ((ballX - 60) < 30) { // hits left side
            if (NW) {
                NW = false;
                NE = true;
                SW = false;
                SE = false;
            }
            if (SW) {
                NW = false;
                NE = false;
                SW = false;
                SE = true;
            }
        }
        if ((ballY + 60) > boardHeight - 30) { // hits bottom
            if (SW) {
                NW = true;
                NE = false;
                SW = false;
                SE = false;
            }
            if (SE) {
                NW = false;
                NE = true;
                SW = false;
                SE = false;
            }
        }
        if ((ballY - 60) < 30) { // hits top
            if (NW) {
                NW = false;
                NE = false;
                SW = true;
                SE = false;
            }
            if (NE) {
                NW = false;
                NE = false;
                SW = false;
                SE = true;
            }
        }


        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        g.drawCircle(ballX, ballY, 60, greenPaint);


        //////////////////////////////////////////////



		/*
		// Determine the pixel position of our ball.  Multiplying by 15
		// has the effect of moving 15 pixel per frame.  Modding by 600
		// (with the appropriate correction if the value was negative)
		// has the effect of "wrapping around" when we get to either end
		// (since our canvas size is 600 in each dimension).
		int num = (count*15)%900;
		if (num < 0) num += 900;

		// Draw the ball in the correct position.
		Paint redPaint = new Paint();
		redPaint.setColor(Color.RED);
		g.drawCircle(num, num, 60, redPaint);
		redPaint.setColor(0xff0000ff);
		*/
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * reverse the ball's direction when the screen is tapped
     */
    public void onTouch(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            goBackwards = !goBackwards;
        }
    }



}//class TextAnimator
