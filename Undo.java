/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package photoeditor;

/**
 *
 * @author WAN QISTINA DAMIA
 */

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;

public class Undo {
    private ImageIcon previousIcon;
    private BufferedImage croppedImage;
    private boolean isUndoRotate;
    private int rotateDegrees;

    public Undo(ImageIcon previousIcon, BufferedImage croppedImage, boolean isUndoRotate, int rotateDegrees) {
        this.previousIcon = previousIcon;
        this.croppedImage = croppedImage;
        this.isUndoRotate = isUndoRotate;
        this.rotateDegrees = rotateDegrees;
    }

    public ImageIcon getPreviousIcon() {
        return previousIcon;
    }

    public BufferedImage getCroppedImage() {
        return croppedImage;
    }

    public boolean isUndoRotate() {
        return isUndoRotate;
    }

    public int getRotateDegrees() {
        return rotateDegrees;
    }
}
