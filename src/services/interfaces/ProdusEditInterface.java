/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import java.util.List;
import java.util.UUID;
import models.produs.Culoare;
import models.produs.DimensiuneCulori;
import models.produs.TranslateProdus;

/**
 *
 * @author Manel
 */
public interface ProdusEditInterface {

    void numeChanged(String newName);

    void descriereChanged(String descriere);

    void umChanged(String um);

    void translateChanged(TranslateProdus translate);

    void dimensiuniChanged(List<String> dimensiuni);

    void coduriSiPreturiChanged(List<DimensiuneCulori> coduriSiPreturi);

    void culoriChanged(List<Culoare> culori);

    void similareChanged(List<UUID> similare);

    void palleteTypeChanged(long palleteType);

    void categoriiChanged(List<String> categorii);

    void imagineDefaultChanged(String imagineDefault);
}
