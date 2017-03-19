package pro.friendlyted.mvnsh.core.api;

import pro.friendlyted.mvnsh.core.processor.MavenMsDeploy;
import pro.friendlyted.mvnsh.core.processor.MavenMsDownload;
import pro.friendlyted.mvnsh.core.processor.MavenMsExec;
import pro.friendlyted.mvnsh.core.processor.MavenMsCollect;
import pro.friendlyted.mvnsh.core.processor.MavenMsInstall;

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

    public static MsFactory MAVEN = new MsFactory() {
        @Override
        public MsDeploy deploy() {
            return new MavenMsDeploy();
        }

        @Override
        public MsInstall install() {
            return new MavenMsInstall();
        }

        @Override
        public MsExec exec() {
            return new MavenMsExec();
        }

        @Override
        public MsCollect get() {
            return new MavenMsCollect();
        }

        @Override
        public MsDownload download() {
            return new MavenMsDownload();
        }
    };
}
