package club.someoneice.pineapplepsychic.util;

import club.someoneice.togocup.tags.Ingredient;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class MatchUtil {
    public static final MatchUtil init = new MatchUtil();

    private MatchUtil() {}

    public <T> boolean matchArray(T[] A, T[] B){
        if (!matchArraySizeWithoutNull(A, B)) return false;
        List<T> listA = Arrays.asList(A);
        List<T> listB = Arrays.asList(B);
        listA.removeIf(Objects::isNull);
        listB.removeIf(Objects::isNull);

        listA.forEach(it -> {
            for (int i = 0; i < listB.size(); i++) {
                if (!Objects.equals(it, listB.get(i))) continue;
                listB.remove(i);
                break;
            }
        });

        return listB.isEmpty();
    }

    public boolean matchRecipe(final Ingredient[] recipeIn, final ItemStack[] input) {
        if (!matchArraySizeWithoutNull(recipeIn, input)) return false;

        List<Ingredient> listRecipe = Arrays.asList(recipeIn);
        List<ItemStack> listInput = Arrays.asList(input);

        Map<Integer, List<Ingredient>> listInputByIngredient = Maps.newHashMap();

        listInput.forEach(item -> {
            List<Ingredient> list = Lists.newArrayListWithExpectedSize(5);
            listRecipe.stream().filter(it -> this.matchStackInList(it.getObj(), item)).forEach(list::add);
            listInputByIngredient.put(list.size(), list);
        });

        listInputByIngredient.keySet().stream().sorted().forEach(it -> {
            List<Ingredient> list = listInputByIngredient.get(it);
            for (int i = 0; i < listRecipe.size(); i++) for (Ingredient ingredient : list) {
                if (!listRecipe.get(i).equals(ingredient)) continue;
                listRecipe.remove(i);
                return;
            }
        });

        return listRecipe.isEmpty();
    }

    public boolean matchItemStackInIngredient(Ingredient ingredient, ItemStack itemStack) {
        if (ingredient == null || ingredient.getObj().isEmpty()) return false;
        return matchStackInList(ingredient.getObj(), itemStack);
    }

    public boolean matchStackInList(List<ItemStack> array, ItemStack target) {
        return array.stream().anyMatch(it -> Util.itemStackEquals(it, target));
    }

    public boolean matchStacks(ItemStack[] A, ItemStack[] B) {
        if (!matchArraySizeWithoutNull(A, B)) return false;
        List<ItemStack> listA = Arrays.asList(A);
        List<ItemStack> listB = Arrays.asList(B);

        return matchStackList(listA, listB);
    }

    public boolean matchStackList(List<ItemStack> listA, List<ItemStack> listB) {
        listA.removeIf(Objects::isNull);
        listB.removeIf(Objects::isNull);

        listA.forEach(it -> {
            for (int i = 0; i < listB.size(); i++) {
                if (!Util.itemStackEquals(it, listB.get(i))) continue;
                listB.remove(i);
                break;
            }
        });

        return listB.isEmpty();
    }

    public boolean matchArraySizeWithoutNull(Object[] A, Object[] B) {
        return Arrays.stream(A).filter(Objects::nonNull).count() == Arrays.stream(B).filter(Objects::nonNull).count();
    }
}
