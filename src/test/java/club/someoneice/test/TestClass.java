package club.someoneice.test;

import com.google.common.collect.Lists;

import java.util.Arrays;

public class TestClass {
    int ic;
    static TestClass c = new TestClass();
    public static void main(String[] args) {
        /*
        Lists.newArrayList("",
                "====================================================================================================",
                "|| .---.  _                                .-.             .---.                  .-.    _        ||",
                "|| : .; ::_;                               : :             : .; :                 : :   :_;       ||",
                "|| :  _.'.-.,-.,-. .--.  .--.  .---. .---. : :   .--.      :  _.'.--. .-..-. .--. : `-. .-. .--.  ||",
                "|| : :   : :: ,. :' '_.'' .; ; : .; `: .; `: :_ ' '_.'     : :  `._-.': :; :'  ..': .. :: :'  ..' ||",
                "|| :_;   :_;:_;:_;`.__.'`.__,_;: ._.': ._.'`.__;`.__.'     :_;  `.__.'`._. ;`.__.':_;:_;:_;`.__.' ||",
                "||                             : :   : :                               .-. :                      ||",
                "||                             :_;   :_;                               `._.'                      ||",
                "====================================================================================================",
                "",
                "Pineapple Psychic is loading now."
        ).forEach(System.out::println);
        */

        String i = "0";


        System.out.println(TestClass.class.getSimpleName());
        System.out.println(i.getClass().getSimpleName());

        Arrays.stream(c.getClass().getDeclaredFields()).forEach(it -> {
            try {
                System.out.println(it.get(c).getClass().getSimpleName());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
