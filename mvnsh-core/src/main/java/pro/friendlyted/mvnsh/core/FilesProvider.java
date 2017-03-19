package pro.friendlyted.mvnsh.core;

import java.io.File;

/**
 *
 * @author Fedor
 */
public interface FilesProvider {

    public File getBasedir();

    public String[] getIncludedFiles();
}
