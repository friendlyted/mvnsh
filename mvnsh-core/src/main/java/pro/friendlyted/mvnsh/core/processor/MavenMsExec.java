package pro.friendlyted.mvnsh.core.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import pro.friendlyted.mvnsh.core.ScriptPreparer;
import pro.friendlyted.mvnsh.core.api.MsExec;
import pro.friendlyted.mvnsh.core.api.MsExecutor;
import pro.friendlyted.mvnsh.core.tools.ArtifactTools;
import pro.friendlyted.mvnsh.core.tools.DownloadTools;
import pro.friendlyted.mvnsh.core.tools.ScriptTools;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class MavenMsExec implements MsExec {

    private List<RemoteRepository> remoteRepos;
    private RepositorySystem repoSystem;
    private RepositorySystemSession repoSession;
    private File workdir;
    private boolean fullPath = true;
    private MsExecutor executor;

    @Override
    public void execute(String artifact) {
        final ScriptPreparer preparer = new ScriptPreparer(workdir);
        preparer.setFullPath(fullPath);

        Collections.singletonList(artifact).stream()
                .map(ArtifactTools::parseArtifact)
                .map(a -> DownloadTools.getRemoteArtifact(a, remoteRepos, repoSystem, repoSession))
                .map(a -> preparer.prepareScript(a, remoteRepos, repoSystem, repoSession))
                .forEach(this::executeScript);
    }

    private void executeScript(File script) {
        if (executor == null) {
            script.setExecutable(true);
        }
        final String[] command = prepareCommand(executor, script.getAbsolutePath());
        System.out.println("command: " + arrayToString(command));
        ScriptTools.executeScript(command, executor != null && executor.isIgnoreInput());
    }

    @Override
    public void setRemoteRepos(List<RemoteRepository> remoteRepos) {
        this.remoteRepos = remoteRepos;
    }

    @Override
    public void setRepoSystem(RepositorySystem repoSystem) {
        this.repoSystem = repoSystem;
    }

    @Override
    public void setRepoSession(RepositorySystemSession repoSession) {
        this.repoSession = repoSession;
    }

    @Override
    public void setWorkdir(File workdir) {
        this.workdir = workdir;
    }

    @Override
    public void setFullPath(boolean fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public void setExecutor(MsExecutor executor) {
        this.executor = executor;
    }

    private static <T> String arrayToString(final T[] array) {
        return arrayToString(array, Object::toString);
    }

    private static <T> String arrayToString(final T[] array, final Function<T, String> itemConverter) {
        return Arrays.stream(array).map(itemConverter).collect(
                StringBuilder::new,
                (b, s) -> b.append(s).append(";"),
                (b, s) -> b.append(s).append(";")
        ).toString();
    }

    private static String[] prepareCommand(MsExecutor executor, String script) {
        if (executor == null) {
            return new String[]{script};
        }
        final List<String> result = new ArrayList<>();

        if (executor.getProgram() != null) {
            result.add(executor.getProgram());
        }

        if (executor.getParameters() != null) {
            result.addAll(executor.getParameters());
        }

        final String prefix = executor.getScriptPrefix();
        result.add((prefix == null ? "" : prefix) + script);

        return result.toArray(new String[0]);
    }

}
