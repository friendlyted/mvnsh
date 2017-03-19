package pro.friendlyted.mvnsh.core.api;

/**
 *
 * @author frekade
 */
public interface MsExec extends IHasRemotes, IHasWorkdir {

    void execute(String artifact);

}
