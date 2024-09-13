import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GifFrameExtractor {

    public static void main(String[] args) {
        JFrame frame = new JFrame("GIF Frame Extractor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
        frame.setLayout(new BorderLayout());
        frame.setLocation(500, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 3));

        JLabel routeLabel = new JLabel("Insert GIF file route below...");
        routeLabel.setHorizontalAlignment(JTextField.CENTER);

        JLabel folderLabel = new JLabel("Destination folder: None");
        folderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField gifPathField = new JTextField();
        gifPathField.setHorizontalAlignment(JTextField.CENTER);

        JButton folderSelectionButton = new JButton("Select the destination folder...");
        folderSelectionButton.setHorizontalAlignment(SwingConstants.CENTER);
        folderSelectionButton.addActionListener(new FolderSelectionListener(folderLabel, frame));


        JButton extractButton = new JButton("Extract Frames");
        extractButton.setHorizontalAlignment(JTextField.CENTER);
        extractButton.addActionListener(new ExtractFramesListener(gifPathField, frame, folderLabel));

        List<Component> components = new ArrayList<>();
        components.add(routeLabel);
        components.add(gifPathField);
        components.add(folderSelectionButton);
        components.add(folderLabel);
        components.add(extractButton);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                if (col == 1) panel.add(components.get(row));
                else panel.add(new JLabel(""));
            }
        }

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);

        System.out.println("Hello world!");
    }

    protected static void extractFrames(File gifFile, File parentFolder) throws IOException {
        ImageInputStream inputStream = ImageIO.createImageInputStream(gifFile);

        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
        ImageReader reader = readers.next();
        reader.setInput(inputStream);

        int numFrames = reader.getNumImages(true);

        String nameWithoutExtension = getFileNameWithoutExtension(gifFile.getAbsolutePath());
        File outputFolder = new File(parentFolder, nameWithoutExtension + " Frames");

        if (!outputFolder.exists()) {
            boolean created = outputFolder.mkdirs();
            if (created) System.out.println("A new folder has been created");
            else System.out.println("Couldn't be able to create a new folder");
        }
        else System.out.println("The folder already exists");

        for (int i = 0; i < numFrames; i++) {
            BufferedImage frame = reader.read(i);

            File outputFile = new File(outputFolder, "frame_" + i + ".png");
            ImageIO.write(frame, "png", outputFile);
        }
    }

    private static String getFileNameWithoutExtension(String filePath) {
        File file = new File(filePath);

        String fileNameWithExtension = file.getName();

        if (fileNameWithExtension.endsWith(".gif")) {
            return fileNameWithExtension.substring(0, fileNameWithExtension.length() - 4);
        } else {
            return fileNameWithExtension;
        }
    }
}