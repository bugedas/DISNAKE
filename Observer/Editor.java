package Observer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class Editor {
    public ScoreObserver events;
    private File file;
    private Path resultsFIle;

    public Editor() {
        this.events = new ScoreObserver("open", "save", "update");
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        this.resultsFIle = Path.of(filePath);
        events.notify("open", file);
    }

    public void updateFile(String content) throws IOException {
        Files.writeString(resultsFIle, content);
        events.notify("update", file);
    }

    public void saveFile() throws Exception {
        if (this.file != null) {
            events.notify("save", file);
        } else {
            throw new Exception("Please open a file first.");
        }
    }
}
