package fel;

import java.util.Random;

public class dice {

    public int throwIt(){
        Random rnd = new Random();
        return rnd.nextInt(1, 7);
    }


}
