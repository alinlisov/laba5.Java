package lab5;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
public class EncryptFilterWriter extends FilterWriter {
    private final int keyShift;
    public EncryptFilterWriter(Writer out, char keyChar) {
        super(out);
        this.keyShift = (int) keyChar; }
    @Override
    public void write(int c) throws IOException {
        // Якщо це кінець файлу (-1), не шифруємо
        if (c == -1) {
            super.write(c);
            return;  }
        super.write(c + keyShift);
    }
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(cbuf[i]); }
    }
    @Override
    public void write(String str, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(str.charAt(i)); }
    }
}