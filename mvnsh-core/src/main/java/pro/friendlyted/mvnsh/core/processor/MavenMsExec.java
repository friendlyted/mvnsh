package pro.friendlyted.mvnsh.core.processor;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import pro.friendlyted.mvnsh.core.ScriptPreparer;
import pro.friendlyted.mvnsh.core.api.MsExec;
import pro.friendlyted.mvnsh.core.tools.ArtifactTools;
import pro.friendlyted.mvnsh.core.tools.DownloadTools;
import pro.friendlyted.mvnsh.core.tools.ScriptTools;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class MavenMsExec implements MsExec {

    private List<RemoteRepository> remoteRepos;
    private RepositorySystem repoSystem;
    private RepositorySystemSession repoSession;
    private File workdir;
    private boolean fullPath = true;

    @Override
    public void execute(String artifact) {
        final ScriptPreparer preparer = new ScriptPreparer(workdir);
        preparer.setFullPath(fullPath);

        Collections.singletonList(artifact).stream()
                .map(ArtifactTools::parseArtifact)
                .map(a -> DownloadTools.getRemoteArtifact(a, remoteRepos, repoSystem, repoSession))
                .map(a -> preparer.prepareScript(a, remoteRepos, repoSystem, repoSession))
                .forEach(ScriptTools::executeScript);
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
