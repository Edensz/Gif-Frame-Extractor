
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ExtractFramesJButtonListener implements ActionListener {
    private final JTextField gifPathField;
    private final JFrame frame;

    public ExtractFramesJButtonListener(JTextField gifPathField, JFrame frame) {
        this.gifPathField = gifPathField;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String gifPath = this.gifPathField.getText();

        File gifFile  = new File(gifPath);

        if (!gifFile.exists()) {
            JOptionPane.showMessageDialog(this.frame, "Invalid file route! Error: " + JOptionPane.ERROR_MESSAGE);
            return;
        }

        File selectedFolder = (File) GifFrameExtractor.folderLabel.getClientProperty("selectedFolder");
        if (selectedFolder == null) {
            JOptionPane.showMessageDialog(this.frame, "Please select a destination folder.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            GifFrameExtractor.extractFrames(gifFile, selectedFolder);
            JOptionPane.showMessageDialog(frame, "The frames have been extracted!", "Successful: ", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(frame, "An internal error has occurred", "Error: ", JOptionPane.ERROR_MESSAGE);
            exception.fillInStackTrace();
        }
    }

}
