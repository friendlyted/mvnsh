package pro.friendlyted.mvnsh.core.tools;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class ScriptTools {

    private ScriptTools() {
    }

    public static void executeScript(final String[] command, boolean ignoreInput) {
        try {
            final Process p = Runtime.getRuntime().exec(command);
            new Thread(new StreamProxy(p.getInputStream(), System.out)).start();
            new Thread(new StreamProxy(p.getErrorStream(), System.err)).start();
            if (!ignoreInput) {
                new Thread(new StreamProxy(System.in, p.getOutputStream())).start();
            } else {
                p.getOutputStream().close();
            }

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
