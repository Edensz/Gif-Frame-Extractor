package destiny;

import javax.swing.*;
import java.io.File;

public class DestinyJButtonUtils {

    public static void changeDestiny(JLabel folderLabel, File file) {
        folderLabel.setText(file.getAbsolutePath());
        folderLabel.putClientProperty("selectedFolder", file);
    }

}
