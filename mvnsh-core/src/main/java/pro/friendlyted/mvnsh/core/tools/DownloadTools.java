package pro.friendlyted.mvnsh.core.tools;

import java.util.List;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.VersionRequest;
import org.eclipse.aether.resolution.VersionResolutionException;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class DownloadTools {

    private DownloadTools() {
    }

    public static Artifact getRemoteArtifact(
            final Artifact artifact,
            List<RemoteRepository> remoteRepos,
            RepositorySystem repoSystem,
            RepositorySystemSession repoSession
    ) {

        final String version = getActualVersion(artifact, remoteRepos, repoSystem, repoSession);

        final ArtifactRequest request = new ArtifactRequest(
                new DefaultArtifact(
                        artifact.getGroupId(),
                        artifact.getArtifactId(),
                        artifact.getClassifier(),
                        artifact.getExtension(),
                        version),
                remoteRepos,
                null
        );

        try {
            final ArtifactResult result = repoSystem.resolveArtifact(repoSession, request);
            return result.getArtifact();
        } catch (ArtifactResolutionException e) {
            throw new RuntimeException("Cannot resolve artifact '" + artifact.toString() + "'", e);
        }
    }

    private static String getActualVersion(final Artifact artifact, List<RemoteRepository> remoteRepos, RepositorySystem repoSystem, RepositorySystemSession repoSession) throws RuntimeException {
        try {
            //Place classifier & extension in correct order
            final String artifactTail = artifact.getClassifier() + "." + artifact.getExtension();
            final VersionRequest vr = new VersionRequest(
                    new DefaultArtifact(
                            artifact.getGroupId(),
                            artifact.getArtifactId(),
                            artifactTail.replaceAll("^([^\\.]+).*", "$1"),
                            artifactTail.replaceAll("^[^\\.]+\\.", ""),
                            artifact.getVersion()),
                    remoteRepos,
                    null
            );

            return repoSystem.resolveVersion(repoSession, vr).getVersion();
        } catch (VersionResolutionException e) {
            throw new RuntimeException("Cannot resolve artifact version '" + artifact.toString() + "'", e);
        }
    }

}
