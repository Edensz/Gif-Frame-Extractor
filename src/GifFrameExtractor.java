import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class GifFrameExtractor {
    public static File outputFolder;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GIF Frame Extractor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
        frame.setLayout(new BorderLayout());
        frame.setLocation(500, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel label = new JLabel("Insert GIF file route below...");
        label.setHorizontalAlignment(JTextField.CENTER);

        JTextField gifPathField = new JTextField();
        gifPathField.setHorizontalAlignment(JTextField.CENTER);

        JButton folderButton = new JButton("Select the destination folder...");
        folderButton.setHorizontalAlignment(SwingConstants.CENTER);

        JButton extractButton = new JButton("Extract Frames");
        extractButton.setHorizontalAlignment(JTextField.CENTER);
        extractButton.addActionListener(new ExtractFramesListener(gifPathField, frame));

        panel.add(label);
        panel.add(gifPathField);
        panel.add(folderButton);
        panel.add(extractButton);

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);

        System.out.println("Hello world!");
    }

    protected static void extractFrames(File gifFile) throws IOException {
        ImageInputStream inputStream = ImageIO.createImageInputStream(gifFile);

        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
        ImageReader reader = readers.next();
        reader.setInput(inputStream);

        int numFrames = reader.getNumImages(true);

        for (int i = 0; i < numFrames; i++) {
            BufferedImage frame = reader.read(i);

            File outputFile = new File(outputFolder, "frame_" + i + ".png");
            ImageIO.write(frame, "png", outputFile);
        }
    }
}