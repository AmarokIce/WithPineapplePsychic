package club.someoneice.pineapplepsychic.model;

/*  TODO
import club.someoneice.json.Pair;
import club.someoneice.json.node.ArrayNode;
import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;
import club.someoneice.pineapplepsychic.util.math.Vector3D;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.util.List;
import java.util.Map;

import static club.someoneice.pineapplepsychic.util.Util.JSON_BEAN;


@SuppressWarnings("all")
public final class ModelBuffered {
    private final Pair<Integer, Integer> size;
    private final Map<String, ResourceLocation> textures;
    private final List<ModelJsonData> elements;


    private ModelBuffered(MapNode node, String name) {
        if (node.isEmpty()) throwError(name);

        ArrayNode sizeArrayNode = JSON_BEAN.tryPullArrayOrEmpty(node.get("texture_size"));
        if (sizeArrayNode.isEmpty() || sizeArrayNode.getObj().size() < 2) throwError(name);
        this.size = new Pair<>((int) sizeArrayNode.get(0).getObj(), (int) sizeArrayNode.get(1).getObj());

        MapNode textureMapNode = JSON_BEAN.tryPullObjectOrEmpty(node.get("textures"));
        if (textureMapNode.isEmpty()) throwError(name);
        Map<String, JsonNode<?>> texturesMap = textureMapNode.getObj();
        this.textures = Maps.newHashMapWithExpectedSize(texturesMap.size());
        texturesMap.forEach((key, value) -> this.textures.put(key, new ResourceLocation(value.toString())));

        ArrayNode elementsBlockNode = JSON_BEAN.tryPullArrayOrEmpty(node.get("elements"));
        if (elementsBlockNode.isEmpty()) throwError(name);
        List<JsonNode<?>> blockNodeList = elementsBlockNode.getObj();
        this.elements = Lists.newArrayListWithExpectedSize(blockNodeList.size());
        blockNodeList.forEach(it -> {
            MapNode mn = JSON_BEAN.tryPullObjectOrEmpty(it);
            if (mn.isEmpty()) throwError(name);
            elements.add(new ModelJsonData(mn));
        });
    }

    public static ModelBuffered createDataByJson(File file) {
        return new ModelBuffered(JSON_BEAN.tryPullObjectOrEmpty(JSON_BEAN.parse(file)), file.getName());
    }

    private final class ModelJsonData {
        private final Pair<Vector3D, Vector3D> blockSize;

        private ModelJsonData(MapNode node) {
            ArrayNode fromNode = JSON_BEAN.tryPullArrayOrEmpty(node.get("from"));
            ArrayNode toNode = JSON_BEAN.tryPullArrayOrEmpty(node.get("to"));

            this.blockSize = new Pair<>(
                    new Vector3D(pullDouble(fromNode.get(0)), pullDouble(fromNode.get(1)), pullDouble(fromNode.get(2))),
                    new Vector3D(pullDouble(toNode.get(0)), pullDouble(toNode.get(1)), pullDouble(toNode.get(2)))
            );


        }

        private double pullDouble(JsonNode<?> node) {
            return (double) node.asTypeNode().getObj();
        }
    }

    private static void throwError(String name) {
        throw new NonModelCanReadException(String.format("Cannot parse JsonModel in file : %s !", name));
    }
}
*/