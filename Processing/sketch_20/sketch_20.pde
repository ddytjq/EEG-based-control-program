import processing.pdf.*;

// File i/o
BufferedReader reader_alpha;
BufferedReader reader_low_beta;
BufferedReader reader_high_beta;
BufferedReader reader_delta;
BufferedReader reader_gamma;
BufferedReader reader_theta;
BufferedReader reader_smr;
String line;

// data input (arraylist)
ArrayList<String> result_alpha = new ArrayList<String>(); // real-time alpha data
ArrayList<String> result_low_beta = new ArrayList<String>(); // real-time log_beta data
ArrayList<String> result_high_beta = new ArrayList<String>(); // real-time high_beta data
ArrayList<String> result_delta = new ArrayList<String>(); // real-time delta data
ArrayList<String> result_gamma = new ArrayList<String>(); // real-time gamma data
ArrayList<String> result_theta = new ArrayList<String>(); // real-time theta data
ArrayList<String> result_smr = new ArrayList<String>(); // real-time smr data

// count 
int count = 0;
int number = 1;

// data read counter
int j = 0;
int num = 0;

// color counter
int c = 0;

// 7 location
int formResolution = 7;

// random size
float stepSize = 3;

// graph size
float initRadius1 = 75;
float initRadius2 = 85;
float initRadius3 = 90;
float initRadius4 = 105; 
float initRadius5 = 115;
float initRadius6 = 125;

// graper position
float centerX, centerY;
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
float[] f1 = new float[formResolution]; //
float[] f2 = new float[formResolution]; //

// graph color array
static int[][][] colorList={
  {{102, 0, 88}, {138, 36, 124}, {174, 72, 160}, {210, 108, 196}, {246, 144, 232}, {255, 180, 255}}
};

// graph inside color
boolean filled = false;

// setup
void setup() {

  size(520, 520);
  smooth();

  // Get 7 points to draw figure
  float angle=radians(360/formResolution);

  // movement shape
  for (int i = 0; i < formResolution; i++) {
    a1[i]=cos(angle*i) * initRadius1;
    a2[i]=sin(angle*i) * initRadius1;
    b1[i]=cos(angle*i) * initRadius2;
    b2[i]=sin(angle*i) * initRadius2;
    c1[i]=cos(angle*i) * initRadius3;
    c2[i]=sin(angle*i) * initRadius3;
    d1[i]=cos(angle*i) * initRadius4;
    d2[i]=sin(angle*i) * initRadius4;
    e1[i]=cos(angle*i) * initRadius5;
    e2[i]=sin(angle*i) * initRadius5;
    f1[i]=cos(angle*i) * initRadius6;
    f2[i]=sin(angle*i) * initRadius6;
  }

  // File i/o (alpha, low_beta, high_beta, delta, gamma, theha, smr)
  reader_alpha = createReader("/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/input/n_alpha.txt");
  reader_low_beta = createReader("/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/input/n_low_beta.txt");
  reader_high_beta = createReader("/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/input/n_high_beta.txt");
  reader_delta = createReader("/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/input/n_delta.txt");
  reader_gamma = createReader("/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/input/n_gamma.txt");
  reader_theta = createReader("/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/input/n_theta.txt");
  reader_smr = createReader("/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/input/n_smr.txt");

  // read data (alpha, low_beta, high_beta, delta, gamma, theha, smr)
  try {
    while ((line=reader_alpha.readLine())!= null) {
      result_alpha.add(line);
    }
    reader_alpha.close();

    while ((line=reader_low_beta.readLine())!= null) {
      result_low_beta.add(line);
    }
    reader_low_beta.close();

    while ((line=reader_high_beta.readLine())!= null) {
      result_high_beta.add(line);
    }
    reader_high_beta.close();

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

    while ((line=reader_smr.readLine())!= null) {
      result_smr.add(line);
    }
    reader_smr.close();
  }
  catch(IOException e) {
    e.printStackTrace();
    line = null;
  }

  // init form
  centerX = width/2; 
  centerY = height/2;

  // Draw background lines
  // background color = black
  // lines color = gray
  back();
}

// draw
void draw() {

  // random setting
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
    f1[i] += random(-stepSize, stepSize);
    f2[i] += random(-stepSize, stepSize);
  }

  // line size
  strokeWeight(1.4);

  // Check the value of c
  // If c==6 is not exist at color[][][].
  // (end of array is color[0][5][5]) So reset int c(try back to first color)
  if (c==6) {
    // reset value of c
    c=0;
    drawShape();
  } else {
    drawShape();
  }

  // It slows down the time that the next figure is drawn.
  delay(500);

  // The image is saved every 10 seconds.
  if (count == 10) {
    saveFrame(number+".png");
    count=0;
    number+=1;
    back();
  }
}

void back() {
  background(25, 25, 25);
  stroke(93, 93, 93);

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

  // inline 1~6
  for (int i=0; i < 6; i++) {
    beginShape();
    fill(25, 25, 25);
    vertex(centerX+180*cos(radians(51.4*i)), centerY+(180*sin(radians(51.4*i))));
    vertex(centerX+180*cos(radians(51.4*i)), centerY+(180*sin(radians(51.4*i))));
    vertex(centerX, centerY);
    vertex(centerX, centerY);
    endShape();
  }
  // inline 7
  beginShape();
  fill(25, 25, 25);
  vertex(centerX+(180*cos(radians(51.6))), centerY-(180*sin(radians(51.6))));
  vertex(centerX+(180*cos(radians(51.6))), centerY-(180*sin(radians(51.6))));
  vertex(centerX, centerY);
  vertex(centerX, centerY);
  endShape();

  // text location
  textSize(12);
  fill(255, 255, 255);
  text("Gamma", centerX-65, centerY-185);
  text("High Beta", centerX+117, centerY-147);
  text("Low Beta", centerX+186, centerY);
  text("SMR", centerX+110, centerY+160);
  text(" Alpha", centerX-55, centerY+193);
  text("Theta", centerX-202, centerY+89);
  text("Delta", centerX-202, centerY-77);
}

// draw function
void drawShape() {

  // graph inside color 
  if (filled) fill(random(255));
  else noFill();

  stroke(colorList[0][c][0], colorList[0][c][1], colorList[0][c][2]);

  // start
  beginShape();

  // start controlpoint
  if (num == 1) {  
    curveVertex(a1[6]+centerX, a2[6]+centerY);
  } else if (num == 2) {
    curveVertex(b1[6]+centerX, b2[6]+centerY);
  } else if (num == 3) {
    curveVertex(c1[6]+centerX, c2[6]+centerY);
  } else if (num == 4) {
    curveVertex(d1[6]+centerX, d2[6]+centerY);
  } else if (num == 5) {
    curveVertex(e1[6]+centerX, e2[6]+centerY);
  } else {
    curveVertex(f1[6]+centerX, f2[6]+centerY);
  }

  // low beta
  if (0<=float(result_low_beta.get(j)) && float(result_low_beta.get(j))<14.99999) {
    curveVertex(a1[0]+centerX, a2[0]+centerY);
    num = 1;
  } else if (15.0<=float(result_low_beta.get(j)) && float(result_low_beta.get(j))<29.99999) {
    curveVertex(b1[0]+centerX, b2[0]+centerY);
    num = 2;
  } else if (30.0<=float(result_low_beta.get(j)) && float(result_low_beta.get(j))<44.99999) {
    curveVertex(c1[0]+centerX, c2[0]+centerY);
    num = 3;
  } else if (45.0<=float(result_low_beta.get(j)) && float(result_low_beta.get(j))<59.99999) {
    curveVertex(d1[0]+centerX, d2[0]+centerY);
    num = 4;
  } else if (60.0<=float(result_low_beta.get(j)) && float(result_low_beta.get(j))<74.99999) {
    curveVertex(e1[0]+centerX, e2[0]+centerY);
    num = 5;
  } else {
    curveVertex(f1[0]+centerX, f2[0]+centerY);
    num = 6;
  }

  // smr
  if (0<=float(result_smr.get(j)) && float(result_smr.get(j))<14.99999) {
    curveVertex(a1[1]+centerX, a2[1]+centerY);
  } else if (15.0<=float(result_smr.get(j)) && float(result_smr.get(j))<29.99999) {
    curveVertex(b1[1]+centerX, b2[1]+centerY);
  } else if (30.0<=float(result_smr.get(j)) && float(result_smr.get(j))<44.99999) {
    curveVertex(c1[1]+centerX, c2[1]+centerY);
  } else if (45.0<=float(result_smr.get(j)) && float(result_smr.get(j))<59.99999) {
    curveVertex(d1[1]+centerX, d2[1]+centerY);
  } else if (60.0<=float(result_smr.get(j)) && float(result_smr.get(j))<74.99999) {
    curveVertex(e1[1]+centerX, e2[1]+centerY);
  } else {
    curveVertex(f1[1]+centerX, f2[1]+centerY);
  }

  // alpha
  if (0<=float(result_alpha.get(j)) && float(result_alpha.get(j))<14.99999) {
    curveVertex(a1[2]+centerX, a2[2]+centerY);
  } else if (15.0<=float(result_alpha.get(j)) && float(result_alpha.get(j))<29.99999) {
    curveVertex(b1[2]+centerX, b2[2]+centerY);
  } else if (30.0<=float(result_alpha.get(j)) && float(result_alpha.get(j))<44.99999) {
    curveVertex(c1[2]+centerX, c2[2]+centerY);
  } else if (45.0<=float(result_alpha.get(j)) && float(result_alpha.get(j))<59.99999) {
    curveVertex(d1[2]+centerX, d2[2]+centerY);
  } else if (60.0<=float(result_alpha.get(j)) && float(result_alpha.get(j))<74.99999) {
    curveVertex(e1[2]+centerX, e2[2]+centerY);
  } else {
    curveVertex(f1[2]+centerX, f2[2]+centerY);
  }

  // theta
  if (0<=float(result_theta.get(j)) && float(result_theta.get(j))<14.99999) {
    curveVertex(a1[3]+centerX, a2[3]+centerY);
  } else if (15.0<=float(result_theta.get(j)) && float(result_theta.get(j))<29.99999) {
    curveVertex(b1[3]+centerX, b2[3]+centerY);
  } else if (30.0<=float(result_theta.get(j)) && float(result_theta.get(j))<44.99999) {
    curveVertex(c1[3]+centerX, c2[3]+centerY);
  } else if (45.0<=float(result_theta.get(j)) && float(result_theta.get(j))<59.99999) {
    curveVertex(d1[3]+centerX, d2[3]+centerY);
  } else if (60.0<=float(result_theta.get(j)) && float(result_theta.get(j))<74.99999) {
    curveVertex(e1[3]+centerX, e2[3]+centerY);
  } else {
    curveVertex(f1[3]+centerX, f2[3]+centerY);
  }

  // delta
  if (0<=float(result_delta.get(j)) && float(result_delta.get(j))<14.99999) {
    curveVertex(a1[4]+centerX, a2[4]+centerY);
  } else if (15.0<=float(result_delta.get(j)) && float(result_delta.get(j))<29.99999) {
    curveVertex(b1[4]+centerX, b2[4]+centerY);
  } else if (30.0<=float(result_delta.get(j)) && float(result_delta.get(j))<44.99999) {
    curveVertex(c1[4]+centerX, c2[4]+centerY);
  } else if (45.0<=float(result_delta.get(j)) && float(result_delta.get(j))<59.99999) {
    curveVertex(d1[4]+centerX, d2[4]+centerY);
  } else if (60.0<=float(result_delta.get(j)) && float(result_delta.get(j))<74.99999) {
    curveVertex(e1[4]+centerX, e2[4]+centerY);
  } else {
    curveVertex(f1[4]+centerX, f2[4]+centerY);
  }

  // gamma
  if (0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<14.99999) {
    curveVertex(a1[5]+centerX, a2[5]+centerY);
  } else if (15.0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<29.99999) {
    curveVertex(b1[5]+centerX, b2[5]+centerY);
  } else if (30.0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<44.99999) {
    curveVertex(c1[5]+centerX, c2[5]+centerY);
  } else if (45.0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<59.99999) {
    curveVertex(d1[5]+centerX, d2[5]+centerY);
  } else if (60.0<=float(result_gamma.get(j)) && float(result_gamma.get(j))<74.99999) {
    curveVertex(e1[5]+centerX, e2[5]+centerY);
  } else {
    curveVertex(f1[5]+centerX, f2[5]+centerY);
  }

  // high beta
  if (0<=float(result_high_beta.get(j)) && float(result_high_beta.get(j))<14.99999) {
    curveVertex(a1[6]+centerX, a2[6]+centerY);
  } else if (15.0<=float(result_high_beta.get(j)) && float(result_high_beta.get(j))<29.99999) {
    curveVertex(b1[6]+centerX, b2[6]+centerY);
  } else if (30.0<=float(result_high_beta.get(j)) && float(result_high_beta.get(j))<44.99999) {
    curveVertex(c1[6]+centerX, c2[6]+centerY);
  } else if (45.0<=float(result_high_beta.get(j)) && float(result_high_beta.get(j))<59.99999) {
    curveVertex(d1[6]+centerX, d2[6]+centerY);
  } else if (60.0<=float(result_high_beta.get(j)) && float(result_high_beta.get(j))<74.99999) {
    curveVertex(e1[6]+centerX, e2[6]+centerY);
  } else {
    curveVertex(f1[6]+centerX, f2[6]+centerY);
  }

  // end controlpoint
  if (num == 1 || num == 0) {  
    curveVertex(a1[0]+centerX, a2[0]+centerY);
    curveVertex(a1[1]+centerX, a2[1]+centerY);
  } else if (num == 2) {
    curveVertex(b1[0]+centerX, b2[0]+centerY);
    curveVertex(b1[1]+centerX, b2[1]+centerY);
  } else if (num == 3) {
    curveVertex(c1[0]+centerX, c2[0]+centerY);
    curveVertex(c1[1]+centerX, c2[1]+centerY);
  } else if (num == 4) {
    curveVertex(d1[0]+centerX, d2[0]+centerY);
    curveVertex(d1[1]+centerX, d2[1]+centerY);
  } else if (num == 5) {
    curveVertex(e1[0]+centerX, e2[0]+centerY);
    curveVertex(e1[1]+centerX, e2[1]+centerY);
  } else if (num == 6) {
    curveVertex(f1[0]+centerX, f2[0]+centerY);
    curveVertex(f1[1]+centerX, f2[1]+centerY);
  }

  if ( j==result_alpha.size()-1 ) {
    exit();
  }

  //end
  endShape();

  // eeg count ++
  j++;

  // graph count ++
  c++;
  count++;
}
