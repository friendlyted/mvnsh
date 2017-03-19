package pro.friendlyted.mvnsh.core;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.RemoteRepository;
import pro.friendlyted.mvnsh.core.tools.ArrayTools;
import pro.friendlyted.mvnsh.core.tools.ArtifactTools;
import pro.friendlyted.mvnsh.core.tools.DownloadTools;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class ScriptPreparer {

    public final static String DIRECTIRVE_PREFIX = "@";
    public final static String MVNSH_DIRECTIVE = DIRECTIRVE_PREFIX + "mvnsh ";

    private File localDir;
    private boolean fullPath = true;

    public ScriptPreparer() {
    }

    public ScriptPreparer(File localDir) {
        this.localDir = localDir;
    }

    public File getLocalDir() {
        return localDir;
    }

    public void setLocalDir(File localDir) {
        this.localDir = localDir;
    }

    public boolean isFullPath() {
        return fullPath;
    }

    public void setFullPath(boolean fullPath) {
        this.fullPath = fullPath;
    }

    public File prepareScript(
            final Artifact artifact,
            final List<RemoteRepository> remoteRepos,
            final RepositorySystem repoSystem,
            final RepositorySystemSession repoSession
    ) {
        try {
            final Charset charset = Charset.forName("utf-8");
            final Path scriptPath = artifact.getFile().toPath();
            final StringBuilder scriptContent = new StringBuilder(new String(Files.readAllBytes(scriptPath), charset));

            final DirectiveProcessor dp = new DirectiveProcessor();
            dp.setDirective(MVNSH_DIRECTIVE);
            dp.setDirectiveArgumentsProcessor((arguments) -> {
                ArrayTools.replaceInIndex(arguments, 0, string -> {
                    final Artifact parsed = ArtifactTools.parseArtifact(string);
                    final Artifact remoteArtifact = DownloadTools.getRemoteArtifact(parsed, remoteRepos, repoSystem, repoSession);
                    final ScriptPreparer preparer = new ScriptPreparer(localDir);
                    preparer.setFullPath(fullPath);
                    final File file = preparer.prepareScript(remoteArtifact, remoteRepos, repoSystem, repoSession);
                    return file.getAbsolutePath();
                });
            });

            final StringBuilder preparedContent = dp.process(scriptContent);
            final File destination;
            if (fullPath) {
                destination = new File(localDir, ArtifactTools.artifactToFilename(artifact));
            } else {
                destination = new File(localDir, ArtifactTools.artifactToShortFilename(artifact));
            }
            destination.getParentFile().mkdirs();
            final Path path = Files.write(destination.toPath(), preparedContent.toString().getBytes(charset));
            System.out.println("File created: " + path);
            return destination;
        } catch (IOException ex) {
            throw new RuntimeException("Cannot prepare script '" + artifact + "'", ex);
        }
    }
}
