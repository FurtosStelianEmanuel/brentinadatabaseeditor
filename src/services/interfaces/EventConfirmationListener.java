/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

/**
 *
 * @author Manel
 */
public interface EventConfirmationListener {

    void onConfirm(Object p);

    void onCancel();

    void onFinish(Object o);
}
