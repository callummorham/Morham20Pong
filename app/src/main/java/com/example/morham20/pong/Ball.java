package com.example.morham20.pong;

import android.graphics.Paint;

/**
 * Created by morham20 on 3/20/2018.
 *
 * Used to create a ball for the PongAnimator class
 */

public class Ball {
    Paint ballColor;
    int ballRadius;
    int ballX;
    int ballY;

    Ball(int ballX, int ballY, int ballRadius, Paint ballColor) {
        this.ballX = ballX;
        this.ballY = ballY;
        this.ballRadius = ballRadius;
        this.ballColor = ballColor;
    }
}
