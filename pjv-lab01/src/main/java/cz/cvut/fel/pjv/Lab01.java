package cz.cvut.fel.pjv;

import java.util.Scanner;

import static java.lang.System.exit;

/*
Main class with all the functionality
nothing special just an easy calculator
Program was firstly developed to throw
exceptions and repeated use in a while
cycle, BRUTE didn't like it much, but
I left it there for easy repeated use,
just remove the comments and put
sout into the for loop. That's it and
have a great day if you're reading this :)
 */

public class Lab01 {
   
   public void start(String[] args) throws Exception {
     homework();
   }

   Scanner Scan = new Scanner(System.in);
   private boolean run = true;

   private void homework() throws Exception {
      System.out.println("Vyber operaci (1-soucet, 2-rozdil, 3-soucin, 4-podil):");
       while (run){
          if (Scan.hasNext()) {
             int option = Scan.nextInt();
             checkOption(option);
             goThroughOptions(option);
          }
          else {
             run = false;
          }
      }
       Scan.close();
   }

   private void goThroughOptions(int option)throws Exception{
      if (option == 1){
         System.out.println("Zadej scitanec: ");
         double plus1 = Scan.nextDouble();
         System.out.println("Zadej scitanec: ");
         double plus2 = Scan.nextDouble();
         System.out.println("Zadej pocet desetinnych mist: ");
         int numOfPlaces = Scan.nextInt();
         checkNumOfPlaces(numOfPlaces);
         String output = String.format("%." + numOfPlaces + "f + %." + numOfPlaces + "f = %." + numOfPlaces + "f", plus1, plus2, plus1 + plus2);
         System.out.println(output);
      }
      else if (option == 2){
         System.out.println("Zadej mensenec: ");
         double sub1 = Scan.nextDouble();
         System.out.println("Zadej mensitel: ");
         double sub2 = Scan.nextDouble();
         System.out.println("Zadej pocet desetinnych mist: ");
         int numOfPlaces = Scan.nextInt();
         checkNumOfPlaces(numOfPlaces);
         String output = String.format("%." + numOfPlaces + "f - %." + numOfPlaces + "f = %." + numOfPlaces + "f", sub1, sub2, sub1 - sub2);
         System.out.println(output);
      }
      else if (option == 3){
         System.out.println("Zadej cinitel: ");
         double mutiply1 = Scan.nextDouble();
         System.out.println("Zadej cinitel: ");
         double mutiply2 = Scan.nextDouble();
         System.out.println("Zadej pocet desetinnych mist: ");
         int numOfPlaces = Scan.nextInt();
         checkNumOfPlaces(numOfPlaces);
         String output = String.format("%." + numOfPlaces + "f * %." + numOfPlaces + "f = %." + numOfPlaces + "f", mutiply1, mutiply2, mutiply1 * mutiply2);
         System.out.println(output);
      }
      else if (option == 4){
         System.out.println("Zadej delenec: ");
         double devide1 = Scan.nextDouble();
         System.out.println("Zadej delitel: ");
         double devide2 = Scan.nextDouble();
         divisionByZero(devide2);
         System.out.println("Zadej pocet desetinnych mist: ");
         int numOfPlaces = Scan.nextInt();
         checkNumOfPlaces(numOfPlaces);
         String output = String.format("%." + numOfPlaces + "f / %." + numOfPlaces + "f = %." + numOfPlaces + "f", devide1, devide2, devide1 / devide2);
         System.out.println(output);
      }
      else {
         throw new Exception("How did we got here?");
      }

      }
   private void checkNumOfPlaces(int numOfPlaces) throws Exception{
      if (numOfPlaces < 0){
         System.out.println("Chyba - musi byt zadane kladne cislo!");
         run = false;
         exit(0);
         //throw new Exception("Chyba - musi byt zadane kladne cislo!");
      }
   }
   private void checkOption(int option) throws Exception{
      if (option > 4 || option < 1){
         System.out.println("Chybna volba!");
         run = false;
         exit(0);
         //throw new Exception("Chybna volba!");
      }
   }
   private void divisionByZero(double devide2) throws Exception{
      if (devide2 == 0){
         System.out.println("Pokus o deleni nulou!");
         run = false;
         exit(0);
         //throw new Exception("Deleni nulou!");
      }
   }
}