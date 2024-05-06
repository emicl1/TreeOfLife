package fel.jsonFun;



import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ItemsToCraftLoader {
    private ObjectMapper mapper;

    public ItemsToCraftLoader() {
        this.mapper = new ObjectMapper();
    }

    public ItemsToCraftConfig loadItemsToCraft(String path) {
        try {
            ItemsToCraftConfig itemsToCraft = mapper.readValue(Gdx.files.internal(path).read(), ItemsToCraftConfig.class);
            return itemsToCraft;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

