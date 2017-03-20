package pro.friendlyted.mvnsh.core.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author Fedor Resnyanskiy
 */
public class StreamProxy implements Runnable {

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public StreamProxy(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
