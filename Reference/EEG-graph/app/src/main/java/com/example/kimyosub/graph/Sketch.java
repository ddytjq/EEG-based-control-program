package com.example.kimyosub.graph;

import processing.core.PApplet;

public class Sketch extends PApplet {

    // File i/o

    // BufferedReader reader;
    // String line;
    // String[] pieces;
    // float[] result;

    public Sketch(int w, int h) {
        this.width = w;
        this.height = h;
    }


    boolean recordPDF = false;

    int formResolution = 7;
    int stepSize = 3; // random size

    float distortionFactor = 1;
    float initRadius = 180; //small size
    float centerX, centerY, centerX2, centerY2;
    float[] x = new float[formResolution];
    float[] y = new float[formResolution];

    // set color array
    static int[][][] colorList = {
            {{102, 0, 88}, {138, 36, 124}, {174, 72, 160}, {210, 108, 196}, {246, 144, 232}, {255, 180, 255}}
    };

    // limit line size
    float sizeX_1 = centerX + 120;
    float sizeX_2 = centerX - 120;
    float sizeY_1 = centerY + 150;
    float sizeY_2 = centerY - 150;

    boolean filled = false;
    boolean freeze = false;

    int c = 0;

    double a = 51.4;

    public void setup() {

        /*
        size(520, 520);
        */
        smooth();

        // init form
        centerX = width / 2;
        centerY = height / 2;
        centerX2 = width / 2;
        centerY2 = height / 2 - 135;

        // Get 7 points to draw figure.
        float angle = radians(360 / formResolution);

        // Shape size
        for (int i = 0; i < formResolution; i++) {
            x[i] = cos(angle * i) * initRadius;
            y[i] = sin(angle * i) * initRadius;
        }

        // Draw background lines
        background(25, 25, 25);  // background color = black

        stroke(93, 93, 93);  // lines color = gray

        beginShape();
        fill(25, 25, 25);
        vertex(centerX + (centerX - 150), centerY - (centerX / 4));  // 1
        vertex(centerX + (centerX - 150) * cos(radians((float) a * 1)), centerY + (centerX - 150) * sin(radians((float) a * 1)) - (centerX / 4));  // 2
        vertex(centerX + (centerX - 150) * cos(radians((float) a * 2)), centerY + (centerX - 150) * sin(radians((float) a * 2)) - (centerX / 4));  // 3
        vertex(centerX + (centerX - 150) * cos(radians((float) a * 3)), centerY + (centerX - 150) * sin(radians((float) a * 3)) - (centerX / 4));  // 4
        vertex(centerX + (centerX - 150) * cos(radians((float) a * 4)), centerY + (centerX - 150) * sin(radians((float) a * 4)) - (centerX / 4));  // 5
        vertex(centerX + (centerX - 150) * cos(radians((float) a * 5)), centerY + (centerX - 150) * sin(radians((float) a * 5)) - (centerX / 4));  // 6
        vertex(centerX + (centerX - 150) * cos(radians((float) 51.6)), centerY - (centerX - 150) * sin(radians((float) 51.6)) - (centerX / 4));  // 7
        vertex(centerX + (centerX - 150), centerY - (centerX / 4));  // 1
        endShape();

        beginShape();
        fill(25, 25, 25);
        vertex(centerX + (centerX - 250), centerY - (centerX / 4));  // 1
        vertex(centerX + (centerX - 250) * cos(radians((float) a * 1)), centerY + (centerX - 250) * sin(radians((float) a * 1)) - (centerX / 4));  // 2
        vertex(centerX + (centerX - 250) * cos(radians((float) a * 2)), centerY + (centerX - 250) * sin(radians((float) a * 2)) - (centerX / 4));  // 3
        vertex(centerX + (centerX - 250) * cos(radians((float) a * 3)), centerY + (centerX - 250) * sin(radians((float) a * 3)) - (centerX / 4));  // 4
        vertex(centerX + (centerX - 250) * cos(radians((float) a * 4)), centerY + (centerX - 250) * sin(radians((float) a * 4)) - (centerX / 4));  // 5
        vertex(centerX + (centerX - 250) * cos(radians((float) a * 5)), centerY + (centerX - 250) * sin(radians((float) a * 5)) - (centerX / 4));  // 6
        vertex(centerX + (centerX - 250) * cos(radians((float) 51.6)), centerY - (centerX - 250) * sin(radians((float) 51.6)) - (centerX / 4));  // 7
        vertex(centerX + (centerX - 250), centerY - (centerX / 4));  // 1
        endShape();

        beginShape();
        fill(25, 25, 25);
        vertex(centerX + (centerX - 350), centerY - (centerX / 4));  // 1
        vertex(centerX + (centerX - 350) * cos(radians((float) a * 1)), centerY + (centerX - 350) * sin(radians((float) a * 1)) - (centerX / 4));  // 2
        vertex(centerX + (centerX - 350) * cos(radians((float) a * 2)), centerY + (centerX - 350) * sin(radians((float) a * 2)) - (centerX / 4));  // 3
        vertex(centerX + (centerX - 350) * cos(radians((float) a * 3)), centerY + (centerX - 350) * sin(radians((float) a * 3)) - (centerX / 4));  // 4
        vertex(centerX + (centerX - 350) * cos(radians((float) a * 4)), centerY + (centerX - 350) * sin(radians((float) a * 4)) - (centerX / 4));  // 5
        vertex(centerX + (centerX - 350) * cos(radians((float) a * 5)), centerY + (centerX - 350) * sin(radians((float) a * 5)) - (centerX / 4));  // 6
        vertex(centerX + (centerX - 350) * cos(radians((float) 51.6)), centerY - (centerX - 350) * sin(radians((float) 51.6)) - (centerX / 4));  // 7
        vertex(centerX + (centerX - 350), centerY - (centerX / 4));  // 1
        endShape();

        beginShape();
        fill(25, 25, 25);
        vertex(centerX + (centerX - 450), centerY - (centerX / 4));  // 1
        vertex(centerX + (centerX - 450) * cos(radians((float) a * 1)), centerY + (centerX - 450) * sin(radians((float) a * 1)) - (centerX / 4));  // 2
        vertex(centerX + (centerX - 450) * cos(radians((float) a * 2)), centerY + (centerX - 450) * sin(radians((float) a * 2)) - (centerX / 4));  // 3
        vertex(centerX + (centerX - 450) * cos(radians((float) a * 3)), centerY + (centerX - 450) * sin(radians((float) a * 3)) - (centerX / 4));  // 4
        vertex(centerX + (centerX - 450) * cos(radians((float) a * 4)), centerY + (centerX - 450) * sin(radians((float) a * 4)) - (centerX / 4));  // 5
        vertex(centerX + (centerX - 450) * cos(radians((float) a * 5)), centerY + (centerX - 450) * sin(radians((float) a * 5)) - (centerX / 4));  // 6
        vertex(centerX + (centerX - 450) * cos(radians((float) 51.6)), centerY - (centerX - 450) * sin(radians((float) 51.6)) - (centerX / 4));  // 7
        vertex(centerX + (centerX - 450), centerY - (centerX / 4));  // 1
        endShape();
        // inline
        // 1~6
        for (int i = 0; i < 6; i++) {
            beginShape();
            fill(25, 25, 25);
           vertex(centerX, centerY - (centerX / 4));
            vertex(centerX + (centerX - 150) * cos(radians((float) 51.4 * i)), centerY + (centerX - 150) * sin(radians((float) 51.4 * i)) - (centerX / 4));
            endShape();
        }

        // 7
        beginShape();
        fill(25, 25, 25);
        vertex(centerX, centerY - (centerX / 4));
        vertex(centerX + (centerX - 150) * cos(radians((float) 51.6)), centerY - (centerX - 150) * sin(radians((float) 51.6)) - (centerX / 4));
        endShape();

        // text location
        textSize(40);
        fill(255, 255, 255);
        text("Gamma", centerX + (centerX + 100) * cos(radians((float) a * 5)), centerY + (centerX - 110) * sin(radians((float) a * 5)) - (centerX / 4));
        text("Beta", centerX + (centerX - 150) * cos(radians((float) 51.6)), centerY - (centerX - 100) * sin(radians((float) 51.6)) - (centerX / 4));
        text(" Fest\nAlpha", centerX + (centerX - 130), centerY - (centerX / 4));
        text("Middle\n Alpha", centerX + (centerX - 160) * cos(radians((float) a * 1)), centerY + (centerX - 70) * sin(radians((float) a * 1)) - (centerX / 4));
        text(" Slow\nAlpha", centerX + (centerX + 50) * cos(radians((float) a * 2)), centerY + (centerX - 80) * sin(radians((float) a * 2)) - (centerX / 4));
        text("Theta", centerX + (centerX - 30) * cos(radians((float) a * 3)), centerY + (centerX - 50) * sin(radians((float) a * 3)) - (centerX / 4));
        text("Delta", centerX + (centerX - 20) * cos(radians((float) a * 4)), centerY + (centerX - 100) * sin(radians((float) a * 4)) - (centerX / 4));

        drawShape();
    }

    //draw
    public void draw() {

        // calculate new points
        for (int i = 0; i < formResolution; i++) {
            x[i] += random(-stepSize, stepSize);
            y[i] += random(-stepSize, stepSize);
        }

        strokeWeight(30); //line size

        // Check the value of c
        // If c==6 is not exist at color[][][].
        // (end of array is color[0][5][5]) So reset int c(try back to first color)
        if (c == 6) {
            c = 0;  // reset value of c
            drawShape();
        } else {
            drawShape();
        }
        //  It slows down the time that the next figure is drawn.
        delay(1000);
    }

    // draw function
    public void drawShape() {

        if (filled) fill(random(255));
        else noFill();

        stroke(colorList[0][c][0], colorList[0][c][1], colorList[0][c][2]);

        strokeWeight(1);

        beginShape();
        // start control point
        // middle alpha - best alpha
        curveVertex(x[formResolution - 1] + centerX2, y[formResolution - 1] + centerY2);

        // only these points are drawn
        for (int i = 0; i < 7; i++) {
            curveVertex(x[i] + centerX2, y[i] + centerY2);
            ellipse(x[i] + centerX2, y[i] + centerY2, 5, 5);
        }

        // end controlpoint1
        curveVertex(x[0] + centerX2, y[0] + centerY2);
        curveVertex(x[1] + centerX2, y[1] + centerY2);
        endShape();

        c++;
    }
}

