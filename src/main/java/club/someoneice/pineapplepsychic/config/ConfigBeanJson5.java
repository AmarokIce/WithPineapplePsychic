package club.someoneice.pineapplepsychic.config;

import club.someoneice.json.JSON;
import club.someoneice.json.Pair;
import club.someoneice.json.PairList;
import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;
import club.someoneice.json.processor.Json5Builder;
import club.someoneice.pineapplepsychic.command.IPineappleConfig;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.Loader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "unused"})
public class ConfigBeanJson5 {
    private final PairList<String, String> command = new PairList<>();
    private final Set<String> packageSets = Sets.newHashSet();
    private final Map<String, Object> config = Maps.newHashMap();
    private final MapNode nodeBase;
    private final File file;

    public ConfigBeanJson5(String fileName, IPineappleConfig pineappleConfig) {
        MapNode nodeBase1;
        File configFile = new File(Loader.instance().getConfigDir().getPath(), fileName + ".json");

        try {
            nodeBase1 = ConfigUtil.INITIALIZE.readFromJson(configFile);
        } catch (IOException e) {
            nodeBase1 = new MapNode();
        }

        // Lazy setting.
        this.nodeBase = nodeBase1;
        this.file = configFile;
    }

    public String getString(String key, String defValue) {
        return getBean(key, defValue);
    }

    public int getInteger(String key, int defValue) {
        return getBean(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getBean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return getBean(key, defValue);
    }

    public double getFloat(String key, double defValue) {
        return getBean(key, defValue);
    }


    public String getString(String key, String defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public int getInteger(String key, int defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public boolean getBoolean(String key, boolean defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public float getFloat(String key, float defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public double getFloat(String key, double defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public void addNote(String note) {
        command.put("note", note);
    }

    public void addEnter() {
        command.put("enter", "enter");
    }

    public void build() {
        Json5Builder builder = new Json5Builder();
        Json5Builder.ObjectBean bean = builder.getObjectBean();

        for (Pair<String, String> it : command.asList()) {
            if (it.getKey().equals("put")) {
                bean.put(it.getValue(), new JsonNode<>(config.get(it.getValue())));
            } else if (it.getKey().equals("map")) {
                bean.put(it.getValue(), mapToNode((Map<String, Object>) config.get(it.getValue())));
            } else if (it.getKey().equals("note")) {
                bean.enterLine();
                bean.addNote(it.getValue());
            } else if (it.getKey().equals("enter")) {
                bean.enterLine();
            }
        }

        builder.put(bean);
        String str = builder.build();

        try {
            ConfigUtil.INITIALIZE.writeToJson(this.file, str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MapNode mapToNode(Map<String, ?> map) {
        MapNode node = new MapNode();
        map.forEach((key, value) -> {
            if (value instanceof Map) {
                node.put(key, mapToNode((Map<String, ?>) value));
            } else node.put(key, new club.someoneice.json.node.JsonNode<>(value));
        });

        return node;
    }

    private <T> T getBean(String key, T defValue) {
        T value;
        if (nodeBase.has(key)) {
            value = (T) nodeBase.get(key).getObj();
        } else value = defValue;

        config.put(key, value);
        command.put("put", key);
        return value;
    }

    private  <T> T getBeanWithPackage(String key, T defValue, String packName) {
        T value;

        if (nodeBase.has(packName)) {
            MapNode node = JSON.json.tryPullObjectOrEmpty(nodeBase.get(packName));
            if (node.has(key)) {
                value = (T) node.get(key).getObj();
            } else value = defValue;
        } else {
            value = defValue;
        }

        Map<String, Object> objectMap;
        if (config.containsKey(packName)) {
            objectMap = (Map<String, Object>) config.get(packName);
        } else {
            objectMap = Maps.newHashMap();
        }

        objectMap.put(key, value);
        config.put(key, objectMap);
        if (!packageSets.contains(packName)) command.put("map", packName);
        return value;
    }
}
