package fel.jsonFun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InventorySaver {

    private ObjectMapper mapper;

    public InventorySaver() {
        this.mapper = new ObjectMapper();
    }

    public void saveInventory(InventoryConfig inventory, String newPath) {
        FileHandle file = Gdx.files.local(newPath);
        try {
            // Convert the level object to a JSON string
            String json = mapper.writeValueAsString(inventory);
            // Write the JSON string to a new file
            file.writeString(json, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
