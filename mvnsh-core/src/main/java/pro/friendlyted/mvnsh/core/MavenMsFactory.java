package pro.friendlyted.mvnsh.core;

import pro.friendlyted.mvnsh.core.api.MsCollect;
import pro.friendlyted.mvnsh.core.api.MsDeploy;
import pro.friendlyted.mvnsh.core.api.MsDownload;
import pro.friendlyted.mvnsh.core.api.MsExec;
import pro.friendlyted.mvnsh.core.api.MsFactory;
import pro.friendlyted.mvnsh.core.api.MsInstall;

/**
 *
 * @author frekade
 */
public class MavenMsFactory implements MsFactory {

    private static final MavenMsFactory INSTANCE = new MavenMsFactory();

    private MavenMsFactory() {
    }

    public static MavenMsFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public MsDeploy deploy() {
        return new pro.friendlyted.mvnsh.core.processor.MavenMsDeploy();
    }

    @Override
    public MsInstall install() {
        return new pro.friendlyted.mvnsh.core.processor.MavenMsInstall();
    }

    @Override
    public MsExec exec() {
        return new pro.friendlyted.mvnsh.core.processor.MavenMsExec();
    }

    @Override
    public MsCollect get() {
        return new pro.friendlyted.mvnsh.core.processor.MavenMsCollect();
    }

    @Override
    public MsDownload download() {
        return new pro.friendlyted.mvnsh.core.processor.MavenMsDownload();
    }
}
