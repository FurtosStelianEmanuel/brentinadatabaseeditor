/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.database;

import org.json.simple.JSONObject;

/**
 *
 * @author Manel
 */
public class AltaCuloare {

    public String nume;
    public String translateEn;
    public String translateHu;
    public String translateDe;

    private AltaCuloare() {
        nume = "";
        translateEn = "";
        translateHu = "";
        translateDe = "";
    }

    private AltaCuloare(String nume, String en, String hu, String de) {
        this.nume = nume;
        this.translateEn = en;
        this.translateHu = hu;
        this.translateDe = de;
    }

    public AltaCuloare(AltaCuloare source) {
        this.nume = source.nume;
        this.translateDe = source.translateDe;
        this.translateEn = source.translateEn;
        this.translateHu = source.translateHu;
    }

    public static AltaCuloare fromStrings(String nume, String en, String hu, String de) {
        return new AltaCuloare(nume, en, hu, de);
    }

    public static AltaCuloare fromJSONObject(JSONObject object) {
        AltaCuloare toReturn = new AltaCuloare();
        toReturn.nume = (String) object.get("nume");
        toReturn.translateDe = (String) object.get("translateDe");
        toReturn.translateEn = (String) object.get("translateEn");
        toReturn.translateHu = (String) object.get("translateHu");
        return toReturn;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        object.put("nume", nume);
        object.put("translateEn", translateEn);
        object.put("translateHu", translateHu);
        object.put("translateDe", translateDe);
        return object.toJSONString();
    }
}
