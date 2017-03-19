package pro.friendlyted.mvnsh.core.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.aether.artifact.Artifact;

/**
 *
 * @author Fedor
 */
public class LocalFileTools {

    private final static Map<Artifact, File> LOCAL_FILES = new HashMap<>();

    private File localDirectory;

    private LocalFileTools() {
    }

    public File getLocalDirectory() {
        return localDirectory;
    }

    public void setLocalDirectory(File localDirectory) {
        this.localDirectory = localDirectory;
    }

    public File localFile(final Artifact artifact) {
        if (LOCAL_FILES.containsKey(artifact)) {
            return LOCAL_FILES.get(artifact);
        }
        try {
            final File destination = new File(localDirectory, artifact.toString());
            destination.mkdirs();
            Files.copy(
                    artifact.getFile().toPath(),
                    destination.toPath(),
                    REPLACE_EXISTING, COPY_ATTRIBUTES
            );
            LOCAL_FILES.put(artifact, destination);
            return destination;
        } catch (IOException ex) {
            throw new RuntimeException("cannot get artifact " + artifact, ex);
        }
    }

}
