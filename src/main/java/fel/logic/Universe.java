package fel.logic;


import java.util.List;

public class Universe {
    private String name;
    private List<Locations> locations;

    public Universe(String name, List<Locations> locations) {
        this.name = name;
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public void addLocation(Locations location){
        locations.add(location);
    }

    public void removeLocation(Locations location) {
        locations.remove(location);
    }

    public void addLocations(List<Locations> locations){
        this.locations.addAll(locations);
    }


}
