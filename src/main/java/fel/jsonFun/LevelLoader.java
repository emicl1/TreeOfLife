package fel.jsonFun;


import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LevelLoader {
    private ObjectMapper mapper;

    public LevelLoader() {
        this.mapper = new ObjectMapper();
    }

    public LevelConfig loadLevel(String path) {
        try {
            String json = new String(Gdx.files.internal(path).readBytes());
            //System.out.println("Loading JSON: " + json);  // Print the raw JSON
            LevelConfig config = mapper.readValue(json, LevelConfig.class);
//            if (config.items != null) {
//                System.out.println("Loaded " + config.items.size() + " items.");
//            } else {
//                System.out.println("No items loaded.");
//            }
            return config;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

