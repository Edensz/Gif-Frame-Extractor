package window.manager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GifFrameExtractorWindowUtils {

    public static void sortPanelComponents(JFrame frame, JPanel panel, List<Component> components) {
        for (Component component : components) {
            for (int col = 0; col < 3; col++) {
                if (col == 1) panel.add(component);
                else panel.add(new JLabel(""));
            }
        }

        frame.add(panel, BorderLayout.CENTER);
    }

}
