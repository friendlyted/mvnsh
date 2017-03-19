package pro.friendlyted.mvnsh.core.api;

import org.eclipse.aether.artifact.DefaultArtifact;

/**
 *
 * @author Fedor Resnyanskiy
 */
public interface MsInstall extends MsUpload {

    void uploadArtifact(DefaultArtifact artifact);

}
