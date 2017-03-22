package pro.friendlyted.mvnsh.plugin.mojo;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import pro.friendlyted.mvnsh.core.MavenMsFactory;
import pro.friendlyted.mvnsh.core.tools.ExceptionTools;
import static pro.friendlyted.mvnsh.plugin.mojo.MvnshPluginConsts.*;
import pro.friendlyted.mvnsh.core.api.MsCollect;

/**
 * Executes script from Maven Repository
 *
 * @author Fedor Resnyanskiy
 */
@Mojo(
        name = "collect",
        aggregator = false,
        defaultPhase = LifecyclePhase.COMPILE,
        executionStrategy = "always",
        requiresOnline = false,
        requiresProject = false,
        instantiationStrategy = InstantiationStrategy.PER_LOOKUP
)
public class CollectMojo extends AbstractMojo {

    @Parameter(property = ARTIFACT_PARAMETER)
    private String artifact;

    @Parameter(property = WORKDIR_PARAMETER, defaultValue = "${user.home}/.mvnsh")
    private File workdir;

    @Parameter(property = FULLPATH_PARAMETER, defaultValue = "true")
    private boolean fullPath;

    @Parameter(property = START_SCRIPT_PARAMETER)
    private String startScript;

    @Parameter(defaultValue = "${project.remotePluginRepositories}")
    private List<RemoteRepository> remoteRepos;

    @Component
    private RepositorySystem repoSystem;

    @Parameter(defaultValue = "${repositorySystemSession}", readonly = true)
    private RepositorySystemSession repoSession;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (artifact == null || artifact.isEmpty()) {
            throw ExceptionTools.parameterNotSpecified(ARTIFACT_PARAMETER);
        }

        try {
            final MsCollect getter = MavenMsFactory.getInstance().get();

            getter.setStartScript(startScript);
            getter.setWorkdir(workdir);
            getter.setFullPath(fullPath);
            getter.setRemoteRepos(remoteRepos);
            getter.setRepoSession(repoSession);
            getter.setRepoSystem(repoSystem);

            getter.collect(artifact);
        } catch (Exception ex) {
            throw new MojoExecutionException("", ex);
        }
    }

}
