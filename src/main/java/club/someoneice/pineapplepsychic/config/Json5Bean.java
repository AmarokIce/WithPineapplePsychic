package club.someoneice.pineapplepsychic.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Json5Bean {
    private final JsonHelper JSON_BEAN;
    private final Map<String, JsonNode> CONFIG_MAP;

    public Json5Bean(String configName) {
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + configName + ".json5";
        JSON_BEAN = new JsonHelper(path);
        CONFIG_MAP = JSON_BEAN.readFronJson();
    }

    public Json5Bean(String configName, IPineappleConfig object) {
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + configName + ".json5";
        JSON_BEAN = new JsonHelper(path);
        CONFIG_MAP = JSON_BEAN.readFronJson();
        ConfigData.configMap.put(configName, object);
    }

    public String getString(String tag, String defaultValue) {
        if (CONFIG_MAP.containsKey(tag)) {
            return CONFIG_MAP.get(tag).asString();
        } else {
            CONFIG_MAP.put(tag, JsonNode.string(defaultValue));
            return defaultValue;
        }
    }

    public int getInt(String tag, int defaultValue) {
        if (CONFIG_MAP.containsKey(tag)) {
            return CONFIG_MAP.get(tag).asInt();
        } else {
            CONFIG_MAP.put(tag, JsonNode.number(defaultValue));
            return defaultValue;
        }
    }

    public boolean getBoolean(String tag, Boolean defaultValue) {
        if (CONFIG_MAP.containsKey(tag)) {
            return CONFIG_MAP.get(tag).asBoolean();
        } else {
            CONFIG_MAP.put(tag, JsonNode.bool(defaultValue));
            return defaultValue;
        }
    }

    public float getFloat(String tag, float defaultValue) {
        if (CONFIG_MAP.containsKey(tag)) {
            return CONFIG_MAP.get(tag).asFloat();
        } else {
            CONFIG_MAP.put(tag, JsonNode.number(defaultValue));
            return defaultValue;
        }
    }

    public double getDouble(String tag, double defaultValue) {
        if (CONFIG_MAP.containsKey(tag)) {
            return CONFIG_MAP.get(tag).asDouble();
        } else {
            CONFIG_MAP.put(tag, JsonNode.number(defaultValue));
            return defaultValue;
        }
    }

    public ArrayList<Integer> getIntList(String tag, int[] defaultValue) {
        if (CONFIG_MAP.containsKey(tag)) {
            ArrayList<Integer> list = Lists.newArrayList();
            for (int i : CONFIG_MAP.get(tag).asIntArray()) list.add(i);
            return list;
        } else {
            CONFIG_MAP.put(tag, JsonNode.numberArray(defaultValue));
            return (ArrayList<Integer>) Arrays.stream(defaultValue).boxed().collect(Collectors.toList());
        }
    }

    public ArrayList<String> getStringList(String tag, String[] defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && CONFIG_MAP.get(tag) instanceof List) {
            ArrayList<String> list = Lists.newArrayList();
            list.addAll(Arrays.asList(CONFIG_MAP.get(tag).asStringArray()));
            return list;
        } else {
            CONFIG_MAP.put(tag, JsonNode.stringArray(defaultValue));
            return (ArrayList<String>) Arrays.asList(defaultValue);
        }
    }

    public ArrayList<Double> getDoubleList(String tag, Double[] defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && CONFIG_MAP.get(tag) instanceof List) {
            ArrayList<Double> list = Lists.newArrayList();
            for (double i : CONFIG_MAP.get(tag).asDoubleArray()) list.add(i);
            return list;
        } else {
            CONFIG_MAP.put(tag, JsonNode.numberArray(defaultValue));
            return (ArrayList<Double>) Arrays.asList(defaultValue);
        }
    }

    public void build() {
        JSON_BEAN.writeToJson(CONFIG_MAP);
    }

    static class JsonHelper {
        Json json = Json.json5();

        private File file;
        public JsonHelper(String filePath) {
            this(new File(filePath));
        }

        public JsonHelper(File file) {
            if (file.isFile()) {
                this.file = file;
            } else {
                try {
                    file.createNewFile();
                    this.file = file;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public Map<String, JsonNode> readFronJson() {
            try {
                return json.parse(file).asMap();
            } catch (IOException e) {
                return Maps.newHashMap();
            }
        }

        public void writeToJson(Map<String, JsonNode> map) {
            try {
                json.serialize(JsonNode.object(map), file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
