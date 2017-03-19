package pro.friendlyted.mvnsh.core.tools;

import java.util.function.Function;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class ArrayTools {

    private ArrayTools() {
    }

    public static void replaceInIndex(String[] toReplace, int index, Function<String, String> replacer) {
        toReplace[index] = replacer.apply(toReplace[index]);
    }

}
