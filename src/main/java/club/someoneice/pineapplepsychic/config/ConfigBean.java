package club.someoneice.pineapplepsychic.config;

import club.someoneice.pineapplepsychic.PineappleMain;
import club.someoneice.pineapplepsychic.api.IPineappleConfig;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Deprecated
@SuppressWarnings("unchecked, unused")
public class ConfigBean {
    private final JsonHelper JSON_BEAN;
    private final Map<String, Object> CONFIG_MAP;
    public static final Map<String, IPineappleConfig> configMap = Maps.newHashMap();

    public ConfigBean(String configName) {
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + configName + ".json";
        JSON_BEAN = new JsonHelper(path);
        CONFIG_MAP = JSON_BEAN.readFronJson();
    }

    public ConfigBean(String configName, IPineappleConfig object) {
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + configName + ".json";
        JSON_BEAN = new JsonHelper(path);
        CONFIG_MAP = JSON_BEAN.readFronJson();
        configMap.put(configName, object);
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

    public ArrayList<Integer> getIntList(String tag, int[] defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && CONFIG_MAP.get(tag) instanceof List) {
            return (ArrayList<Integer>) CONFIG_MAP.get(tag);
        } else {
            CONFIG_MAP.put(tag, defaultValue);
            return (ArrayList<Integer>) Arrays.stream(defaultValue).boxed().collect(Collectors.toList());
        }
    }

    public ArrayList<String> getStringList(String tag, String[] defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && CONFIG_MAP.get(tag) instanceof List) {
            return (ArrayList<String>) CONFIG_MAP.get(tag);
        } else {
            CONFIG_MAP.put(tag, defaultValue);
            return (ArrayList<String>) Arrays.asList(defaultValue);
        }
    }

    public ArrayList<Double> getDoubleList(String tag, Double[] defaultValue) {
        if (CONFIG_MAP.containsKey(tag) && CONFIG_MAP.get(tag) instanceof List) {
            return (ArrayList<Double>) CONFIG_MAP.get(tag);
        } else {
            CONFIG_MAP.put(tag, defaultValue);
            return (ArrayList<Double>) Arrays.asList(defaultValue);
        }
    }

    /** For List Map **/
    public String getString(String packageName, String tag, String defaultValue) {
        if (CONFIG_MAP.containsKey(packageName) && CONFIG_MAP.get(packageName) instanceof Map) {
            Map map = (Map) CONFIG_MAP.get(packageName);
            if (map.containsKey(tag) && map.get(tag) instanceof String)
                return (String) map.get(tag);
            else {
                map.put(tag, defaultValue);
                return defaultValue;
            }
        } else {
            Map map = new HashMap<>();
            map.put(tag, defaultValue);
            CONFIG_MAP.put(packageName, map);
            return defaultValue;
        }
    }

    public int getInt(String packageName, String tag, int defaultValue) {
        if (CONFIG_MAP.containsKey(packageName) && CONFIG_MAP.get(packageName) instanceof Map) {
            Map map = (Map) CONFIG_MAP.get(packageName);
            if (map.containsKey(tag) && map.get(tag) instanceof Integer)
                return (int) map.get(tag);
            else {
                map.put(tag, defaultValue);
                return defaultValue;
            }
        } else {
            Map map = new HashMap<>();
            map.put(tag, defaultValue);
            CONFIG_MAP.put(packageName, map);
            return defaultValue;
        }
    }

    public boolean getBoolean(String packageName, String tag, boolean defaultValue) {
        if (CONFIG_MAP.containsKey(packageName) && CONFIG_MAP.get(packageName) instanceof Map) {
            Map map = (Map) CONFIG_MAP.get(packageName);
            if (map.containsKey(tag) && map.get(tag) instanceof Boolean)
                return (boolean) map.get(tag);
            else {
                map.put(tag, defaultValue);
                return defaultValue;
            }
        } else {
            Map map = new HashMap<>();
            map.put(tag, defaultValue);
            CONFIG_MAP.put(packageName, map);
            return defaultValue;
        }
    }

    public float getFloat(String packageName, String tag, float defaultValue) {
        if (CONFIG_MAP.containsKey(packageName) && CONFIG_MAP.get(packageName) instanceof Map) {
            Map map = (Map) CONFIG_MAP.get(packageName);
            if (map.containsKey(tag) && map.get(tag) instanceof Float)
                return (Float) map.get(tag);
            else {
                map.put(tag, defaultValue);
                return defaultValue;
            }
        } else {
            Map map = new HashMap<>();
            map.put(tag, defaultValue);
            CONFIG_MAP.put(packageName, map);
            return defaultValue;
        }
    }

    public double getDouble(String packageName, String tag, double defaultValue) {
        if (CONFIG_MAP.containsKey(packageName) && CONFIG_MAP.get(packageName) instanceof Map) {
            Map map = (Map) CONFIG_MAP.get(packageName);
            if (map.containsKey(tag) && (map.get(tag) instanceof Double || map.get(tag) instanceof Float))
                return (Double) map.get(tag);
            else {
                map.put(tag, defaultValue);
                return defaultValue;
            }
        } else {
            Map map = new HashMap<>();
            map.put(tag, defaultValue);
            CONFIG_MAP.put(packageName, map);
            return defaultValue;
        }
    }

    public ArrayList<Integer> getIntList(String packageName, String tag, int[] defaultValue) {
        if (CONFIG_MAP.containsKey(packageName) && CONFIG_MAP.get(packageName) instanceof Map) {
            Map map = (Map) CONFIG_MAP.get(packageName);
            if (map.containsKey(tag) && map.get(tag) instanceof List)
                return (ArrayList<Integer>) map.get(tag);
            else {
                map.put(tag, defaultValue);
                return (ArrayList<Integer>) Arrays.stream(defaultValue).boxed().collect(Collectors.toList());
            }
        } else {
            Map map = new HashMap<>();
            map.put(tag, defaultValue);
            CONFIG_MAP.put(packageName, map);
            return (ArrayList<Integer>) Arrays.stream(defaultValue).boxed().collect(Collectors.toList());
        }
    }

    public ArrayList<String> getStringList(String packageName, String tag, String[] defaultValue) {
        if (CONFIG_MAP.containsKey(packageName) && CONFIG_MAP.get(packageName) instanceof Map) {
            Map map = (Map) CONFIG_MAP.get(packageName);
            if (map.containsKey(tag) && map.get(tag) instanceof List)
                return (ArrayList<String>) map.get(tag);
            else {
                map.put(tag, defaultValue);
                return (ArrayList<String>) Arrays.asList(defaultValue);
            }
        } else {
            Map map = new HashMap<>();
            map.put(tag, defaultValue);
            CONFIG_MAP.put(packageName, map);
            return (ArrayList<String>) Arrays.asList(defaultValue);
        }
    }

    public ArrayList<Double> getDoubleList(String packageName, String tag, Double[] defaultValue) {
        if (CONFIG_MAP.containsKey(packageName) && CONFIG_MAP.get(packageName) instanceof Map) {
            Map map = (Map) CONFIG_MAP.get(packageName);
            if (map.containsKey(tag) && map.get(tag) instanceof List)
                return (ArrayList<Double>) map.get(tag);
            else {
                map.put(tag, defaultValue);
                return (ArrayList<Double>) Arrays.asList(defaultValue);
            }
        } else {
            Map map = new HashMap<>();
            map.put(tag, defaultValue);
            CONFIG_MAP.put(packageName, map);
            return (ArrayList<Double>) Arrays.asList(defaultValue);
        }
    }

    public void build() {
        JSON_BEAN.writeToJson(CONFIG_MAP);
    }

    public static class JsonHelper {
        private Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
                    PineappleMain.LOGGER.error(e);
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
                PineappleMain.LOGGER.error(e);
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
                PineappleMain.LOGGER.error(e);
            }
        }
    }
}
