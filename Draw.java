/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package photoeditor;

/**
 *
 * @author User
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Draw {
    private Point startPoint;
    private Point endPoint;

    public Draw(JLabel imageDisplayLabel) {
        imageDisplayLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startPoint = new Point(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e) {
                endPoint = new Point(e.getX(), e.getY());
                drawOnImage(imageDisplayLabel);
            }
        });
    }

    private void drawOnImage(JLabel imageDisplayLabel) {
        ImageIcon icon = (ImageIcon) imageDisplayLabel.getIcon();
        if (icon != null) {
            Image image = icon.getImage();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            g2d.dispose();

            ImageIcon newIcon = new ImageIcon(bufferedImage);
            imageDisplayLabel.setIcon(newIcon);
            imageDisplayLabel.revalidate();
            imageDisplayLabel.repaint();
        }
    }
}
