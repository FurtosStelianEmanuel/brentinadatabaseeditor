/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import models.produs.Culoare;
import models.produs.DimensiuneCulori;
import models.produs.Produs;
import models.produs.TranslateProdus;
import services.interfaces.ProdusEditInterface;

/**
 *
 * @author Manel
 */
public class ProdusEditService implements ProdusEditInterface {

    Produs copyProdus;
    Produs originalProdus;

    public ProdusEditService(Produs currentProdus) {
        this.originalProdus = currentProdus;
        this.copyProdus = new Produs(currentProdus);
    }

    public Produs getProdusCopy() {
        return copyProdus;
    }

    /**
     * Doar cand esti sigur ca vrei sa inlocuiesti produsul din lista
     *
     * @return
     */
    public Produs getOriginalProdus() {
        return originalProdus;
    }

    @Override
    public void numeChanged(String nume) {
        copyProdus.nume = nume;
    }

    @Override
    public void descriereChanged(String descriere) {
        copyProdus.descriere = descriere;
    }

    @Override
    public void umChanged(String um) {
        copyProdus.um = um;
    }

    @Override
    public void translateChanged(TranslateProdus translate) {
        copyProdus.translate = translate;
    }

    @Override
    public void dimensiuniChanged(List<String> dimensiuni) {
        copyProdus.dimensiuni = dimensiuni;
    }

    @Override
    public void coduriSiPreturiChanged(List<DimensiuneCulori> coduriSiPreturi) {
        copyProdus.coduriSiPreturi = coduriSiPreturi;
    }

    @Override
    public void culoriChanged(List<Culoare> culori) {
        copyProdus.culori = culori;
    }

    @Override
    public void similareChanged(List<String> similare) {
        copyProdus.similare = similare;
    }

    @Override
    public void palleteTypeChanged(long palleteType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void categoriiChanged(List<String> categorii) {
        copyProdus.categorii = categorii;
    }

}
