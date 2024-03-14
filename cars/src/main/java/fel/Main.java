package fel;

import fel.model.car;

public class Main {

    public static void main(String[] args) {
        car car = new car("Model S", 2021, "Tesla");
        car car2 = new car("Model 3", 2021, "Tesla");
        car car3 = new car("Model X", 2021, "Tesla");
        car car4 = new car("Model Y", 2021, "Tesla");
        car car5 = new car("Cybertruck", 2021, "Tesla");
        System.out.println(car);
        System.out.println(car5.getNumberOfCars() + "");
    }


}