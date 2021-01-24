/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.produs;

import main.Configuration;
import org.json.simple.JSONObject;
import services.interfaces.TranslateProdusInterface;

/**
 *
 * @author Manel
 */
public class TranslateProdus implements TranslateProdusInterface {

    String descriereEn;
    String descriereHu;
    String descriereDe;
    Nume nume;

    private TranslateProdus() {
        descriereEn = "";
        descriereHu = "";
        descriereDe = "";
        nume = Nume.emptyInstance();
    }

    public static TranslateProdus emptyInstance() {
        return new TranslateProdus();
    }

    public static TranslateProdus fromTranslationStrings(String nume[], String descrieri[]) {
        if (nume.length != Configuration.SUPPORTED_FOREIGN_LANGUAGES || descrieri.length != Configuration.SUPPORTED_FOREIGN_LANGUAGES) {
            throw new UnsupportedOperationException("Wrong translation data");//
        } else {
            TranslateProdus translateProdus = new TranslateProdus();
            translateProdus.descriereEn = descrieri[0];
            translateProdus.descriereHu = descrieri[1];
            translateProdus.descriereDe = descrieri[2];

            translateProdus.nume.en = nume[0];
            translateProdus.nume.hu = nume[1];
            translateProdus.nume.de = nume[2];
            return translateProdus;
        }
    }

    public static TranslateProdus fromJSONObject(JSONObject translateObject) {
        TranslateProdus toReturn = new TranslateProdus();
        toReturn.descriereEn = (String) translateObject.get("en");
        toReturn.descriereHu = (String) translateObject.get("hu");
        toReturn.descriereDe = (String) translateObject.get("de");
        toReturn.nume = Nume.fromJSONObject((JSONObject) translateObject.get("nume"));
        return toReturn;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("en", descriereEn);
        object.put("hu", descriereHu);
        object.put("de", descriereDe);
        object.put("nume", nume.toJSONObject());
        return object;
    }

    @Override
    public String getEnglishName() {
        return nume.en;
    }

    @Override
    public String getHungarianName() {
        return nume.hu;
    }

    @Override
    public String getGermanName() {
        return nume.de;
    }

    @Override
    public String getEnglishDescription() {
        return descriereEn;
    }

    @Override
    public String getHungarianDescription() {
        return descriereHu;
    }

    @Override
    public String getGermanDescription() {
        return descriereDe;
    }

}
