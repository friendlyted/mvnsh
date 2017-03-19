package pro.friendlyted.mvnsh.plugin.mojo;

import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import pro.friendlyted.mvnsh.core.api.MsFactory;
import pro.friendlyted.mvnsh.core.api.MsUpload;

/**
 *
 * @author Fedor
 */
@Mojo(
        name = "install",
        aggregator = false,
        defaultPhase = LifecyclePhase.INSTALL,
        executionStrategy = "always",
        requiresOnline = false,
        requiresProject = false,
        instantiationStrategy = InstantiationStrategy.PER_LOOKUP
)
public class InstallMojo extends AbstractUploadMojo {

    @Override
    protected MsUpload createUploadProcessor() {
        return MsFactory.MAVEN.install();
    }

}
