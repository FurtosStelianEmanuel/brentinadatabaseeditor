/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.produs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Manel
 */
public class Culoare {

    Color rgb;
    String nume;
    TranslateCuloare translate;
    long unghiuri;
    List<String> alteCulori;

    private Culoare() {
        rgb = new Color(0, 0, 0);
        nume = "";
        translate = TranslateCuloare.emptyInstance();
        unghiuri = 0;
        alteCulori = new ArrayList<>();
    }

    public static List<Culoare> fromJSONObject(JSONArray culoriArray) {
        List<Culoare> toReturn = new ArrayList<>();
        for (Object obj : culoriArray) {
            JSONObject culoare = (JSONObject) obj;
            Culoare c = new Culoare();
            c.unghiuri = (long) culoare.get("unghiuri");
            JSONArray rgb = (JSONArray) culoare.get("rgb");
            long r = (long) rgb.get(0);
            long g = (long) rgb.get(1);
            long b = (long) rgb.get(2);
            c.rgb = new Color((int) r, (int) g, (int) b);
            c.nume = (String) culoare.get("nume");
            c.translate = TranslateCuloare.fromJSONObject((JSONObject) culoare.get("translate"));
            if (culoare.get("alteCulori") != null) {
                JSONArray alteCuloriArray = (JSONArray) culoare.get("alteCulori");
                c.alteCulori.addAll(alteCuloriArray);
            }
            toReturn.add(c);
        }
        return toReturn;
    }

    public static JSONArray Condens(List<Culoare> culori) {
        JSONArray toReturn = new JSONArray();
        for (Culoare culoare : culori) {
            JSONObject culoareObject = new JSONObject();
            culoareObject.put("nume", culoare.nume);
            JSONArray rgb = new JSONArray();
            rgb.add(culoare.rgb.getRed());
            rgb.add(culoare.rgb.getGreen());
            rgb.add(culoare.rgb.getBlue());
            culoareObject.put("rgb", rgb);
            JSONObject translate = new JSONObject();
            translate.put("en", culoare.translate.en);
            translate.put("hu", culoare.translate.hu);
            translate.put("de", culoare.translate.de);
            culoareObject.put("translate", translate);
            culoareObject.put("unghiuri", culoare.unghiuri);
            if (culoare.alteCulori.size() > 0) {
                culoareObject.put("alteCulori", culoare.alteCulori);
            }
            toReturn.add(culoareObject);
        }
        return toReturn;
    }

    Culoare(Culoare culoare) {
        rgb = new Color(culoare.rgb.getRed(), culoare.rgb.getGreen(), culoare.rgb.getBlue());
        nume = culoare.nume;
        unghiuri = culoare.unghiuri;
        translate = new TranslateCuloare(culoare.translate);
    }
}
