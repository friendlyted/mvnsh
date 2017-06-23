package pro.friendlyted.mvnsh.plugin.mojo;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import pro.friendlyted.mvnsh.core.MavenMsFactory;
import pro.friendlyted.mvnsh.core.api.MsDeploy;

/**
 *
 * @author Fedor Resnyanskiy
 */
@Mojo(
        name = "deploy",
        aggregator = false,
        defaultPhase = LifecyclePhase.DEPLOY,
        executionStrategy = "always",
        requiresOnline = false,
        requiresProject = false,
        instantiationStrategy = InstantiationStrategy.PER_LOOKUP
)
public class DeployMojo extends AbstractUploadMojo {

    @Parameter(defaultValue = "${project.distributionManagementArtifactRepository}")
    private ArtifactRepository distribRepository;

    @Override
    protected MsDeploy createUploadProcessor() {

        final MsDeploy deploy = MavenMsFactory.getInstance().deploy();
        deploy.setArtifactRepository(RepoConverter.toRepo(distribRepository));
        return deploy;
    }

}
