package pro.friendlyted.mvnsh.plugin.mojo;

import java.util.ArrayList;
import java.util.List;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RepositoryPolicy;
import org.eclipse.aether.util.repository.AuthenticationBuilder;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class RepoConverter {

    private RepoConverter() {
    }

    public static List<RemoteRepository> toRepos(List<ArtifactRepository> repos) {
        if (repos == null) {
            return null;
        }

        List<RemoteRepository> results = new ArrayList<>(repos.size());
        for (ArtifactRepository repo : repos) {
            results.add(toRepo(repo));
        }
        return results;
    }

    public static RemoteRepository toRepo(ArtifactRepository repo) {
        RemoteRepository result = null;
        if (repo != null) {
            RemoteRepository.Builder builder
                    = new RemoteRepository.Builder(repo.getId(), getLayout(repo), repo.getUrl());
            builder.setSnapshotPolicy(toPolicy(repo.getSnapshots()));
            builder.setReleasePolicy(toPolicy(repo.getReleases()));
            builder.setAuthentication(toAuthentication(repo.getAuthentication()));
            builder.setProxy(toProxy(repo.getProxy()));
            builder.setMirroredRepositories(toRepos(repo.getMirroredRepositories()));
            result = builder.build();
        }
        return result;
    }

    public static String getLayout(ArtifactRepository repo) {
        try {
            return repo.getLayout().getId();
        } catch (LinkageError e) {
            /*
             * NOTE: getId() was added in 3.x and is as such not implemented by plugins compiled against 2.x APIs.
             */
            String className = repo.getLayout().getClass().getSimpleName();
            if (className.endsWith("RepositoryLayout")) {
                String layout = className.substring(0, className.length() - "RepositoryLayout".length());
                if (layout.length() > 0) {
                    layout = Character.toLowerCase(layout.charAt(0)) + layout.substring(1);
                    return layout;
                }
            }
            return "";
        }
    }

    private static RepositoryPolicy toPolicy(ArtifactRepositoryPolicy policy) {
        RepositoryPolicy result = null;
        if (policy != null) {
            result = new RepositoryPolicy(policy.isEnabled(), policy.getUpdatePolicy(), policy.getChecksumPolicy());
        }
        return result;
    }

    private static Authentication toAuthentication(org.apache.maven.artifact.repository.Authentication auth) {
        Authentication result = null;
        if (auth != null) {
            AuthenticationBuilder authBuilder = new AuthenticationBuilder();
            authBuilder.addUsername(auth.getUsername()).addPassword(auth.getPassword());
            authBuilder.addPrivateKey(auth.getPrivateKey(), auth.getPassphrase());
            result = authBuilder.build();
        }
        return result;
    }

    private static Proxy toProxy(org.apache.maven.repository.Proxy proxy) {
        Proxy result = null;
        if (proxy != null) {
            AuthenticationBuilder authBuilder = new AuthenticationBuilder();
            authBuilder.addUsername(proxy.getUserName()).addPassword(proxy.getPassword());
            result = new Proxy(proxy.getProtocol(), proxy.getHost(), proxy.getPort(), authBuilder.build());
        }
        return result;
    }
}
