package pro.friendlyted.mvnsh.core.api;

/**
 *
 * @author Fedor Resnyanskiy
 */
public interface MsFactory {

    MsDeploy deploy();

    MsInstall install();

    MsExec exec();

    MsCollect get();

    MsDownload download();

}
