package window.manager;

import window.buttons.destiny.DestinyJButtonListener;
import window.buttons.destiny.DestinyJButtonUtils;
import window.buttons.extract.ExtractJButtonListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GifFrameExtractorWindow {
    private final List<Component> components = new ArrayList<>();
    public static JLabel folderLabel;

    private final JFrame frame;
    private JPanel panel;

    public GifFrameExtractorWindow() {
        this.frame = new JFrame("GIF Frame Extractor");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(850, 500);
        this.frame.setLocation(350, 175);
        this.frame.setLayout(new BorderLayout());
    }

    public void createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(this.components.size(), 3));
        this.panel = panel;
    }

    public void createDestinationComponents() {
        String userHome = System.getProperty("user.home");
        String documentsPath = userHome + File.separator + "Documents";
        File documentsFolder = new File(documentsPath);

        File outputFolder = new File(documentsFolder, "GifFrameExtractor");
        if (!outputFolder.exists()) {
            boolean created = outputFolder.mkdirs();
            if (created) System.out.println("A new folder has been created");
            else System.out.println("Couldn't be able to create a new folder");
        }
        else System.out.println("The folder already exists");

        folderLabel = new JLabel();
        folderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        DestinyJButtonUtils.changeDestiny(folderLabel, outputFolder);

        JButton folderSelectionButton = new JButton("Select the destination folder...");
        folderSelectionButton.setHorizontalAlignment(SwingConstants.CENTER);
        folderSelectionButton.addActionListener(new DestinyJButtonListener(folderLabel, this.frame));

        this.components.add(new JLabel(""));
        this.components.add(folderLabel);
        this.components.add(folderSelectionButton);
    }

    public void createExtractionComponents() {
        JLabel routeLabel = new JLabel("Insert GIF file route below...");
        routeLabel.setHorizontalAlignment(JTextField.CENTER);

        JTextField gifPathField = new JTextField();
        gifPathField.setHorizontalAlignment(JTextField.CENTER);

        JButton extractButton = new JButton("Extract Frames");
        extractButton.setHorizontalAlignment(JTextField.CENTER);
        extractButton.addActionListener(new ExtractJButtonListener(gifPathField, this.frame));

        this.components.add(routeLabel);
        this.components.add(gifPathField);
        this.components.add(extractButton);
    }

    public void build() {
        GifFrameExtractorWindowUtils.sortPanelComponents(this.frame, this.panel, this.components);

        this.frame.add(this.panel, BorderLayout.CENTER);
        this.frame.setVisible(true);
    }
}
