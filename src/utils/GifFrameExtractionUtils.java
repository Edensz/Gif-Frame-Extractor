package utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class GifFrameExtractionUtils {

    public static void extractFrames(File gifFile, File parentFolder) throws IOException {
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
