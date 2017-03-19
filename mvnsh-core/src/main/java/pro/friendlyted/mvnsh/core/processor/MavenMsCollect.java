package pro.friendlyted.mvnsh.core.processor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import pro.friendlyted.mvnsh.core.ScriptPreparer;
import pro.friendlyted.mvnsh.core.tools.ArtifactTools;
import pro.friendlyted.mvnsh.core.tools.DownloadTools;
import pro.friendlyted.mvnsh.core.api.MsCollect;
import pro.friendlyted.mvnsh.core.api.MsException;

/**
 *
 * @author frekade
 */
public class MavenMsCollect implements MsCollect {

    private List<RemoteRepository> remoteRepos;
    private RepositorySystem repoSystem;
    private RepositorySystemSession repoSession;
    private File workdir;
    private boolean fullPath = true;

    @Override
    public void collect(String artifact) throws MsException {
        final ScriptPreparer preparer = new ScriptPreparer(workdir);
        preparer.setFullPath(fullPath);

        final List<File> scripts = Collections.singletonList(artifact).stream()
                .map(ArtifactTools::parseArtifact)
                .map(a -> DownloadTools.getRemoteArtifact(a, remoteRepos, repoSystem, repoSession))
                .map(a -> preparer.prepareScript(a, remoteRepos, repoSystem, repoSession))
                .collect(Collectors.toList());

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (File script : scripts) {
            try {
                baos.write(script.getAbsolutePath().getBytes(Charset.forName("utf-8")));
            } catch (IOException ex) {
                throw new MsException("Cannot print name of file " + script, ex);
            }
            baos.write('\n');
        }

        final File start = new File(workdir, "start." + (isWindows() ? "bat" : "sh"));

        try {
            Files.write(start.toPath(), baos.toByteArray());
        } catch (IOException ex) {
            throw new MsException("Cannot create start script " + start, ex);
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    @Override
    public void setRemoteRepos(List<RemoteRepository> remoteRepos) {
        this.remoteRepos = remoteRepos;
    }

    @Override
    public void setRepoSystem(RepositorySystem repoSystem) {
        this.repoSystem = repoSystem;
    }

    @Override
    public void setRepoSession(RepositorySystemSession repoSession) {
        this.repoSession = repoSession;
    }

    @Override
    public void setWorkdir(File workdir) {
        this.workdir = workdir;
    }

    @Override
    public void setFullPath(boolean fullPath) {
        this.fullPath = fullPath;
    }

}
