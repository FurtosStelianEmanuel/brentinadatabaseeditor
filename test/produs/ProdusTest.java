/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produs;

import factory.ProdusFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import models.produs.Produs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import services.DatabaseService;

/**
 *
 * @author Manel
 */
public class ProdusTest {

    DatabaseService dbService;
    File goodInputFile = Paths.get("c:", "users", "manel", "desktop", "produse.json").toFile();
    File outpootFile = Paths.get("c:", "users", "manel", "desktop", "output.json").toFile();
    ProdusFactory produsFactory;

    public ProdusTest() {
        dbService = new DatabaseService();
        produsFactory = new ProdusFactory();
    }

    @Test
    public void When_ProdusConstructorCalledWithProdus_Then_DeepCopyCreated() throws ClassNotFoundException, IOException {
        Produs produs = produsFactory.getBasicObject();
        Produs result = new Produs(produs);

        assertFalse(produs == result);

        assertEquals(produs.id, result.id);
        assertEquals(produs.imagineDefault, result.imagineDefault);
        assertEquals(produs.nume, result.nume);
        assertEquals(produs.palleteType, result.palleteType);
        assertEquals(produs.pret, result.pret, .001);
        assertEquals(produs.um, result.um);
        assertEquals(produs.descriere, result.descriere);

        assertFalse(produs.coduriSiPreturi == result.coduriSiPreturi);
        assertEquals(produs.coduriSiPreturi.size(), result.coduriSiPreturi.size());
        assertEquals(1, result.coduriSiPreturi.size());
        assertEquals(produs.coduriSiPreturi.get(0).getDimensiune(), result.coduriSiPreturi.get(0).getDimensiune());
        assertFalse(produs.coduriSiPreturi.get(0).getData() == result.coduriSiPreturi.get(0).getData());
        assertEquals(produs.coduriSiPreturi.get(0).getData().size(), result.coduriSiPreturi.get(0).getData().size());
        assertEquals(1, result.coduriSiPreturi.get(0).getData().size());
        assertEquals(produs.coduriSiPreturi.get(0).getData().get(0).getCod(), result.coduriSiPreturi.get(0).getData().get(0).getCod());
        assertEquals(produs.coduriSiPreturi.get(0).getData().get(0).getNume(), result.coduriSiPreturi.get(0).getData().get(0).getNume());
        assertEquals(produs.coduriSiPreturi.get(0).getData().get(0).getPret(), result.coduriSiPreturi.get(0).getData().get(0).getPret());

        assertFalse(produs.culori == result.culori);
        assertEquals(produs.culori.size(), result.culori.size());
        assertEquals(1, result.culori.size());
        assertFalse(produs.culori.get(0).getAlteCulori() == result.culori.get(0).getAlteCulori());
        assertEquals(produs.culori.get(0).getAlteCulori().size(), result.culori.get(0).getAlteCulori().size());
        assertEquals(2, result.culori.get(0).getAlteCulori().size());
        assertEquals(produs.culori.get(0).getAlteCulori().get(0), result.culori.get(0).getAlteCulori().get(0));
        assertEquals(produs.culori.get(0).getAlteCulori().get(1), result.culori.get(0).getAlteCulori().get(1));

        assertFalse(produs.dimensiuni == result.dimensiuni);
        assertEquals(produs.dimensiuni.size(), result.dimensiuni.size());
        assertEquals(1, result.dimensiuni.size());
        assertEquals(produs.dimensiuni.get(0), result.dimensiuni.get(0));

        assertFalse(produs.similare == result.similare);
        assertEquals(produs.similare.size(), result.similare.size());
        assertEquals(2, result.similare.size());
        assertEquals(produs.similare.get(0), result.similare.get(0));
        assertEquals(produs.similare.get(1), result.similare.get(1));

        assertFalse(produs.translate == result.translate);
        assertEquals(produs.translate.getEnglishName(), result.translate.getEnglishName());
        assertEquals(produs.translate.getEnglishDescription(), result.translate.getEnglishDescription());
        assertEquals(produs.translate.getHungarianDescription(), result.translate.getHungarianDescription());
        assertEquals(produs.translate.getGermanDescription(), result.translate.getGermanDescription());
    }
}
