package pro.friendlyted.mvnsh.core.tools;

import java.util.function.UnaryOperator;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class ArrayTools {

    private ArrayTools() {
    }

    public static <T> void replaceInIndex(T[] toReplace, int index, UnaryOperator<T> replacer) {
        toReplace[index] = replacer.apply(toReplace[index]);
    }

}
