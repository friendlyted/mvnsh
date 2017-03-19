package pro.friendlyted.mvnsh.core;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Consumer;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class DirectiveProcessor {

    private String directive;
    private Consumer<String[]> directiveArgumentsProcessor;

    public String getDirective() {
        return directive;
    }

    public void setDirective(String directive) {
        this.directive = directive;
    }

    public Consumer<String[]> getDirectiveArgumentsProcessor() {
        return directiveArgumentsProcessor;
    }

    public void setDirectiveArgumentsProcessor(Consumer<String[]> directiveArgumentsProcessor) {
        this.directiveArgumentsProcessor = directiveArgumentsProcessor;
    }

    public StringBuilder process(StringBuilder input) {
        final StringBuilder output = new StringBuilder(input);
        int cursor = 0;
        while ((cursor = output.indexOf(directive, cursor)) != -1) {
            //Find full command with multiline features in bash (\) and bat (^)
            final String directive_content
                    = output.substring(cursor + directive.length()).replaceAll("(.*){1}?((?<![\\^\\\\])\\r?\\n).*", "$1");
            final String arguments_string
                    = directive_content.replaceAll("[\\^\\\\][\\r\\n]", "");
            final String[] arguments = arguments_string.split("\\s");

            directiveArgumentsProcessor.accept(arguments);
            final StringJoiner joiner = new StringJoiner(" ");
            Arrays.stream(arguments).forEach(s -> joiner.add(s));
            output.replace(cursor, cursor + directive.length() + directive_content.length(), joiner.toString());
        }
        return output;
    }
}
