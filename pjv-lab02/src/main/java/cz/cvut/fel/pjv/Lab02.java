/*
 * File name: Lab06.java
 * Date:      2023/02/22
 * Author:    @michaal4
 */

package cz.cvut.fel.pjv;
import java.util.Scanner;

/*
class to calculate standard deviation and
mean from an ongoing stream of data. Every
10 input of data the deviation and mean are
calculated with a naive approach. Better way 
might be through Welford's algorithm, but it's
only 10 pieces of data, so it's manageable. 
Nevertheless, have a great day :)
 */

public class Lab02 {

   public void start(String[] args) {
      homework();
   }

   Scanner Scan = new Scanner(System.in);
   float[] sequence = new float[10];
   int numOfNum = 0;
   int line = 0;

   private void homework(){
      while (Scan.hasNextLine()){
         String currentLine = Scan.nextLine();
         ++line;
         if(numOfNum == 10){
            printData();
            numOfNum = 0;
            sequence = new float[10];
         }
         // a new scanner specifically for the line
         // because input can have a space between words
         Scanner lineScanner = new Scanner(currentLine);
         if (lineScanner.hasNextFloat()) {
            handle_input(Float.parseFloat(lineScanner.next()));
         }
         else {
            // when it doesn't detect an int it assumes and error
            System.err.printf("A number has not been parsed from line %d\n", line);
         }
         lineScanner.close();
      }
      if (numOfNum != 0 && numOfNum !=1){
         printData();
      }
      System.err.println("End of input detected!");
   }

   private void handle_input(float currentNum){
         sequence[numOfNum] = currentNum;
         ++numOfNum;
   }

   private void printData(){
      double mean = mean();
      double deviation = deviation(mean);
      System.out.printf("%2d %.3f %.3f\n", numOfNum, mean, deviation);
   }

   private double mean(){
      double sum = 0.0;
      for (float element : sequence){
         sum += element;
      }
      return sum / numOfNum;
   }

   private double deviation(double mean){
      double sumOfSquareDiff = 0.0;
      for (int i= 0 ; i< numOfNum ; i++){
         sumOfSquareDiff += Math.pow(sequence[i] - mean, 2);
      }
      return Math.sqrt(sumOfSquareDiff / numOfNum);
   }
}
















