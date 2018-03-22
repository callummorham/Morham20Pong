package com.example.morham20.pong;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by morham20 on 3/20/2018.
 *
 * Used to create a paddle for the PongAnimator Class
 */

public class Paddle {

    Paint paddleColor;
    int paddleTopLeftX;
    int paddleTopLeftY;
    int paddleBottomRightX;
    int paddleBottomRightY;
    Rect paddleBounds;

    Paddle(int paddleTopLeftX, int paddleTopLeftY, int paddleBottomRightX, int paddleBottomRightY, Paint paddleColor) {

        this.paddleColor = paddleColor;
        this.paddleTopLeftX = paddleTopLeftX;
        this.paddleTopLeftY = paddleTopLeftY;
        this.paddleBottomRightX = paddleBottomRightX;
        this.paddleBottomRightY = paddleBottomRightY;
        this.paddleBounds = new Rect(paddleTopLeftX, paddleTopLeftY, paddleBottomRightX, paddleBottomRightY);

    }
}
