BufferedReader reader; 
ArrayList<String> result = new ArrayList<String>();
String line;
String[] pieces;
// File I/O , file read
void setup() {
  reader = createReader("/Users/kimyosub/Documents/Processing/File_io/test.txt");
  try {
    while ((line=reader.readLine())!= null) {
      result.add(line);
    }
    reader.close();
  }
  catch(IOException e) { //exception
    e.printStackTrace();
    line = null;
  }
  print(result.toString());
}
