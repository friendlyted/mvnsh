package pro.friendlyted.mvnsh.core.api;

import java.util.List;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;

/**
 *
 * @author Fedor Resnyanskiy
 */
public interface MsUpload {

    void setArtifactId(String artifactId);

    void setGroupId(String groupId);

    void setRepoSession(RepositorySystemSession repoSession);

    void setRepoSystem(RepositorySystem repoSystem);

    void setScriptsList(List<FilesProvider> scriptsList);

    void setVersion(String version);

    void upload() throws MsException;
}
