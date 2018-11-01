import processing.pdf.*;
import java.util.Calendar;

boolean recordPDF = false;

int formResolution = 7;
int stepSize = 2; // random size

float distortionFactor = 1;
float initRadius = 100;
float centerX, centerY;
float[] x = new float[formResolution];
float[] y = new float[formResolution];

boolean filled = false;
boolean freeze = false;


void setup() {
  size(500, 500);
  smooth();

  // init form
  centerX = width/2; 
  centerY = height/2;
  
  float angle = radians(360/float(formResolution));
  for (int i=0; i<formResolution; i++) {
    x[i] = cos(angle*i) * initRadius;
    y[i] = sin(angle*i) * initRadius;
  }

  stroke(255,94,0);
  background(255);
}


void draw(){
  // calculate new points
  /*
  for (int i=0; i<formResolution; i++){
    x[i] += random(-stepSize,stepSize);
    y[i] += random(-stepSize,stepSize);
    //ellipse(x[i], y[i], 50, 50);
  }
  */

  strokeWeight(0.1); //
  if (filled) fill(random(255));
  else noFill();

  beginShape();
  
  //start controlpoint
  curveVertex(x[formResolution-1]+centerX, y[formResolution-1]+centerY);
  curveVertex(x[formResolution-1]+centerX, y[formResolution-1]+centerY);
  ellipse(x[formResolution-1]+centerX, y[formResolution-1]+centerY,5,5);

  // only these points are drawn
  for (int i=0; i<formResolution; i++){
    curveVertex(x[i]+centerX, y[i]+centerY);
    ellipse(x[i]+centerX, y[i]+centerY,5,5);
  }
  curveVertex(x[0]+centerX, y[0]+centerY);
  // end controlpoint
  endShape();
  
  beginShape();
  vertex(250,65);
  vertex(250, 65);
  vertex(92, 123);
  vertex(62, 273);
  vertex(155, 403);
  vertex(337, 403);
  vertex(430, 273);
  vertex(385, 123);
  vertex(250, 65);
  vertex(250, 65);
  endShape();
  
  beginShape();
  vertex(250, 123);
  vertex(145, 161);
  vertex(125, 273);
  vertex(194, 358);
  vertex(312, 358);
  vertex(369, 273);
  vertex(337, 161);
  vertex(250, 123);  
  endShape();  
  
  beginShape();
  vertex(250, 169);
  vertex(187, 196);
  vertex(177, 273);
  vertex(219, 323);
  vertex(290, 323);
  vertex(323, 273);
  vertex(305, 196);
  vertex(250, 169);
  endShape();
  
  beginShape();
  vertex(250, 203);
  vertex(220, 222);
  vertex(212, 260);
  vertex(235, 296);
  vertex(273, 296);
  vertex(292, 260);
  vertex(281, 222);
  vertex(250, 203);
  endShape(); 
}
