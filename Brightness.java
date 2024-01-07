/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package photoeditor;

/**
 *
 * @author User
 */
import java.awt.image.BufferedImage;

public class Brightness {
    public BufferedImage adjustBrightness(BufferedImage originalImage, int factor) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage brightenedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = originalImage.getRGB(i, j);

                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                // Adjust brightness
                red = clamp(red + factor);
                green = clamp(green + factor);
                blue = clamp(blue + factor);

                int brightenedPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                brightenedImage.setRGB(i, j, brightenedPixel);
            }
        }

        return brightenedImage;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
