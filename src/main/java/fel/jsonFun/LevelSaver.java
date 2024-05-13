package fel.jsonFun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

/**
 * Class that saves a level to a JSON file
 * The LevelConfig object is converted to a JSON string
 * The JSON string is then written to a new file
 */
public class LevelSaver {

    private ObjectMapper mapper;

    public LevelSaver() {
        this.mapper = new ObjectMapper();
    }

    public void saveLevel(LevelConfig level, String newPath) {
        FileHandle file = Gdx.files.local(newPath);
        try {
            String json = mapper.writeValueAsString(level);
            file.writeString(json, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
