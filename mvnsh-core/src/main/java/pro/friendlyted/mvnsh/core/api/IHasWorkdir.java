package pro.friendlyted.mvnsh.core.api;

import java.io.File;

/**
 *
 * @author frekade
 */
public interface IHasWorkdir {

    void setWorkdir(File workdir);

    void setFullPath(boolean fullPath);
}
