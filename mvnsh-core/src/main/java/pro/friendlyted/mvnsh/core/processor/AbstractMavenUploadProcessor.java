package pro.friendlyted.mvnsh.core.processor;

import pro.friendlyted.mvnsh.core.api.MsUpload;
import pro.friendlyted.mvnsh.core.api.MsException;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import pro.friendlyted.mvnsh.core.FilesProvider;
import static pro.friendlyted.mvnsh.core.api.MvnshConsts.*;

/**
 *
 * @author Fedor Resnyanskiy
 */
public abstract class AbstractMavenUploadProcessor implements MsUpload {

    private List<FilesProvider> scriptsList;
    private String groupId;
    private String artifactId;
    private String version;
    private RepositorySystem repoSystem;
    private RepositorySystemSession repoSession;

    public void upload() throws MsException {
        try {
            getScriptsList().stream().forEach(
                    list -> Arrays.stream(list.getIncludedFiles())
                            .forEach(file -> upload(list.getBasedir(), file))
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MsException("", ex);
        }
    }

    public void upload(File basedir, String filename) {
        final File script = new File(basedir, filename);

        final DefaultArtifact sArt = new DefaultArtifact(
                getGroupId(),
                getArtifactId(),
                filename.substring(0, filename.lastIndexOf(".")).replace(File.separator, FOLDER_DELIMITER),
                filename.substring(filename.lastIndexOf(".") + 1),
                getVersion(),
                null,
                script
        );

        uploadArtifact(sArt);
    }

    abstract public void uploadArtifact(DefaultArtifact artifact);

    public List<FilesProvider> getScriptsList() {
        return scriptsList;
    }

    @Override
    public void setScriptsList(List<FilesProvider> scriptsList) {
        this.scriptsList = scriptsList;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    public RepositorySystem getRepoSystem() {
        return repoSystem;
    }

    @Override
    public void setRepoSystem(RepositorySystem repoSystem) {
        this.repoSystem = repoSystem;
    }

    public RepositorySystemSession getRepoSession() {
        return repoSession;
    }

    @Override
    public void setRepoSession(RepositorySystemSession repoSession) {
        this.repoSession = repoSession;
    }

}
