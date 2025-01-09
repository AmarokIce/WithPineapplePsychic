package club.someoneice.pineapplepsychic.util;

import club.someoneice.togocup.tags.Ingredient;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public final class MatchUtil {
    private MatchUtil() {
    }

    /**
     * Match two of arrays' what they hold is the same. <br>
     * 匹配两份 Array 持有的内容是相同的。<br>
     *
     * @param A The array A.
     * @param B The array B.
     * @return If the collections same.
     * @param <T> The anything.
     */
    public static <T> boolean matchArray(Collection<T> A, Collection<T> B) {
        if (!matchArraySizeWithoutNull(A, B)) return false;
        List<T> listA = Lists.newArrayList(A.stream().filter(Objects::nonNull).iterator());
        List<T> listB = Lists.newArrayList(B.stream().filter(Objects::nonNull).iterator());

        if (listA.isEmpty() && listB.isEmpty()) return true;
        if (listA.size() != listB.size()) return false;

        listA.forEach(it -> {
            for (int i = 0; i < listB.size(); i++) {
                if (!Objects.equals(it, listB.get(i))) continue;
                listB.remove(i);
                break;
            }
        });

        return listB.isEmpty();
    }

    /**
     * It is usually used to match recipes, checking that what is entered is the same as what the recipe requires.  <br>
     * 通常会用于匹配食谱，检查输入的内容与食谱需求是相同的。<br>
     *
     * @param recipeIn The recipe requires.
     * @param input    The input by player.
     */
    public static boolean matchRecipe(final ImmutableList<Ingredient> recipeIn, final ImmutableList<ItemStack> input) {
        if (!matchArraySizeWithoutNull(recipeIn, input)) return false;

        List<Integer> cache = Lists.newArrayList();
        for (int i = 0; i < recipeIn.size(); i ++) {
            for (int o = 0; o < input.size(); o ++) {
                if (cache.contains(o)) continue;
                if (matchItemStackInIngredient(recipeIn.get(i), input.get(o))) {
                    cache.add(o);
                    break;
                }
            }

            if (cache.size()  < i) {
                return false;
            }
        }

        return cache.size() == recipeIn.size();
    }

    /*
    public static boolean matchRecipe(final Ingredient[] recipeIn, final ItemStack[] input) {
        if (!matchArraySizeWithoutNull(recipeIn, input)) return false;
        List<Ingredient> listRecipe = Arrays.asList(recipeIn);
        List<ItemStack> listInput = Arrays.asList(input);

        if (listRecipe.size() != listInput.size()) return false;

        Map<Integer, List<Ingredient>> listInputByIngredient = Maps.newHashMap();

        listInput.forEach(item -> {
            List<Ingredient> list = Lists.newArrayListWithExpectedSize(5);
            listRecipe.stream().filter(it -> matchStackInList(it.getObj(), item)).forEach(list::add);
            listInputByIngredient.put(list.size(), list);
        });

        listInputByIngredient.keySet().stream().sorted().forEach(it -> {
            List<Ingredient> list = listInputByIngredient.get(it);
            for (int i = 0; i < listRecipe.size(); i++)
                for (Ingredient ingredient : list) {
                    if (!listRecipe.get(i).equals(ingredient)) continue;
                    listRecipe.remove(i);
                    return;
                }
        });

        return listRecipe.isEmpty();
    }
    */

    /**
     * Check to see if the item is included in Ingredient. <br>
     * 检查物品是否被包含于 Ingredient。
     */
    public static boolean matchItemStackInIngredient(Ingredient ingredient, ItemStack itemStack) {
        if (ingredient == null || ingredient.getObj().isEmpty()) return false;
        return matchStackInList(ingredient.getObj(), itemStack);
    }

    /**
     * Check to see if the item is included in List. <br>
     * 检查物品是否被包含于 List。
     */
    public static boolean matchStackInList(List<ItemStack> array, ItemStack target) {
        return array.stream().anyMatch(it -> Util.itemStackEquals(it, target));
    }

    /**
     * Match the Array that holds the ItemStack. <br>
     * 匹配持有 ItemStack 的 Array。
     */
    public static boolean matchStacks(Collection<ItemStack> A, Collection<ItemStack> B) {
        if (!matchArraySizeWithoutNull(A, B)) return false;
        return matchStackList(A, B);
    }

    /**
     * Match the List that holds the ItemStack. <br>
     * 匹配持有 ItemStack 的 List。
     */
    public static boolean matchStackList(Collection<ItemStack> AList, Collection<ItemStack> BList) {
        List<ItemStack> listA = Lists.newArrayList(AList.stream().filter(Objects::nonNull).iterator());
        List<ItemStack> listB = Lists.newArrayList(BList.stream().filter(Objects::nonNull).iterator());

        if (listA.isEmpty() && listB.isEmpty()) return true;
        if (listA.size() != listB.size()) return false;

        listA.forEach(it -> {
            for (int i = 0; i < listB.size(); i++) {
                if (!Util.itemStackEquals(it, listB.get(i))) {
                    continue;
                }
                listB.remove(i);
                break;
            }
        });

        return listB.isEmpty();
    }

    /**
     * Check the Array size without Null objects.
     */
    public static <A, B> boolean matchArraySizeWithoutNull(Collection<A> AList, Collection<B> BList) {
        return AList.stream().filter(Objects::nonNull).count() == BList.stream().filter(Objects::nonNull).count();
    }
}
