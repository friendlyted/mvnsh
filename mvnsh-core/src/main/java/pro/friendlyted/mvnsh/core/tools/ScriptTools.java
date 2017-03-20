package pro.friendlyted.mvnsh.core.tools;

import java.io.File;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class ScriptTools {

    private ScriptTools() {
    }

    public static void executeScript(final File script) {
        script.setExecutable(true);
        try {
            final Process p = Runtime.getRuntime().exec(script.getAbsolutePath());
            new Thread(new StreamProxy(p.getInputStream(), System.out)).start();
            new Thread(new StreamProxy(p.getErrorStream(), System.err)).start();
            new Thread(new StreamProxy(System.in, p.getOutputStream())).start();

            try {
                p.waitFor();
            } catch (InterruptedException ex) {
                p.destroyForcibly();
                throw new RuntimeException("Interrupted", ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Script execution error", ex);
        }
    }

}
