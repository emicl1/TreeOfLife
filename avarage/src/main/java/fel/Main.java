package fel;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        float sum = 0;
        int count = args.length;

        for (int i = 0; i < args.length; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            sum += Integer.valueOf(args[i]);
        }

        Scanner scn = new Scanner(System.in);
        while (scn.hasNextInt()) {
            sum = sum + scn.nextInt();
            count++;
        }
        System.out.println(sum / count);
    }
}