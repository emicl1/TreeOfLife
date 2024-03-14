package fel.model;

import java.util.UUID;

public class car {

    private final String manufacturer;

    private final String modelName;

    private final int modelYear;

    private final UUID vinCode;

    static private int count = 0;

    private String [] carList = new String[5];

    public int getNumberOfCars() {
        return count;
    }

    public car(String modelName, int modelYear, String manufacturer) {
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.manufacturer = manufacturer;
        vinCode = UUID.randomUUID();
        count++;
    }

    public String ToString() {
        return "Car{" + "manufacturer=" + manufacturer + ", modelName=" + modelName + ", modelYear=" + modelYear + ", vinCode=" + vinCode + '}';
    }
}
