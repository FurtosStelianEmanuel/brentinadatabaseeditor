/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.auxclasses;

import java.awt.event.KeyAdapter;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.awt.event.KeyEvent;

/**
 *
 * @author Manel
 */
public class JListVerticalMovementListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent evt) {
        JList list = (JList) evt.getSource();
        DefaultListModel model = (DefaultListModel) list.getModel();
        if (list.getSelectedIndex() > 0) {
            if (evt.getKeyCode() == KeyEvent.VK_UP) {
                Object aux = model.get(list.getSelectedIndex() - 1);
                int index = list.getSelectedIndex();
                model.set(index - 1, model.get(index));
                model.set(index, aux);
            }
        }
        if (list.getSelectedIndex() < model.getSize() - 1) {
            if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
                Object aux = model.get(list.getSelectedIndex() + 1);
                int index = list.getSelectedIndex();
                model.set(index + 1, model.get(index));
                model.set(index, aux);
            }
        }
    }
}
