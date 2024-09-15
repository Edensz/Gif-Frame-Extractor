import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChangeDestinyJButtonListener implements ActionListener {

    private final JLabel folderLabel;
    private final JFrame frame;

    public ChangeDestinyJButtonListener(JLabel folderLabel, JFrame frame) {
        this.folderLabel = folderLabel;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = folderChooser.showOpenDialog(this.frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder  = folderChooser.getSelectedFile();
            GifFrameExtractor.changeFramesFolderDestiny(this.folderLabel, selectedFolder);
        }
    }

}
