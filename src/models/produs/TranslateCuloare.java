/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.produs;

import org.json.simple.JSONObject;

/**
 *
 * @author Manel
 */
public class TranslateCuloare {

    String en;
    String hu;
    String de;

    private TranslateCuloare() {
        en = "";
        hu = "";
        de = "";
    }

    public static TranslateCuloare emptyInstance() {
        return new TranslateCuloare();
    }

    public static TranslateCuloare fromJSONObject(JSONObject culoare) {
        TranslateCuloare toReturn = new TranslateCuloare();
        toReturn.en = (String) culoare.get("en");
        toReturn.hu = (String) culoare.get("hu");
        toReturn.de = (String) culoare.get("de");
        return toReturn;
    }

    TranslateCuloare(TranslateCuloare translate) {
        en = translate.en;
        hu = translate.hu;
        de = translate.de;
    }
}
