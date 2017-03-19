package pro.friendlyted.mvnsh.plugin.mojo;

import org.apache.maven.RepositoryUtils;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import pro.friendlyted.mvnsh.core.api.MsDeploy;
import pro.friendlyted.mvnsh.core.api.MsFactory;
import pro.friendlyted.mvnsh.core.api.MsUpload;


/**
 *
 * @author Fedor
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

    @Component
    private ArtifactRepository artifactRepository;
    
    @Override
    protected MsUpload createUploadProcessor() {
        final MsDeploy deploy = MsFactory.MAVEN.deploy();
        deploy.setArtifactRepository(RepositoryUtils.toRepo(artifactRepository));
        return deploy;
    }

}
