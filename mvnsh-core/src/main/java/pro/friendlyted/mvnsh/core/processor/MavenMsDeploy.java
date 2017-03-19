package pro.friendlyted.mvnsh.core.processor;

import java.util.Collections;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.deployment.DeployRequest;
import org.eclipse.aether.deployment.DeploymentException;
import org.eclipse.aether.repository.RemoteRepository;
import pro.friendlyted.mvnsh.core.api.MsDeploy;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class MavenMsDeploy extends AbstractMavenUploadProcessor implements MsDeploy {

    private RemoteRepository artifactRepository;

    @Override
    public void uploadArtifact(DefaultArtifact artifact) {
        final DeployRequest deployRequest = new DeployRequest();
        deployRequest.setArtifacts(Collections.singletonList(artifact));

        try {
            deployRequest.setRepository(getArtifactRepository());
            getRepoSystem().deploy(getRepoSession(), deployRequest);
        } catch (DeploymentException ex) {
            throw new RuntimeException("Cannot deploy artifact '" + artifact + "'", ex);
        }
    }

    public RemoteRepository getArtifactRepository() {
        return artifactRepository;
    }

    @Override
    public void setArtifactRepository(RemoteRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

}
