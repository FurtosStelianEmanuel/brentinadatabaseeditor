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
public class Nume {

    String en;
    String hu;
    String de;

    public void setEn(String en) {
        this.en = en;
    }

    public void setHu(String hu) {
        this.hu = hu;
    }

    public void setDe(String de) {
        this.de = de;
    }

    private Nume() {
        en = "";
        hu = "";
        de = "";
    }

    public static Nume emptyInstance() {
        return new Nume();
    }

    public static Nume fromJSONObject(JSONObject name) {
        Nume toReturn = new Nume();
        toReturn.en = (String) name.get("en");
        toReturn.hu = (String) name.get("hu");
        toReturn.de = (String) name.get("de");
        return toReturn;
    }

    public JSONObject toJSONObject() {
        JSONObject toReturn = new JSONObject();
        toReturn.put("en", en);
        toReturn.put("de", de);
        toReturn.put("hu", hu);
        return toReturn;
    }
}
