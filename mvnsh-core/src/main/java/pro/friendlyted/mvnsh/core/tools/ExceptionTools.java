package pro.friendlyted.mvnsh.core.tools;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class ExceptionTools {

    private ExceptionTools() {
    }
    
    public static final String PARAMETER_MUST_BE_SPECIFIED = "Parameter '%s' must be specified";
    public static final String WRONG_FORMAT = "Executable '%s' has wrong format";

    public static RuntimeException parameterNotSpecified(String paramName) {
        return format(PARAMETER_MUST_BE_SPECIFIED, paramName);
    }

    public static RuntimeException wrong_format(String source) {
        return format(WRONG_FORMAT, source);
    }

    private static RuntimeException format(final String format, String... arguments) {
        return new RuntimeException(String.format(format, (Object[]) arguments));
    }
}
