package pro.friendlyted.mvnsh.plugin.mojo;

import java.io.File;
import java.util.List;
import java.util.Map;

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
import pro.friendlyted.mvnsh.core.api.MsExec;
import pro.friendlyted.mvnsh.core.api.MsExecutor;
import pro.friendlyted.mvnsh.core.tools.ExceptionTools;
import static pro.friendlyted.mvnsh.plugin.mojo.MvnshPluginConsts.*;

/**
 * Executes script from Maven Repository
 *
 * @author Fedor Resnyanskiy
 */
@Mojo(
        name = "exec",
        aggregator = false,
        defaultPhase = LifecyclePhase.COMPILE,
        executionStrategy = "always",
        requiresOnline = false,
        requiresProject = false,
        instantiationStrategy = InstantiationStrategy.PER_LOOKUP
)
public class ExecMojo extends AbstractMojo {

    @Parameter(property = ARTIFACT_PARAMETER)
    private String artifact;
    
    @Parameter(property = IGNORE_INPNUT, defaultValue = "false")
    private boolean ignoreInput;

    @Parameter(property = WORKDIR_PARAMETER, defaultValue = "${user.home}/.mvnsh")
    private File workdir;

    @Parameter(property = FULLPATH_PARAMETER, defaultValue = "true")
    private boolean fullPath;
    
    @Parameter(property = SCRIPT_PREFIX)
    private String scriptPrefix;

    @Parameter(property = EXECUTOR_PROGRAM)
    private String execProgram;

    @Parameter(property = EXECUTOR_PARAMETERS)
    private List<String> execParameters;

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
            final MsExec exec = MavenMsFactory.getInstance().exec();
            exec.setWorkdir(workdir);
            exec.setRemoteRepos(remoteRepos);
            exec.setRepoSession(repoSession);
            exec.setRepoSystem(repoSystem);
            exec.setFullPath(fullPath);
            exec.setExecutor(createExecutor());
            exec.execute(artifact);
        } catch (Exception ex) {
            throw new MojoExecutionException("", ex);
        }
    }

    private MsExecutor createExecutor() {
        final MsExecutor result = new MsExecutor();

        result.setParameters(execParameters);
        result.setProgram(execProgram);
        result.setScriptPrefix(scriptPrefix);
        result.setIgnoreInput(ignoreInput);

        return result;
    }

}
