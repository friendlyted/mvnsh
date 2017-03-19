package pro.friendlyted.mvnsh.core.api;

import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;

/**
 *
 * @author Fedor Resnyanskiy
 */
public interface MsDeploy extends MsUpload {

    void setArtifactRepository(RemoteRepository artifactRepository);

    void uploadArtifact(DefaultArtifact artifact) throws MsException;
    
}
