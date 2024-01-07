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

public class Blur {
    public BufferedImage applyGaussianBlur(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[][] kernel = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
        };

        int kernelSum = 16; // sum of all elements in the kernel

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int pixel = originalImage.getRGB(i + k, j + l);
                        sumRed += ((pixel >> 16) & 0xFF) * kernel[k + 1][l + 1];
                        sumGreen += ((pixel >> 8) & 0xFF) * kernel[k + 1][l + 1];
                        sumBlue += (pixel & 0xFF) * kernel[k + 1][l + 1];
                    }
                }

                int avgRed = sumRed / kernelSum;
                int avgGreen = sumGreen / kernelSum;
                int avgBlue = sumBlue / kernelSum;

                int blurredPixel = (avgRed << 16) | (avgGreen << 8) | avgBlue;
                blurredImage.setRGB(i, j, blurredPixel);
            }
        }

        return blurredImage;
    }
}
