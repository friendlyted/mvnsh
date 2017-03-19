package pro.friendlyted.mvnsh.core.processor;

import java.util.Collections;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.installation.InstallRequest;
import org.eclipse.aether.installation.InstallationException;
import pro.friendlyted.mvnsh.core.api.MsInstall;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class MavenMsInstall extends AbstractMavenUploadProcessor implements MsInstall {

    @Override
    public void uploadArtifact(DefaultArtifact artifact) {
        final InstallRequest request = new InstallRequest();
        request.setArtifacts(Collections.singletonList(artifact));

        try {
            getRepoSystem().install(getRepoSession(), request);
        } catch (InstallationException ex) {
            throw new RuntimeException("Cannot install artifact '" + artifact + "'", ex);
        }
    }

}
