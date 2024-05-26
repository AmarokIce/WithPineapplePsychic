package club.someoneice.pineapplepsychic.config;

import club.someoneice.json.JSON;
import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;
import club.someoneice.json.processor.Json5Builder;
import club.someoneice.pineapplepsychic.api.IPineappleConfig;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class ConfigData {
    private ConfigData() {}

    public static final ConfigData INITIALIZE = new ConfigData();
    public Map<String, IPineappleConfig> configs = Maps.newHashMap();

    MapNode readFromJson(File file) throws IOException {
        if (!file.exists() || !file.isFile()) file.createNewFile();
        else if (file.canRead()) {
            JsonNode<?> node = JSON.json5.parse(file);
            if (node.getType() == JsonNode.NodeType.Null) return new MapNode();
            if (node.getType() == JsonNode.NodeType.Map) return (MapNode) node;
        }

        return new MapNode();
    }

    void writeToJson(File file, String str) throws IOException {
        if (!file.exists() || !file.isFile()) file.createNewFile();
        Files.write(str.getBytes(), file);

    }

    Json5Builder.ObjectBean getObjectBean() {
        return new Json5Builder.ObjectBean();
    }

    Json5Builder.ArrayBean getArrayBean() {
        return new Json5Builder.ArrayBean();
    }
}
