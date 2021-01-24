/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.swing.JFrame;
import models.produs.InitialComplete;
import models.produs.TranslateProdus;
import views.edit.EditTranslateProdusDescrieriForm;

/**
 *
 * @author Manel
 */
public class EditTranslateProdusDescrieriApplicator extends InitialComplete {

    EditTranslateProdusDescrieriForm form;

    public EditTranslateProdusDescrieriApplicator(JFrame form) {
        super(form);
        this.form = (EditTranslateProdusDescrieriForm) form;
    }

    @Override
    public void autoCompleteData(Object objectData) {
        TranslateProdus translateProdus = (TranslateProdus) objectData;
        form.englishName.setText(translateProdus.getEnglishName());
        form.hungarianName.setText(translateProdus.getHungarianName());
        form.germanName.setText(translateProdus.getGermanName());

        form.descriptionEnglish.setText(translateProdus.getEnglishDescription());
        form.descriptionMaghiara.setText(translateProdus.getHungarianDescription());
        form.descriptionGerman.setText(translateProdus.getGermanDescription());
    }
}
