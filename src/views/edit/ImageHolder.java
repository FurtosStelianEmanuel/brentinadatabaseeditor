/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.edit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import services.interfaces.EventConfirmationListener;

/**
 *
 * @author Manel
 */
public class ImageHolder extends JButton implements MouseMotionListener {

    final int fixedWidthForAll = 150;
    protected ImageIcon icon;
    final int garbageIconWidth = 25;
    final Point garbageLocation;
    boolean isHoveringGarbage = false;
    EventConfirmationListener eventConfirmationListener;

    final protected Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    final protected Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    final Stroke garbageStroke = new BasicStroke(2);

    Path pathToImage;

    public ImageHolder(String name) {
        super(name);
        garbageLocation = new Point(
                (int) (getPreferredSize().getWidth() - garbageIconWidth - 5),
                (int) (getPreferredSize().getHeight() - garbageIconWidth - 5)
        );
    }

    public ImageHolder(Path path) {
        setImageHolderIcon(path);
        garbageLocation = new Point(
                (int) (getPreferredSize().getWidth() - garbageIconWidth - 5),
                (int) (getPreferredSize().getHeight() - garbageIconWidth - 5)
        );
        addMouseMotionListener(this);
    }

    public Path getPathToImage() {
        return pathToImage;
    }

    public final void setImageHolderIcon(Path p) {
        pathToImage = p;
        ImageIcon originalIcon = new ImageIcon(p.toString());
        originalIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(fixedWidthForAll,
                fixedWidthForAll * originalIcon.getIconHeight() / originalIcon.getIconWidth(),
                Image.SCALE_SMOOTH
        ));
        icon = originalIcon;
        setSize(icon.getIconWidth(), icon.getIconWidth());
        setIcon(icon);
    }

    public void setEventConfirmationListener(EventConfirmationListener confirmationListener) {
        this.eventConfirmationListener = confirmationListener;
    }

    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        g2.fillOval(
                (int) garbageLocation.getX(),
                (int) garbageLocation.getY(),
                garbageIconWidth,
                garbageIconWidth
        );
    }

    private boolean isInsideGarbage(Point cursor) {
        return cursor.getX() > garbageLocation.getX()
                && cursor.getX() < garbageLocation.getX() + garbageIconWidth
                && cursor.getY() > garbageLocation.getY()
                && cursor.getY() < garbageLocation.getY() + garbageIconWidth;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (isInsideGarbage(new Point(me.getX(), me.getY()))) {
            if (!isHoveringGarbage) {
                setCursor(handCursor);
                isHoveringGarbage = true;
            }
        } else {
            if (isHoveringGarbage) {
                setCursor(defaultCursor);
                isHoveringGarbage = false;
            }
        }
    }

    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static void writeCompressedImage(File imageFile, double compress, double resize) throws FileNotFoundException, IOException {
        File compressedImageFile = new File(imageFile.getParent(), String.format("%s-min.jpg", imageFile.getName().replace(".jpg", "")));
        OutputStream os;
        ImageWriter writer;
        ImageOutputStream ios;
        try (InputStream is = new FileInputStream(imageFile)) {
            os = new FileOutputStream(compressedImageFile);
            double quality = 1 - compress;
            BufferedImage image = ImageIO.read(is);
            image = toBufferedImage(
                    image.getScaledInstance(
                            (int) (image.getWidth() * resize),
                            (int) (image.getHeight() * resize),
                            Image.SCALE_SMOOTH)
            );

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            if (!writers.hasNext()) {
                throw new IllegalStateException("No writers found");
            }

            writer = (ImageWriter) writers.next();
            ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality((float) quality);

            writer.write(null, new IIOImage(image, null, null), param);

            os.close();
            ios.close();
            writer.dispose();
        }

    }

}
