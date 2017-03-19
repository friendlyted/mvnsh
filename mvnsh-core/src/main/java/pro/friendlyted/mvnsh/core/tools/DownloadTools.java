package pro.friendlyted.mvnsh.core.tools;

import java.util.List;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

/**
 *
 * @author Fedor
 */
public class DownloadTools {

    public static Artifact getRemoteArtifact(
            final Artifact localArtifact,
            List<RemoteRepository> remoteRepos,
            RepositorySystem repoSystem,
            RepositorySystemSession repoSession
    ) {
        final ArtifactRequest request = new ArtifactRequest();
        request.setArtifact(localArtifact);
        request.setRepositories(remoteRepos);

        try {
            final ArtifactResult result = repoSystem.resolveArtifact(repoSession, request);
            return result.getArtifact();
        } catch (ArtifactResolutionException e) {
            throw new RuntimeException("Cannot resolve artifact '" + localArtifact.toString() + "'", e);
        }
    }

}
