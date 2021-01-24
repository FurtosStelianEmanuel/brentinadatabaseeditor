/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.produs;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Manel
 */
public class CuloareCodPret {

    String nume;
    String cod;
    String pret;

    private CuloareCodPret() {
        nume = "";
        cod = "";
        pret = "";
    }

    public static List<CuloareCodPret> fromJSONObject(JSONObject culoareCodPretJSON) {
        List<CuloareCodPret> toReturn = new ArrayList<>();
        for (Object culoare : culoareCodPretJSON.keySet()) {
            CuloareCodPret culoareCodPret = new CuloareCodPret();
            JSONArray codSiPret = (JSONArray) culoareCodPretJSON.get(culoare);
            culoareCodPret.nume = (String) culoare;
            culoareCodPret.cod = (String) codSiPret.get(0);
            culoareCodPret.pret = (String) codSiPret.get(1);
            toReturn.add(culoareCodPret);
        }
        return toReturn;
    }

    CuloareCodPret(CuloareCodPret culoareCodPret) {
        nume = culoareCodPret.nume;
        cod = culoareCodPret.cod;
        pret = culoareCodPret.pret;
    }
}
