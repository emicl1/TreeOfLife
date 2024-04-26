package fel.jsonFun;



import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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

