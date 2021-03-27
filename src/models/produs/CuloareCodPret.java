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

    static CuloareCodPret fromStrings(String culoare, String cod, String pret) {
        CuloareCodPret ccp = new CuloareCodPret();
        ccp.nume = culoare;
        ccp.cod = cod;
        ccp.pret = pret;
        return ccp;
    }

    public static CuloareCodPret emptyInstance() {
        return new CuloareCodPret();
    }

    String nume;

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
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

    public String getNume() {
        return nume;
    }

    public String getCod() {
        return cod;
    }

    public String getPret() {
        return pret;
    }
}
