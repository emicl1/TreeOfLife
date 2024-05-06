package fel.jsonFun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

public class LevelSaver {

    private ObjectMapper mapper;

    public LevelSaver() {
        this.mapper = new ObjectMapper();
    }

    public void saveLevel(LevelConfig level, String newPath) {
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
