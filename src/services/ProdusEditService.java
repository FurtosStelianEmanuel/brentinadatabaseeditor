/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
    List<Produs> produseCopy;

    public List<Produs> getProduseCopy() {
        return produseCopy;
    }

    public ProdusEditService(Produs currentProdus, List<Produs> produse) {
        this.originalProdus = currentProdus;
        this.copyProdus = new Produs(currentProdus);
        this.produseCopy = new ArrayList<>();
        produse.forEach((produs) -> {
            this.produseCopy.add(new Produs(produs));
        });
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
    public void similareChanged(List<UUID> similare) {
        copyProdus.similare = similare;
    }

    @Override
    public void palleteTypeChanged(long palleteType) {
        copyProdus.palleteType = palleteType;
    }

    @Override
    public void categoriiChanged(List<String> categorii) {
        copyProdus.categorii = categorii;
    }

    @Override
    public void imagineDefaultChanged(String imagineDefault) {
        copyProdus.imagineDefault = imagineDefault;
    }

    public List<String> getNumeProduse() {
        return produseCopy.stream().map(p -> p.nume).collect(Collectors.toList());
    }

    private Produs getProdusById(UUID id) {
        try {
            Optional<Produs> produs = produseCopy.stream().filter(p -> p.id.equals(id)).findFirst();
            return produs.get();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public List<String> getProduseSimilareNames() {
        List<String> toReturn = new ArrayList<>();
        copyProdus.similare.stream().map((id) -> getProdusById(id)).filter((produs) -> (produs != null)).forEachOrdered((produs) -> {
            toReturn.add(produs.nume);
        });
        return toReturn;
    }
}
