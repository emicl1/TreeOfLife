package fel.jsonFun;


import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Class that loads a level from a JSON file
 * The JSON file is converted to a LevelConfig object
 * The LevelConfig object is then returned
 */

public class LevelLoader {
    private ObjectMapper mapper;

    public LevelLoader() {
        this.mapper = new ObjectMapper();
    }

    public LevelConfig loadLevel(String path) {
        try {
            String json = new String(Gdx.files.internal(path).readBytes());
            LevelConfig config = mapper.readValue(json, LevelConfig.class);
            return config;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

