import window.manager.GifFrameExtractorWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GifFrameExtractor {

    public static void main(String[] args) {
        GifFrameExtractorWindow window = new GifFrameExtractorWindow();
        window.createExtractionComponents();
        window.createDestinationComponents();
        window.createPanel();
        window.build();
    }
}