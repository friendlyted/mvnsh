package pro.friendlyted.mvnsh.core.api;

import java.util.List;

/**
 * Parameters for script execution with program execution.
 *
 * @author frekade
 */
public class MsExecutor {

    /**
     * This string will be prepended to script.
     */
    private String scriptPrefix;
    /**
     * This program will be called. Script provided as last parameter.
     */
    private String program;
    /**
     * Additional parameters for program;
     */
    private List<String> parameters;

    public String getProgram() {
        return program;
    }

    public String getScriptPrefix() {
        return scriptPrefix;
    }

    public void setScriptPrefix(String scriptPrefix) {
        this.scriptPrefix = scriptPrefix;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

}
