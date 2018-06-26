import processing.pdf.*;

// File i/o
BufferedReader reader_alpha;
BufferedReader reader_beta;
BufferedReader reader_delta;
BufferedReader reader_gamma;
BufferedReader reader_theta;

ArrayList<String> result_alpha = new ArrayList<String>(); // real-time alpha data
ArrayList<String> result_beta = new ArrayList<String>(); // real-time beta data
ArrayList<String> result_delta = new ArrayList<String>(); // real-time delta data
ArrayList<String> result_gamma = new ArrayList<String>(); // real-time gamma data
ArrayList<String> result_theta = new ArrayList<String>(); // real-time theta data
String line;
String[] pieces;
int j = 0;

int formResolution = 7; // 7 location
float stepSize = 2.5; // random size

float distortionFactor = 1;

// graph size
float initRadius1 = 60;
float initRadius2 = 75;
float initRadius3 = 90;
float initRadius4 = 105;
float initRadius5 = 130;

float centerX, centerY;

// graph position
float[] a1 = new float[formResolution]; //
float[] a2 = new float[formResolution]; //
float[] b1 = new float[formResolution]; //
float[] b2 = new float[formResolution]; //
float[] c1 = new float[formResolution]; //
float[] c2 = new float[formResolution]; //
float[] d1 = new float[formResolution]; //
float[] d2 = new float[formResolution]; //
float[] e1 = new float[formResolution]; //
float[] e2 = new float[formResolution]; //

// set color array
static int[][][] colorList={
  {{102, 0, 88}, {138, 36, 124}, {174, 72, 160}, {210, 108, 196}, {246, 144, 232}, {255, 180, 255}}
};

boolean filled = false;
boolean freeze = false;

int c = 0; // color count

void setup() {
  size(520, 520);
  smooth();

  // File i/o
  reader_alpha = createReader("/Users/kimyosub/Documents/Processing/File_io/alpha.txt");
  reader_beta = createReader("/Users/kimyosub/Documents/Processing/File_io/beta.txt");
  reader_delta = createReader("/Users/kimyosub/Documents/Processing/File_io/delta.txt");
  reader_gamma = createReader("/Users/kimyosub/Documents/Processing/File_io/gamma.txt");
  reader_theta = createReader("/Users/kimyosub/Documents/Processing/File_io/theta.txt");

  try {
    while ((line=reader_alpha.readLine())!= null) {
      result_alpha.add(line);
    }
    reader_alpha.close();

    while ((line=reader_beta.readLine())!= null) {
      result_beta.add(line);
    }
    reader_beta.close();

    while ((line=reader_delta.readLine())!= null) {
      result_delta.add(line);
    }
    reader_delta.close();

    while ((line=reader_gamma.readLine())!= null) {
      result_gamma.add(line);
    }
    reader_gamma.close();

    while ((line=reader_theta.readLine())!= null) {
      result_theta.add(line);
    }
    reader_theta.close();
  }
  catch(IOException e) { //exception
    e.printStackTrace();
    line = null;
  }

  // init form
  centerX = width/2; 
  centerY = height/2;

  // Get 7 points to draw figure.
  float angle = radians(360/formResolution);

  // default Shape size
  for (int i=0; i<formResolution; i++) {
    a1[i] = cos(angle*i) * (initRadius1);
    a2[i] = sin(angle*i) * (initRadius1);
  }

  // Shape size 2
  for (int i = 0; i < formResolution; i++) {
    b1[i] = cos(angle*i) * (initRadius2);
    b2[i] = sin(angle*i) * (initRadius2);
  }

  // Shape size 3
  for (int i = 0; i < formResolution; i++) {
    c1[i] = cos(angle*i) * (initRadius3);
    c2[i] = sin(angle*i) * (initRadius3);
  }

  // Shape size 4
  for (int i = 0; i < formResolution; i++) {
    d1[i] = cos(angle*i) * (initRadius4);
    d2[i] = sin(angle*i) * (initRadius4);
  }
  // Shape size 5
  for (int i = 0; i < formResolution; i++) {
    e1[i] = cos(angle*i) * (initRadius5);
    e2[i] = sin(angle*i) * (initRadius5);
  }

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

  // inline
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

  // text location
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

// draw
void draw() {

  // calculate new points
  // x == default
  // y == impact
  for (int i=0; i<formResolution; i++) {
    a1[i] += random(-stepSize, stepSize);
    a2[i] += random(-stepSize, stepSize);
    b1[i] += random(-stepSize, stepSize);
    b2[i] += random(-stepSize, stepSize); 
    c1[i] += random(-stepSize, stepSize);
    c2[i] += random(-stepSize, stepSize); 
    d1[i] += random(-stepSize, stepSize);
    d2[i] += random(-stepSize, stepSize); 
    e1[i] += random(-stepSize, stepSize);
    e2[i] += random(-stepSize, stepSize);
  }
  // line size
  strokeWeight(1.4);

  // Check the value of c
  // If c==6 is not exist at color[][][].
  // (end of array is color[0][5][5]) So reset int c(try back to first color)
  if (c==6) {
    c=0;  // reset value of c
    drawShape();
  } else {
    drawShape();
  }

  // It slows down the time that the next figure is drawn.
  delay(800);
}

// draw function
void drawShape() {

  if (filled) fill(random(255));
  else noFill();

  stroke(colorList[0][c][0], colorList[0][c][1], colorList[0][c][2]);
  beginShape();

  // start controlpoint
  curveVertex(a1[formResolution-1]+centerX, a2[formResolution-1]+centerY);

  // only these points are drawn
  for (int i=0; i<formResolution; i++) {
    print("delta :", float(result_delta.get(j)), " ");
    print("theta :", float(result_theta.get(j)), " ");
    print("alpha :", float(result_alpha.get(j)), " ");
    print("beta  :", float(result_beta.get(j)), " ");
    print("gamma :", float(result_gamma.get(j)), " ");

    if (0.5<=float(result_delta.get(j)) && float(result_delta.get(j))<1.89999) {
      curveVertex(b1[4]+centerX, b2[4]+centerY);
    } else if (1.9<=float(result_delta.get(j)) && float(result_delta.get(j))<2.89999) {
      curveVertex(c1[4]+centerX, c2[4]+centerY);
    } else if (2.9<=float(result_delta.get(j)) && float(result_delta.get(j))<3.99999) {
      curveVertex(d1[4]+centerX, d2[4]+centerY);
    } else {
      curveVertex(a1[4]+centerX, a2[4]+centerY);
    }

    if (4.0<=float(result_theta.get(j)) && float(result_theta.get(j))<4.89999) {
      curveVertex(b1[3]+centerX, b2[3]+centerY);
    } else if (4.9<=float(result_theta.get(j)) && float(result_theta.get(j))<5.89999) {
      curveVertex(c1[3]+centerX, c2[3]+centerY);
    } else if (5.9<=float(result_theta.get(j)) && float(result_theta.get(j))<6.99999) {
      curveVertex(d1[3]+centerX, d2[3]+centerY);
    } else {
      curveVertex(a1[3]+centerX, a2[3]+centerY);
    }

    if (7.0<=float(result_alpha.get(j)) && float(result_alpha.get(j))<7.89999) {
      curveVertex(b1[2]+centerX, b2[2]+centerY);
    } else if (7.9<=float(result_alpha.get(j)) && float(result_alpha.get(j))<8.89999) {
      curveVertex(c1[2]+centerX, c2[2]+centerY);
    } else if (8.9<=float(result_alpha.get(j)) && float(result_alpha.get(j))<9.99999) {
      curveVertex(d1[2]+centerX, d2[2]+centerY);
    } else {
      curveVertex(a1[2]+centerX, a2[2]+centerY);
    }

    if (10.0<=float(result_alpha.get(j)) && float(result_alpha.get(j))<10.89999) {
      curveVertex(c1[1]+centerX, c2[1]+centerY);
    } else if (10.9<=float(result_alpha.get(j)) && float(result_alpha.get(j))<11.99999) {
      curveVertex(d1[1]+centerX, d2[1]+centerY);
    } else {
      curveVertex(a1[1]+centerX, a2[1]+centerY);
    }

    if (12.0<=float(result_alpha.get(j)) && float(result_alpha.get(j))<12.89999) {
      curveVertex(c1[0]+centerX, c2[0]+centerY);
    } else if (12.9<=float(result_alpha.get(j)) && float(result_alpha.get(j))<13.99999) {
      curveVertex(d1[0]+centerX, d2[0]+centerY);
    } else {
      curveVertex(a1[0]+centerX, a2[0]+centerY);
    }

    if (14.0<=float(result_beta.get(j)) && float(result_beta.get(j))<17.99999) {
      curveVertex(b1[6]+centerX, b2[6]+centerY);
    } else if (18.0<=float(result_beta.get(j)) && float(result_beta.get(j))<21.99999) {
      curveVertex(c1[6]+centerX, c2[6]+centerY);
    } else if (22.0<=float(result_beta.get(j)) && float(result_beta.get(j))<25.99999) {
      curveVertex(d1[6]+centerX, d2[6]+centerY);
    } else if (26.0<=float(result_beta.get(j)) && float(result_beta.get(j))<29.99999) {
      curveVertex(e1[6]+centerX, e2[6]+centerY);
    } else {
      curveVertex(a1[6]+centerX, a2[6]+centerY);
    }

    if (30.0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<34.99999) {
      curveVertex(b1[5]+centerX, b2[5]+centerY);
    } else if (35.0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<39.99999) {
      curveVertex(c1[5]+centerX, c2[5]+centerY);
    } else if (40.0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<44.99999) {
      curveVertex(d1[5]+centerX, d2[5]+centerY);
    } else if (45.0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<49.99999) {
      curveVertex(e1[5]+centerX, e2[5]+centerY);
    } else {
      curveVertex(a1[5]+centerX, a2[5]+centerY);
    }

    j++;
  }
  // end controlpoint
  /*
  curveVertex(a1[0]+centerX, a2[0]+centerY);
  curveVertex(a1[1]+centerX, a2[1]+centerY);
  */
  endShape();
  
  c++;
}
