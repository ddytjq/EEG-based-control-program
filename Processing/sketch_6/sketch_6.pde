import processing.pdf.*;
import java.util.Calendar;

boolean recordPDF = false;

int formResolution = 7;
int stepSize = 2; // random size

float distortionFactor = 1;
float initRadius = 110;
float centerX, centerY;
float[] x = new float[formResolution];
float[] y = new float[formResolution];
// set color array
static int[][][] colorList={
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

void setup() {
  size(520, 520);
  smooth();

  // init form
  centerX = width/2; 
  centerY = height/2;

  // Get 7 points to draw figure.
  float angle = radians(360/float(formResolution));

  // Shape size
  for (int i=0; i<formResolution; i++) {
    x[i] = cos(angle*i) * initRadius;
    y[i] = sin(angle*i) * initRadius;
  }

  // Draw background lines
  background(25, 25, 25);  // background color = black

  stroke(93, 93, 93);  // lines color = gray

  //out line
  beginShape();
  fill(25, 25, 25);
  vertex(centerX, centerY-190);  // 1
  vertex(centerX-130, centerY-125);  // 2
  vertex(centerX-180, centerY);  // 3
  vertex(centerX-95, centerY+150);  // 4
  vertex(centerX+95, centerY+150);  // 5
  vertex(centerX+180, centerY);  // 6
  vertex(centerX+130, centerY-125);  // 7
  vertex(centerX, centerY-190);  // 1
  endShape();

  beginShape();
  fill(25, 25, 25);
  vertex(centerX, centerY-155);  // 1
  vertex(centerX-105, centerY-100);  // 2
  vertex(centerX-145, centerY);  // 3
  vertex(centerX-75, centerY+120);  // 4
  vertex(centerX+75, centerY+120);  // 5
  vertex(centerX+145, centerY);  // 6
  vertex(centerX+105, centerY-100);  // 7
  vertex(centerX, centerY-155);  // 1
  endShape(); 

  beginShape();
  fill(25, 25, 25);
  vertex(centerX, centerY-120);  // 1
  vertex(centerX-79, centerY-75);  // 2
  vertex(centerX-110, centerY);  // 3
  vertex(centerX-56, centerY+90);  // 4
  vertex(centerX+56, centerY+90);  // 5
  vertex(centerX+110, centerY);  // 6
  vertex(centerX+79, centerY-75);  // 7
  vertex(centerX, centerY-120);  // 1
  endShape();

  beginShape();
  fill(25, 25, 25);
  vertex(centerX, centerY-85);  // 1
  vertex(centerX-50, centerY-50);  // 2
  vertex(centerX-72, centerY);  // 3
  vertex(centerX-37, centerY+60);  // 4
  vertex(centerX+37, centerY+60);  // 5
  vertex(centerX+72, centerY);  // 6
  vertex(centerX+50, centerY-50);  // 7
  vertex(centerX, centerY-85);  // 1
  endShape(); 

  //in line
  beginShape();
  fill(25, 25, 25);
  vertex(centerX, centerY-50);  // 1
  vertex(centerX-30, centerY-30);  // 2
  vertex(centerX-40, centerY);  // 3
  vertex(centerX-20, centerY+35);  // 4
  vertex(centerX+20, centerY+35);  // 5
  vertex(centerX+40, centerY);  // 6
  vertex(centerX+30, centerY-30);  // 7
  vertex(centerX, centerY-50);  // 1
  endShape(); 

  // 1
  beginShape();
  vertex(centerX, centerY-190);
  vertex(centerX, centerY-190);
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();

  // 2
  beginShape();
  vertex(centerX-130, centerY-125);
  vertex(centerX-130, centerY-125);
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();

  // 3
  beginShape();
  vertex(centerX-180, centerY);
  vertex(centerX-180, centerY);
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();

  // 4
  beginShape();
  vertex(centerX-95, centerY+150);
  vertex(centerX-95, centerY+150);
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();

  // 5
  beginShape();
  vertex(centerX+95, centerY+150);
  vertex(centerX+95, centerY+150);
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();

  // 6
  beginShape();
  vertex(centerX+180, centerY);
  vertex(centerX+180, centerY);
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();

  // 7
  beginShape();
  vertex(centerX+130, centerY-125);
  vertex(centerX+130, centerY-125);
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();

  textSize(12);
  fill(255, 255, 255);
  text("Gamma", centerX-23, centerY-208);
  text("Beta", centerX+150, centerY-128);
  text(" Best\nAlpha", centerX+200, centerY-8);
  text("Middle\n Alpha", centerX+90, centerY+180);
  text(" Slow\nAlpha", centerX-130, centerY+180);
  text("Theta", centerX-240, centerY+3);
  text("Delta", centerX-190, centerY-128);
}

//draw
void draw() {

  // calculate new points
  for (int i=0; i<formResolution; i++) {
    x[i] += random(-stepSize, stepSize);
    y[i] += random(-stepSize, stepSize);
  }

  strokeWeight(1); //line size

  // Check the value of c
  // If c==6 is not exist at color[][][].
  // (end of array is color[0][5][5]) So reset int c(try back to first color)
  if (c==6) {
    c=0;  // reset value of c
    drawShape();
  } else {
    drawShape();
  }
  //  It slows down the time that the next figure is drawn.
  delay(200);
}

// draw function
void drawShape() {
  if (filled) fill(random(255));
  else noFill();

  stroke(colorList[0][c][0], colorList[0][c][1], colorList[0][c][2]);
  beginShape();

  // start controlpoint
  curveVertex(x[formResolution-1]+centerX, y[formResolution-1]+centerY);

  // only these points are drawn
  for (int i=0; i<formResolution; i++) {
    curveVertex(x[i]+centerX, y[i]+centerY);
  }
  curveVertex(x[0]+centerX, y[0]+centerY);

  // end controlpoint
  curveVertex(x[1]+centerX, y[1]+centerY);
  endShape();

  // limit line 
  if ( x[1]+centerX < sizeX_1 ||x[2]+centerX < sizeX_1 ||x[3]+centerX < sizeX_1 
    ||x[4]+centerX < sizeX_1 || x[5]+centerX < sizeX_1 ||x[6]+centerX < sizeX_1 
    ||x[1]+centerX > sizeX_2 ||x[2]+centerX > sizeX_2 ||x[3]+centerX > sizeX_2 
    ||x[4]+centerX > sizeX_2 ||x[5]+centerX > sizeX_2 ||x[6]+centerX > sizeX_2 
    || y[1]+centerY < sizeY_1 || y[2]+centerY < sizeY_1 || y[3]+centerY < sizeY_1 
    || y[4]+centerY < sizeY_1 || y[5]+centerY < sizeY_1 || y[6]+centerY < sizeY_1
    || y[1]+centerY > sizeY_2 || y[2]+centerY > sizeY_2 || y[3]+centerY > sizeY_2 
    || y[4]+centerY > sizeY_2 || y[5]+centerY > sizeY_2 || y[6]+centerY > sizeY_2 )
  { 
    x[1] = x[1] - 0.05;
    y[1] = y[1] - 0.05;
    x[2] = x[2] - 0.05;
    y[2] = y[2] - 0.05;
    x[3] = x[3] + 0.05;
    y[3] = y[3] - 0.05;
    x[4] = x[4] + 0.05;
    y[4] = y[4] - 0.05;
    x[5] = x[5] - 0.05;
    y[5] = y[5] + 0.05;
    x[6] = x[6] - 0.05;
    y[6] = y[6] + 0.05;
  }
  c++;
}
