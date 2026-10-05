package lab5;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
public class DecryptFilterReader extends FilterReader {
    private final int keyShift;
    public DecryptFilterReader(Reader in, char keyChar) {
        super(in);
        this.keyShift = (int) keyChar; }
    @Override
    public int read() throws IOException {
        int c = super.read();
        return (c == -1) ? -1 : (c - keyShift); }
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int numRead = super.read(cbuf, off, len);
        if (numRead != -1) {
            for (int i = off; i < off + numRead; i++) {
                cbuf[i] = (char) (cbuf[i] - keyShift);}
        }
        return numRead;
    }
}