package pro.friendlyted.mvnsh.plugin.mojo;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import pro.friendlyted.mvnsh.core.api.FilesProvider;
import pro.friendlyted.mvnsh.core.api.MsException;
import pro.friendlyted.mvnsh.core.api.MsUpload;

/**
 *
 * @author Fedor Resnyanskiy
 */
public abstract class AbstractUploadMojo extends AbstractMojo {

    @Component
    private RepositorySystem repoSystem;

    @Parameter(defaultValue = "${repositorySystemSession}", readonly = true)
    private RepositorySystemSession repoSession;

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    private List<DirectoryScanner> scriptsList;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            if (scriptsList == null) {
                throw new MojoExecutionException("Scripts directory must be defined");
            }

            final MsUpload upload = createUploadProcessor();
            setupUploadProcessor(upload);
            upload.upload();
        } catch (MsException ex) {
            ex.printStackTrace();
            throw new MojoExecutionException("", ex);
        }
    }

    protected abstract MsUpload createUploadProcessor();

    protected void setupUploadProcessor(final MsUpload upload) {
        upload.setGroupId(project.getGroupId());
        upload.setArtifactId(project.getArtifactId());
        upload.setVersion(project.getVersion());
        upload.setRepoSession(repoSession);
        upload.setRepoSystem(repoSystem);
        upload.setScriptsList(
                scriptsList.stream()
                        .peek(i -> i.scan())
                        .map(i -> new FilesProvider() {
                    @Override
                    public File getBasedir() {
                        return i.getBasedir();
                    }

                    @Override
                    public String[] getIncludedFiles() {
                        return i.getIncludedFiles();
                    }
                })
                        .collect(Collectors.toList())
        );
    }

}
