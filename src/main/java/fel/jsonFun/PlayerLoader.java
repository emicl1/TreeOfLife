package fel.jsonFun;



import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Class that loads a player from a JSON file
 * The JSON file is converted to a PlayerConfig object
 * The PlayerConfig object is then returned
 */
public class PlayerLoader {
    private ObjectMapper mapper;

    public PlayerLoader() {
        this.mapper = new ObjectMapper();
    }

    public PlayerConfig loadPlayer(String path) {
        try {
            PlayerConfig player = mapper.readValue(Gdx.files.internal(path).read(), PlayerConfig.class);
            return player;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

