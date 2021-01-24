/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.edit;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.nio.file.Path;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Manel
 */
public class ImageHolder extends JButton implements MouseMotionListener {

    final int fixedWidthForAll = 150;
    final protected ImageIcon icon;
    final int garbageIconWidth = 25;
    final Point garbageLocation;
    boolean isHoveringGarbage = false;

    final protected Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    final protected Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

    public ImageHolder(Path path) {
        ImageIcon originalIcon = new ImageIcon(path.toString());
        originalIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(fixedWidthForAll,
                fixedWidthForAll * originalIcon.getIconHeight() / originalIcon.getIconWidth(),
                Image.SCALE_SMOOTH
        ));
        icon = originalIcon;
        setSize(icon.getIconWidth(), icon.getIconWidth());
        setIcon(icon);
        garbageLocation = new Point(
                (int) (getPreferredSize().getWidth() - garbageIconWidth - 5),
                (int) (getPreferredSize().getHeight() - garbageIconWidth - 5)
        );
        addMouseMotionListener(this);
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
        g2.fillRect(
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

}
