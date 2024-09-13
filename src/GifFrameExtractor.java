
import destiny.DestinyJButtonListener;
import destiny.DestinyJButtonUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GifFrameExtractor {
    public static JLabel folderLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GIF Frame Extractor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 500);
        frame.setLocation(350, 175);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 3));

        JLabel routeLabel = new JLabel("Insert GIF file route below...");
        routeLabel.setHorizontalAlignment(JTextField.CENTER);

        JTextField gifPathField = new JTextField();
        gifPathField.setHorizontalAlignment(JTextField.CENTER);

        JButton extractButton = new JButton("Extract Frames");
        extractButton.setHorizontalAlignment(JTextField.CENTER);
        extractButton.addActionListener(new ExtractJButtonListener(gifPathField, frame));

        String userHome = System.getProperty("user.home");
        String documentsPath = userHome + File.separator + "Documents";
        File documentsFolder = new File(documentsPath);

        File outputFolder = new File(documentsFolder, "GifFrameExtractor");
        if (!outputFolder.exists()) {
            boolean wasSuccessful = outputFolder.mkdirs();
            if (wasSuccessful) System.out.println("The file has been created successfully: " + outputFolder.getName());
        }

        folderLabel = new JLabel();
        folderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        DestinyJButtonUtils.changeDestiny(folderLabel, outputFolder);

        JButton folderSelectionButton = new JButton("Select the destination folder...");
        folderSelectionButton.setHorizontalAlignment(SwingConstants.CENTER);
        folderSelectionButton.addActionListener(new DestinyJButtonListener(folderLabel, frame));

        Component[] components = {routeLabel, gifPathField, folderLabel, folderSelectionButton, extractButton};
        for (Component component : components) {
            for (int col = 0; col < 3; col++) {
                if (col == 1) panel.add(component);
                else panel.add(new JLabel(""));
            }
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void extractFrames(File gifFile, File outputFolder) throws IOException {
        File personalFolder = new File(outputFolder, getFileNameWithoutExtension(gifFile.getAbsolutePath()) + " Frames");
        if (personalFolder.exists()) {
            boolean wasSuccessful = personalFolder.delete();
            if (wasSuccessful) System.out.println("The file has been deleted: " + personalFolder.getName());
        }

        boolean wasSuccessful = personalFolder.mkdirs();
        if (wasSuccessful) System.out.println("The file has been created successfully: " + personalFolder.getName());

        try {
            String[] imageKit = new String[] {
                    "imageLeftPosition",
                    "imageTopPosition",
                    "imageWidth",
                    "imageHeight"
            };

            ImageReader imageReader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream inputStream = ImageIO.createImageInputStream(gifFile);
            imageReader.setInput(inputStream, false);

            int numberOfImages = imageReader.getNumImages(true);
            BufferedImage master = null;

            for (int i = 0; i < numberOfImages; i++) {
                BufferedImage image = imageReader.read(i);
                IIOMetadata metadata = imageReader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node nodeItem = children.item(j);

                    if (nodeItem.getNodeName().equals("ImageDescriptor")) {
                        Map<String, Integer> imageAttr = new HashMap<>();

                        for (String s : imageKit) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(s);
                            imageAttr.put(s, Integer.valueOf(attnode.getNodeValue()));
                        }

                        if (i == 0) master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        if (master != null) master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
                    }
                }

                if (master != null) ImageIO.write(master, "GIF", new File(personalFolder, i + ".gif"));
            }
        }
        catch (IOException exception) {
            exception.fillInStackTrace();
        }
    }

    public static String getFileNameWithoutExtension(String filePath) {
        File file = new File(filePath);

        String fileNameWithExtension = file.getName();

        if (fileNameWithExtension.endsWith(".gif")) {
            return fileNameWithExtension.substring(0, fileNameWithExtension.length() - 4);
        } else {
            return fileNameWithExtension;
        }
    }
}