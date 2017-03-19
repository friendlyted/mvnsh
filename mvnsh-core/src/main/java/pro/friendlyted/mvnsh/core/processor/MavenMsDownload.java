package pro.friendlyted.mvnsh.core.processor;

import java.util.Collections;
import java.util.List;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import pro.friendlyted.mvnsh.core.api.MsDownload;
import pro.friendlyted.mvnsh.core.tools.ArtifactTools;
import pro.friendlyted.mvnsh.core.tools.DownloadTools;

/**
 *
 * @author frekade
 */
public class MavenMsDownload implements MsDownload {

    private List<RemoteRepository> remoteRepos;
    private RepositorySystem repoSystem;
    private RepositorySystemSession repoSession;

    @Override
    public void download(String artifact) {
        Collections.singletonList(artifact).stream()
                .map(ArtifactTools::parseArtifact)
                .map(a -> DownloadTools.getRemoteArtifact(a, remoteRepos, repoSystem, repoSession));
    }

    @Override
    public void setRemoteRepos(List<RemoteRepository> remoteRepos) {
        this.remoteRepos = remoteRepos;
    }

    @Override
    public void setRepoSession(RepositorySystemSession repoSession) {
        this.repoSession = repoSession;
    }

    @Override
    public void setRepoSystem(RepositorySystem repoSystem) {
        this.repoSystem = repoSystem;
    }

}
