package club.someoneice.pineapplepsychic.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ConfigBean {
    private final JsonHelper JSON_BEAN;
    private final Map<String, Object> CONFIG_MAP;

    public ConfigBean(String configName) {
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + configName + ".json";
        JSON_BEAN = new JsonHelper(path);
        CONFIG_MAP = JSON_BEAN.readFronJson();
    }

    public String getString(String tag, String defaultValue) {
        if (CONFIG_MAP.containsKey(tag)) {
            return CONFIG_MAP.get(tag).toString();
        } else {
            CONFIG_MAP.put(tag, defaultValue);
            return defaultValue;
        }
    }

    public int getInt(String tag, int defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && CONFIG_MAP.get(tag) instanceof Integer) {
            return (Integer) CONFIG_MAP.get(tag);
        } else {
            CONFIG_MAP.put(tag, defaultValue);
            return defaultValue;
        }
    }

    public boolean getBoolean(String tag, Boolean defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && CONFIG_MAP.get(tag) instanceof Boolean) {
            return (Boolean) CONFIG_MAP.get(tag);
        } else {
            CONFIG_MAP.put(tag, defaultValue);
            return defaultValue;
        }
    }

    public float getFloat(String tag, float defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && CONFIG_MAP.get(tag) instanceof Float) {
            return (Float) CONFIG_MAP.get(tag);
        } else {
            CONFIG_MAP.put(tag, defaultValue);
            return defaultValue;
        }
    }

    public double getDouble(String tag, double defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && (CONFIG_MAP.get(tag) instanceof Float || CONFIG_MAP.get(tag) instanceof Double)) {
            return (Double) CONFIG_MAP.get(tag);
        } else {
            CONFIG_MAP.put(tag, defaultValue);
            return defaultValue;
        }
    }

    public void build() {
        JSON_BEAN.writeToJson(CONFIG_MAP);
    }


    private class JsonHelper {
        private Gson gson = new GsonBuilder().create();
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

        public Map<String, Object> readFronJson() {
            byte[] stringText = new byte[((Long) file.length()).intValue()];
            try {
                FileInputStream inputStream = new FileInputStream(this.file);
                inputStream.read(stringText);
                inputStream.close();

                String text = new String(stringText, StandardCharsets.UTF_8);
                Map<String, Object> map = gson.fromJson(text, new TypeToken<Map<String, Object>>() {}.getType());
                if (map != null) return map;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new HashMap<>();
        }

        public void writeToJson(Map<String, Object> map) {
            String cfg = gson.toJson(map);
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(cfg.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
