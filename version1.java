/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package photoeditor;

/**
 *
 * @author WAN AINNA 
 */
// version1.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;  // Import for IOException
import java.awt.image.BufferedImage;  // Import for BufferedImage
import javax.imageio.ImageIO;  // Import for ImageIO
import java.util.Stack;

public class version1 {
    
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private ImageIcon icon;
    private JLabel imageDisplayLabel;
    private CropImage cropImage;
    private RotateImage rotateImage;
    private Stack<ImageIcon> undoStack;

    public version1() {
        frame = new JFrame();
        tabbedPane = new JTabbedPane();
        JPanel panel = homePage();

        frame.add(tabbedPane);
        tabbedPane.addTab("Home", panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Photo Editor");
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        cropImage = new CropImage();
        rotateImage = new RotateImage();
        undoStack = new Stack<>();
    }

    private JPanel homePage() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 50, 100));
        panel.setBackground(Color.decode("#f7f1e6"));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(150, 150, 50, 150);

        JLabel titleLabel = createTitleLabel("Photo Editor");
        panel.add(titleLabel, constraints);

        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 10, 0);

        JButton importButton = createButton("Import Image");
        importButton.setPreferredSize(new Dimension(200, 50));
        importButton.setBackground(Color.decode("#fae6c0"));
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechooser = new JFileChooser();
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int result = filechooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = filechooser.getSelectedFile();
                    showImage(selectedFile);
                }
            }
        });

        constraints.gridy = 2;
        panel.add(importButton, constraints);

        return panel;
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Poppins", Font.BOLD, 48));
        label.setForeground(Color.decode("#ff7733"));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.PLAIN, 16));
        button.setForeground(Color.BLACK);

        Color initialBackgroundColor = Color.decode("#fae6c0");

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#f7d38f"));
                button.setOpaque(true);
                button.requestFocus();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(initialBackgroundColor);
                button.setOpaque(false);
            }
        });

        return button;
    }

    private void showImage(File selectedFile) {
        ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());

        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();
        int scaledWidth, scaledHeight;

        if (originalWidth > originalHeight) {
            scaledWidth = 400;
            scaledHeight = (int) ((double) originalHeight / originalWidth * scaledWidth);
        } else {
            scaledHeight = 400;
            scaledWidth = (int) ((double) originalWidth / originalHeight * scaledHeight);
        }

        Image image = icon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        this.icon = new ImageIcon(image);

        //integrate the CropImage function
        BufferedImage originalImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = originalImage.createGraphics();
        icon.paintIcon(null, g2d, 0, 0);
        g2d.dispose();
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imageDisplayLabel = new JLabel(this.icon);
        
        
        //crop action
        cropImage = new CropImage();
        cropImage.imageDisplayLabel = imageDisplayLabel; // Pass the reference to imageDisplayLabel
        cropImage.loadImage(selectedFile);
        
        imagePanel.add(imageDisplayLabel, BorderLayout.CENTER);

        // Crop button
        JButton cropButton = createButton("Crop");
        cropButton.setPreferredSize(new Dimension(200, 50));
        cropButton.setBackground(Color.decode("#fae6c0"));
        cropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cropImage.setCropMode(true);
            }
        });
        
        //Rotate action
        JButton rotateButton = createButton("Rotate");
        rotateButton.setPreferredSize(new Dimension(200, 50));
        rotateButton.setBackground(Color.decode("#fae6c0"));
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateImage.showRotateMenu(imageDisplayLabel); // Pass the imageDisplayLabel reference
            }
        });
        
        //undo button
        JButton undoButton = createButton("Undo");
        undoButton.setPreferredSize(new Dimension(200, 50));
        undoButton.setBackground(Color.decode("#fae6c0"));
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon previousIcon = rotateImage.undo(imageDisplayLabel);
                if (previousIcon != null) {
                    undoStack.push(previousIcon);
                }
            }
        });

        // Other features (Placeholder buttons)
        JButton blurButton = createButton("Blur");
        blurButton.setPreferredSize(new Dimension(200, 50));
        blurButton.setBackground(Color.decode("#fae6c0"));
        blurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your blur implementation here...
            }
        });

        JButton brightnessButton = createButton("Brightness");
        brightnessButton.setPreferredSize(new Dimension(200, 50));
        brightnessButton.setBackground(Color.decode("#fae6c0"));
        brightnessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your brightness implementation here...
            }
        });

        JButton monochromeButton = createButton("Monochrome");
        monochromeButton.setPreferredSize(new Dimension(200, 50));
        monochromeButton.setBackground(Color.decode("#fae6c0"));
        monochromeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your monochrome implementation here...
            }
        });

        JButton resizeButton = createButton("Resize");
        resizeButton.setPreferredSize(new Dimension(200, 50));
        resizeButton.setBackground(Color.decode("#fae6c0"));
        resizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your resize implementation here...
            }
        });

        JButton drawButton = createButton("Draw");
        drawButton.setPreferredSize(new Dimension(200, 50));
        drawButton.setBackground(Color.decode("#fae6c0"));
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your draw implementation here...
            }
        });

        JButton resetButton = createButton("Reset");
        resetButton.setPreferredSize(new Dimension(200, 50));
        resetButton.setBackground(Color.decode("#fae6c0"));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your reset implementation here...
            }
        });

        JButton saveButton = createButton("Save");
        saveButton.setPreferredSize(new Dimension(200, 50));
        saveButton.setBackground(Color.decode("#fae6c0"));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your save implementation here...
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(cropButton);
        buttonPanel.add(rotateButton);
        buttonPanel.add(blurButton);
        buttonPanel.add(brightnessButton);
        buttonPanel.add(monochromeButton);
        buttonPanel.add(resizeButton);
        buttonPanel.add(drawButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(undoButton);

        imagePanel.add(imageDisplayLabel, BorderLayout.CENTER);
        imagePanel.add(buttonPanel, BorderLayout.SOUTH);

        JLabel closeButton = new JLabel(new ImageIcon("D:\\SE S3\\WIX1002\\icon\\cross.png"));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tabIndex = tabbedPane.indexOfComponent(imagePanel);
                if (tabIndex != -1) {
                    tabbedPane.removeTabAt(tabIndex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        JPanel tabPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(selectedFile.getName());

        tabPanel.add(titleLabel, BorderLayout.WEST);
        tabPanel.add(Box.createHorizontalStrut(10), BorderLayout.CENTER);
        tabPanel.add(closeButton, BorderLayout.EAST);

        tabbedPane.addTab(null, imagePanel);
        int lastIndex = tabbedPane.getTabCount() - 1;
        tabbedPane.setTabComponentAt(lastIndex, tabPanel);

        tabbedPane.setTitleAt(lastIndex, selectedFile.getName());
    }

    private void undo() {
        if (!undoStack.isEmpty()) {
            ImageIcon previousIcon = undoStack.pop();
            imageDisplayLabel.setIcon(previousIcon);
            frame.revalidate();
            frame.repaint();
        }
    }

    private static BufferedImage loadImage(String filePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new version1();
        new RotateImage();
        new CropImage();
    });
    }

}
