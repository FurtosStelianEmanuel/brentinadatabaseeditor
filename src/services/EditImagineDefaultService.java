/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author Manel
 */
public class EditImagineDefaultService {

    EditImagineDefaultApplicator applicator;
    private String imagineDefault;

    public String getImagineDefault() {
        return imagineDefault;
    }

    public long getPalleteType() {
        return palleteType;
    }
    private long palleteType;

    public EditImagineDefaultService(String imagineDefault, long palleteType) {
        this.imagineDefault = imagineDefault;
        this.palleteType = palleteType;
    }

    public void imagineDefaultChanged(String imagineDefault) {
        this.imagineDefault = imagineDefault;
        applicator.imagineDefaultChanged(imagineDefault);
    }

    public void setApplicator(EditImagineDefaultApplicator applicator) {
        this.applicator = applicator;
    }

    public void palleteTypeChanged(int selectedIndex) {
        this.palleteType = selectedIndex;
    }
}
