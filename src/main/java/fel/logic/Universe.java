package fel.logic;

import java.util.List;

/**
 * Universe class that represents the universe in the game
 * The universe has a name and locations
 */
public class Universe {
    private String name;
    private List<Locations> locations;

    public Universe(String name, List<Locations> locations) {
        this.name = name;
        this.locations = locations;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public String getName() {
        return name;
    }


    public void addLocation(Locations location) {
        locations.add(location);
    }
}
