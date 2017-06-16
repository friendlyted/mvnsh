package pro.friendlyted.mvnsh.core.api;

/**
 *
 * @author frekade
 */
public interface MsExec extends IHasRemotes, IHasWorkdir, IHasExecutor {

    void execute(String artifact);

}
