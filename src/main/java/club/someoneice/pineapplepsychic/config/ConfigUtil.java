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

public class ConfigUtil {
    private ConfigUtil() {}

    public static final ConfigUtil INITIALIZE = new ConfigUtil();
    public Map<String, IPineappleConfig> configs = Maps.newHashMap();

    public MapNode readFromJson(File file) throws IOException {
        if (!file.exists() || !file.isFile()) file.createNewFile();
        else if (file.canRead()) {
            JsonNode<?> node = JSON.json5.parse(file);
            if (node.getType() == JsonNode.NodeType.Null) return new MapNode();
            if (node.getType() == JsonNode.NodeType.Map) return (MapNode) node;
        }

        return new MapNode();
    }

    public void writeToJson(File file, String str) throws IOException {
        if (!file.exists() || !file.isFile()) file.createNewFile();
        Files.write(str.getBytes(), file);

    }

    private final Json5Builder builder = new Json5Builder();
    public Json5Builder.ObjectBean getObjectBean() {
        return builder.getObjectBean();
    }

    public Json5Builder.ArrayBean getArrayBean() {
        return builder.getArrayBean();
    }
}
