package fel.jsonFun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

/**
 * Class that saves a player to a JSON file
 * The PlayerConfig object is converted to a JSON string
 * The JSON string is then written to a new file
 */
public class PlayerSaver {

    private ObjectMapper mapper;

    public PlayerSaver() {
        this.mapper = new ObjectMapper();
    }

    public void savePlayer(PlayerConfig level, String newPath) {
        FileHandle file = Gdx.files.local(newPath);
        try {
            // Convert the level object to a JSON string
            String json = mapper.writeValueAsString(level);
            // Write the JSON string to a new file
            file.writeString(json, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
