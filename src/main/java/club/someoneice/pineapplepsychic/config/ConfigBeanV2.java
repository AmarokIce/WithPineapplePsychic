package club.someoneice.pineapplepsychic.config;

import club.someoneice.json.JSON;
import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;
import club.someoneice.json.processor.Json5Builder;
import club.someoneice.pineapplepsychic.PineappleMain;
import club.someoneice.pineapplepsychic.api.IPineappleConfig;
import cpw.mods.fml.common.Loader;

import java.io.File;
import java.io.IOException;

@SuppressWarnings({"unchecked", "unused"})
public class ConfigBeanV2 {
    private final MapNode nodeBase;
    private final File file;

    public ConfigBeanV2(String fileName) {
        MapNode nodeBase1;
        File configFile = new File(Loader.instance().getConfigDir().getPath(), fileName + ".json5");

        try {
            nodeBase1 = ConfigUtil.INITIALIZE.readFromJson(configFile);
        } catch (IOException e) {
            nodeBase1 = new MapNode();
        }

        // Lazy setting.
        this.nodeBase = nodeBase1;
        this.file = configFile;

        if (this instanceof IPineappleConfig) {
            ConfigUtil.INITIALIZE.configs.put(fileName, (IPineappleConfig) this);
        }
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

    // public void addNote(String note) {
    //     beanBase.addNote(note);
    // }

    // public void addEnter() {
    //     beanBase.enterLine();
    // }

    public void build() {
        Json5Builder builder = new Json5Builder();
        Json5Builder.ObjectBean beanBase = builder.getObjectBean();
        nodeBase.getObj().forEach(beanBase::put);

        builder.put(beanBase);
        String str = builder.build();
        try {
            ConfigUtil.INITIALIZE.writeToJson(this.file, str);
        } catch (IOException e) {
            PineappleMain.LOGGER.error(e);
        }
    }

    private <T> T getBean(String key, T defValue) {
        T value;
        if (nodeBase.has(key)) {
            value = (T) nodeBase.get(key).getObj();
        } else value = defValue;

        nodeBase.put(key, new JsonNode<>(value));
        return value;
    }

    private  <T> T getBeanWithPackage(String key, T defValue, String packName) {
        T value;
        MapNode node;
        if (nodeBase.has(packName)) {
            node = JSON.json.tryPullObjectOrEmpty(nodeBase.get(packName));
            if (node.has(key)) {
                value = (T) node.get(key).getObj();
            } else value = defValue;
        } else {
            node = new MapNode();
            value = defValue;
        }

        node.put(key, new JsonNode<>(value));
        nodeBase.put(packName, node);

        return value;
    }
}
