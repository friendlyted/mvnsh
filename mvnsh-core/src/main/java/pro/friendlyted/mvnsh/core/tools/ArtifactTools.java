package pro.friendlyted.mvnsh.core.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import static pro.friendlyted.mvnsh.core.api.MvnshConsts.*;

/**
 *
 * @author Fedor
 */
public class ArtifactTools {

    public final static Pattern ARTIFACT_PATTERN = Pattern.compile(String.format("\\s*([^%1$s]+)%1$s([^%1$s]+)%1$s([^%1$s]+)%1$s?([^%1$s]+)?%1$s?([^%1$s]+)?\\s*", PART_DELIMITER));

    private ArtifactTools() {
    }

    public static Artifact parseArtifact(final String string) throws RuntimeException {
        final Matcher matcher = ARTIFACT_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw ExceptionTools.wrong_format(string);
        }
        return new DefaultArtifact(
                matcher.group(1),
                matcher.group(2),
                matcher.group(5),
                matcher.group(4),
                matcher.group(3)
        );
    }

    public static String artifactToFilename(final Artifact artifact) {
        return artifact.getGroupId() + FILE_SEPARATOR
                + artifact.getArtifactId() + FILE_SEPARATOR
                + artifact.getVersion() + FILE_SEPARATOR
                + artifactToShortFilename(artifact);
    }

    public static String artifactToShortFilename(final Artifact artifact) {
        return artifact.getClassifier().replace(FOLDER_DELIMITER, FILE_SEPARATOR) + "." + artifact.getExtension();
    }
}
