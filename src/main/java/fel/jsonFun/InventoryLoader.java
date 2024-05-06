package fel.jsonFun;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InventoryLoader {
    private ObjectMapper mapper;

    public InventoryLoader() {
        this.mapper = new ObjectMapper();
    }

    public InventoryConfig loadInventory(String path) {
        try {
            InventoryConfig inventory = mapper.readValue(Gdx.files.internal(path).read(), InventoryConfig.class);
            return inventory;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
