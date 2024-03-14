package fel;

 import java.util.Scanner;
 import java.util.StringJoiner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    double x;
    double y;

    public Main(double y) {
        this.y = y;
    }

    // write a code for prime factorization
    // use a scan object to take input from the user
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number: ");

        int n = scan.nextInt();
        String result = n + " = ";
        StringJoiner sj = new StringJoiner("*", n + "=", "");
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                n = n / i;
                System.out.println(i);
                sj.add(String.valueOf(i));
            }

        }
        System.out.println(sj.toString());
        //sieveOfEratosthenes(100000000);
    }
    // code for sieve of eratosthenes
    // with a given number n, print all prime numbers less than n

    public static void sieveOfEratosthenes(int n) {
        boolean[] prime = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            prime[i] = true;
        }
        for (int p = 2; p * p <= n; p++) {
            if (prime[p] == true) {
                for (int i = p * p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        for (int i = 2; i <= n; i++) {
            if (prime[i] == true) {
                System.out.println(i);
            }
        }
    }
}