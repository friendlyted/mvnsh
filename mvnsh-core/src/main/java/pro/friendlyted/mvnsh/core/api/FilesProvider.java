package pro.friendlyted.mvnsh.core.api;

import java.io.File;

/**
 *
 * @author Fedor Resnyanskiy
 */
public interface FilesProvider {

    public File getBasedir();

    public String[] getIncludedFiles();
}
