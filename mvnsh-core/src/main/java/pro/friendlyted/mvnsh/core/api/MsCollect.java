package pro.friendlyted.mvnsh.core.api;

/**
 *
 * @author frekade
 */
public interface MsCollect extends IHasRemotes, IHasWorkdir {

    void collect(String artifact) throws MsException;
}
