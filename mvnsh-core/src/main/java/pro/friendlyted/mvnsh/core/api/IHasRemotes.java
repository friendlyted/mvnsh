package pro.friendlyted.mvnsh.core.api;

import java.util.List;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;

/**
 *
 * @author frekade
 */
public interface IHasRemotes {

    void setRemoteRepos(List<RemoteRepository> remoteRepos);

    void setRepoSession(RepositorySystemSession repoSession);

    void setRepoSystem(RepositorySystem repoSystem);
}
