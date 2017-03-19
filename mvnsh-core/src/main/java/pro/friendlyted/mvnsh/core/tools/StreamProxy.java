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
            final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = in.readLine()) != null) {
                outputStream.write(line.getBytes());
                outputStream.write('\n');
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
