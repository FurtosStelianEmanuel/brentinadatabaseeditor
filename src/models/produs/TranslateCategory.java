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
public class TranslateCategory {

    public static TranslateCategory fromStrings(String en, String hu, String de) {
        return new TranslateCategory(en, hu, de);
    }

    String en;
    String hu;
    String de;

    public TranslateCategory(TranslateCategory translate) {
        this.en = translate.en;
        this.hu = translate.hu;
        this.de = translate.de;
    }

    public String getEn() {
        return en;
    }

    public String getHu() {
        return hu;
    }

    public String getDe() {
        return de;
    }

    private TranslateCategory(String en, String hu, String de) {
        this.en = en;
        this.hu = hu;
        this.de = de;
    }

    private TranslateCategory(Object en, Object hu, Object de) {
        this.en = (String) en;
        this.hu = (String) hu;
        this.de = (String) de;
    }

    public static TranslateCategory emptyInstance() {
        return new TranslateCategory("", "", "");
    }

    public static TranslateCategory fromJSONObject(JSONObject translate) {
        return new TranslateCategory(translate.get("en"), translate.get("hu"), translate.get("de"));
    }
}
