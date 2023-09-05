package club.someoneice.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.shadew.json.Json;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestClass {
    public static void main(String[] args) throws IOException {
        File file = new File(System.getProperty("user.dir") + File.separator + "File.json5");
        Json json = Json.json5();
        String cr = json.parse(file).asMap().toString();
        Gson gson = new Gson();
        String c = gson.toJson(cr)
                .replaceAll("\\\\\"", "")
                .replaceAll("\\\\u003d", ":")
                .replaceAll("\"", "");

        System.out.print(c + "\n");

        Map<String, String> map = gson.fromJson(c, new TypeToken<Map<String, Object>>() {}.getType());

        map.forEach((k, v) -> {
            System.out.print(k);
            System.out.print("\n");
            System.out.print(v);
        });


    }
}
