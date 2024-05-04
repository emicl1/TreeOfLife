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
            LevelConfig level = mapper.readValue(Gdx.files.internal(path).read(), LevelConfig.class);
            return level;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

