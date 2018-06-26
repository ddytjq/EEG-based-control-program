import processing.pdf.*;
import java.util.Calendar;

boolean recordPDF = false;

int formResolution = 7;
int stepSize = 2; // random size

float distortionFactor = 1;
float initRadius = 130;
float centerX, centerY;
float[] x = new float[formResolution];
float[] y = new float[formResolution];
// set color array
static int colorList[][][]={
  {{102,0,88},{138,36,124},{174,72,160},{210,108,196},{246,144,232},{255,180,255}}
};

boolean filled = false;
boolean freeze = false;


void setup() {
  size(500, 500);
  smooth();

  // init form
  centerX = width/2; 
  centerY = height/2;
  
  // Get 7 points to draw figure.
  float angle = radians(360/float(formResolution));
  for (int i=0; i<formResolution; i++) {
    x[i] = cos(angle*i) * initRadius;
    y[i] = sin(angle*i) * initRadius;
  }


  // Draw background lines
  background(25,25,25);  // background color = black
  
  stroke(93,93,93);  // lines color = gray
  
  beginShape();
  fill(25,25,25);
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

int c=0;
void draw(){
  
  // calculate new points
  for (int i=0; i<formResolution; i++){
    x[i] += random(-stepSize,stepSize);
    y[i] += random(-stepSize,stepSize);
  }
  
  strokeWeight(1);
  
  // Check the value of c
  // If c==6 is not exist at color[][][].(end of array is color[0][5][5]) So reset int c(try back to first color)
  if(c==6){
    c=0;
    if (filled) fill(random(255));
    else noFill();

    stroke(colorList[0][c][0],colorList[0][c][1],colorList[0][c][2]);
    beginShape();
  
    // start controlpoint
    curveVertex(x[formResolution-1]+centerX, y[formResolution-1]+centerY);

    // only these points are drawn
    for (int i=0; i<formResolution; i++){
      curveVertex(x[i]+centerX, y[i]+centerY);
    }
    curveVertex(x[0]+centerX, y[0]+centerY);
  
    // end controlpoint
    curveVertex(x[1]+centerX, y[1]+centerY);
    endShape();
    c++;
  }
  // get color from color array
  else{
    if (filled) fill(random(255));
    else noFill();

    stroke(colorList[0][c][0],colorList[0][c][1],colorList[0][c][2]);
    beginShape();
  
    // start controlpoint
    curveVertex(x[formResolution-1]+centerX, y[formResolution-1]+centerY);

    // only these points are drawn
    for (int i=0; i<formResolution; i++){
      curveVertex(x[i]+centerX, y[i]+centerY);
    }
    curveVertex(x[0]+centerX, y[0]+centerY);
  
    // end controlpoint
    curveVertex(x[1]+centerX, y[1]+centerY);
    endShape();
    c++;
  }
  
  //  It slows down the time that the next figure is drawn.
  delay(200);
 
}
