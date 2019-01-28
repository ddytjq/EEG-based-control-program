import processing.pdf.*;
import java.util.Calendar;

// File i/o
BufferedReader reader; 
String line;
String[] pieces;
float[] result;


boolean recordPDF = false;

int formResolution = 7;
int stepSize = 2; // random size

float distortionFactor = 1;
float initRadius = 80; //small size
float initRadius2 = 110; // big size
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
  float angle = radians(360/formResolution);

  // default Shape size
  for (int i=0; i<formResolution; i++) {
    x[i] = cos(angle*i) * initRadius;
    y[i] = sin(angle*i) * initRadius;
  }

  //File I/O, file read
  reader = createReader("/Users/kimyosub/Documents/Processing/File_io/test.txt");

  // Draw background lines
  background(25, 25, 25);  // background color = black

  stroke(93, 93, 93);  // lines color = gray

  // out line
  beginShape();
  fill(25, 25, 25);
  vertex(centerX+180, centerY);  // 1
  vertex(centerX+(180*cos(radians(51.4))), centerY+(180*sin(radians(51.4))));  // 2
  vertex(centerX+(180*cos(radians(51.4*2))), centerY+(180*sin(radians(51.4*2))));  // 3
  vertex(centerX+(180*cos(radians(51.4*3))), centerY+(180*sin(radians(51.4*3))));  // 4
  vertex(centerX+(180*cos(radians(51.4*4))), centerY+(180*sin(radians(51.4*4))));  // 5
  vertex(centerX+(180*cos(radians(51.4*5))), centerY+(180*sin(radians(51.4*5))));  // 6
  vertex(centerX+(180*cos(radians(51.6))), centerY-(180*sin(radians(51.6))));  // 7
  vertex(centerX+180, centerY);  // 1
  endShape();

  beginShape();
  fill(25, 25, 25);
  vertex(centerX+180-45, centerY);  // 1
  vertex(centerX+(180*cos(radians(51.4))-28), centerY+(180*sin(radians(51.4)))-35);  // 2
  vertex(centerX+(180*cos(radians(51.4*2))+10), centerY+(180*sin(radians(51.4*2)))-45);  // 3
  vertex(centerX+(180*cos(radians(51.4*3))+43), centerY+(180*sin(radians(51.4*3)))-20);  // 4
  vertex(centerX+(180*cos(radians(51.4*4))+43), centerY+(180*sin(radians(51.4*4)))+20);  // 5
  vertex(centerX+(180*cos(radians(51.4*5))+11), centerY+(180*sin(radians(51.4*5)))+45);  // 6
  vertex(centerX+(180*cos(radians(51.6))-28), centerY-(180*sin(radians(51.6)))+35);  // 7
  vertex(centerX+180-45, centerY);  // 1
  endShape();

  beginShape();
  fill(25, 25, 25);
  vertex(centerX+180-90, centerY);  // 1
  vertex(centerX+(180*cos(radians(51.4))-55), centerY+(180*sin(radians(51.4)))-68);  // 2
  vertex(centerX+(180*cos(radians(51.4*2))+20), centerY+(180*sin(radians(51.4*2)))-85);  // 3
  vertex(centerX+(180*cos(radians(51.4*3))+80), centerY+(180*sin(radians(51.4*3)))-38);  // 4
  vertex(centerX+(180*cos(radians(51.4*4))+81), centerY+(180*sin(radians(51.4*4)))+39);  // 5
  vertex(centerX+(180*cos(radians(51.4*5))+20), centerY+(180*sin(radians(51.4*5)))+85);  // 6
  vertex(centerX+(180*cos(radians(51.6))-55), centerY-(180*sin(radians(51.6)))+70);  // 7
  vertex(centerX+180-90, centerY);  // 1
  endShape();

  beginShape();
  fill(25, 25, 25);
  vertex(centerX+180-135, centerY);  // 1
  vertex(centerX+(180*cos(radians(51.4))-83), centerY+(180*sin(radians(51.4)))-105);  // 2
  vertex(centerX+(180*cos(radians(51.4*2))+29), centerY+(180*sin(radians(51.4*2)))-130);  // 3
  vertex(centerX+(180*cos(radians(51.4*3))+119), centerY+(180*sin(radians(51.4*3)))-57);  // 4
  vertex(centerX+(180*cos(radians(51.4*4))+120), centerY+(180*sin(radians(51.4*4)))+58);  // 5
  vertex(centerX+(180*cos(radians(51.4*5))+30), centerY+(180*sin(radians(51.4*5)))+130);  // 6
  vertex(centerX+(180*cos(radians(51.6))-83), centerY-(180*sin(radians(51.6)))+105);  // 7
  vertex(centerX+180-135, centerY);  // 1
  endShape();

  // 1~6
  for (int i=0; i < 6; i++) {
    beginShape();
    fill(25, 25, 25);
    vertex(centerX+180*cos(radians(51.4*i)), centerY+(180*sin(radians(51.4*i))));
    vertex(centerX+180*cos(radians(51.4*i)), centerY+(180*sin(radians(51.4*i))));
    vertex(centerX, centerY);
    vertex(centerX, centerY);
    endShape();
  }

  // 7
  beginShape();
  fill(25, 25, 25);
  vertex(centerX+(180*cos(radians(51.6))), centerY-(180*sin(radians(51.6))));
  vertex(centerX+(180*cos(radians(51.6))), centerY-(180*sin(radians(51.6))));
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();  // 1

  textSize(12);
  fill(255, 255, 255);
  text("Gamma", centerX-65, centerY-185);
  text("Beta", centerX+117, centerY-147);
  text(" Fest\nAlpha", centerX+186, centerY-8);
  text("Middle\n Alpha", centerX+110, centerY+160);
  text(" Slow\nAlpha", centerX-55, centerY+193);
  text("Theta", centerX-202, centerY+89);
  text("Delta", centerX-202, centerY-77);
}

//draw
void draw() {

  // File I/O
  try {
    line= reader.readLine(); // file line read
  }
  catch(IOException e) { //exception
    e.printStackTrace();
    line = null;
  }
  
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
  delay(300);
}

// draw function
void drawShape() {

  if (filled) fill(random(255));
  else noFill();

  stroke(colorList[0][c][0], colorList[0][c][1], colorList[0][c][2]);
  beginShape();

  // start controlpoint
  // middle alpha - best alpha
  curveVertex(x[formResolution-1]+centerX, y[formResolution-1]+centerY);

  // only these points are drawn
  for (int i=0; i<formResolution; i++) {
    curveVertex(x[i]+centerX, y[i]+centerY);
    ellipse(x[i]+centerX, y[i]+centerY, 5, 5);
  }

  // end controlpoint
  curveVertex(x[0]+centerX, y[0]+centerY);
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
