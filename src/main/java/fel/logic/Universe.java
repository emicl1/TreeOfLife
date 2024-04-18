package fel.logic;


public class Universe {
    private String name;
    private Locations[] locations;

    public Universe(String name, Locations[] locations) {
        this.name = name;
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public Locations[] getLocations() {
        return locations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocations(Locations[] locations) {
        this.locations = locations;
    }

    public void addLocation(Locations location) {
        Locations[] temp = new Locations[locations.length + 1];
        for (int i = 0; i < locations.length; i++) {
            temp[i] = locations[i];
        }
        temp[locations.length] = location;
        locations = temp;
    }

    public void getLocationsInfo() {
        for (int i = 0; i < locations.length; i++) {
            System.out.println(locations[i].getName());
        }
    }

}
