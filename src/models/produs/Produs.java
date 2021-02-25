/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.produs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Manel
 */
public class Produs {

    public String nume;
    public List<String> dimensiuni;
    public List<DimensiuneCulori> coduriSiPreturi;
    public List<Culoare> culori;
    public String imagineDefault;
    public String descriere;
    public TranslateProdus translate;
    /**
     * momentan nu are nicio functionalitate in spate, ar trebui scos din model (in viitor)
     */
    public double pret;
    public List<String> similare;
    public List<String> categorii;
    public String um;
    public long palleteType;

    private Produs() {
        nume = "";
        dimensiuni = new ArrayList<>();
        coduriSiPreturi = new ArrayList<>();
        culori = new ArrayList();
        imagineDefault = "";
        translate = TranslateProdus.emptyInstance();
        pret = 0;
        similare = new ArrayList<>();
        categorii = new ArrayList<>();
        um = "";
        palleteType = 0;
    }

    public Produs(Produs source) {
        coduriSiPreturi = new ArrayList<>();
        culori = new ArrayList<>();
        dimensiuni = new ArrayList<>();
        similare = new ArrayList<>();
        categorii = new ArrayList<>();
        imagineDefault = source.imagineDefault;
        nume = source.nume;
        translate = TranslateProdus.emptyInstance();
        pret = source.pret;
        um = source.um;
        palleteType = source.palleteType;

        translate.descriereEn = source.translate.descriereEn;
        translate.descriereHu = source.translate.descriereHu;
        translate.descriereDe = source.translate.descriereDe;

        translate.nume = Nume.emptyInstance();
        translate.nume.en = source.translate.nume.en;
        translate.nume.hu = source.translate.nume.hu;
        translate.nume.de = source.translate.nume.de;

        source.coduriSiPreturi.forEach((DimensiuneCulori dimensiuneCulori) -> {
            coduriSiPreturi.add(new DimensiuneCulori(dimensiuneCulori));
        });

        source.culori.forEach((Culoare culoare) -> {
            culori.add(new Culoare(culoare));
        });

        source.dimensiuni.forEach((String dimensiune) -> {
            dimensiuni.add(dimensiune);
        });

        source.similare.forEach((String similar) -> {
            similare.add(similar);
        });

        source.categorii.forEach((String categorie) -> {
            categorii.add(categorie);
        });
    }

    public static Produs emptyInstance() {
        Produs p = new Produs();
        p.categorii = new ArrayList<>();
        p.coduriSiPreturi = new ArrayList<>();
        p.culori = new ArrayList<>();
        p.descriere = "";
        p.dimensiuni = new ArrayList<>();
        p.imagineDefault = "";
        p.nume = "";
        p.palleteType = 0;
        p.pret = 3.0d;
        p.similare = new ArrayList<>();
        p.translate = TranslateProdus.emptyInstance();
        p.um = "";
        return p;
    }

    public static Produs fromJSONObject(JSONObject produs) {
        Produs p = new Produs();
        p.nume = (String) produs.get("nume");
        JSONArray dimensiuni = (JSONArray) produs.get("dimensiuni");
        if (dimensiuni != null) {
            for (Object dimensiune : dimensiuni) {
                p.dimensiuni.add((String) dimensiune);
            }
        }
        JSONObject coduriSiProduse = (JSONObject) produs.get("coduriSiPreturi");
        if (coduriSiProduse != null) {
            p.coduriSiPreturi = DimensiuneCulori.fromJSONObject(coduriSiProduse);
        }
        JSONArray culori = (JSONArray) produs.get("culori");
        if (culori != null) {
            p.culori = Culoare.fromJSONObject(culori);
        }
        Object imagineDefault = produs.get("imagineDefault");
        if (imagineDefault != null) {
            p.imagineDefault = (String) imagineDefault;
        }
        Object descriere = produs.get("descriere");
        if (descriere != null) {
            p.descriere = (String) descriere;
        }
        JSONObject translate = (JSONObject) produs.get("translate");
        if (translate != null) {
            p.translate = TranslateProdus.fromJSONObject(translate);
        }
        Object pret = produs.get("pret");
        if (pret != null) {
            p.pret = (double) pret;
        }
        JSONArray similare = (JSONArray) produs.get("similare");
        for (Object similar : similare) {
            p.similare.add((String) similar);
        }
        JSONArray categorii = (JSONArray) produs.get("categorii");
        for (Object o : categorii) {
            p.categorii.add((String) o);
        }
        Object um = produs.get("um");
        if (um != null) {
            p.um = (String) um;
        }
        Object palleteType = produs.get("palleteType");
        if (palleteType != null) {
            p.palleteType = (long) palleteType;
        }
        return p;
    }

    public JSONObject toJSONObject() throws ParseException {
        JSONObject map = new JSONObject();
        map.put("nume", nume);
        map.put("dimensiuni", dimensiuni);
        map.put("coduriSiPreturi", DimensiuneCulori.Condens(coduriSiPreturi));
        map.put("culori", Culoare.Condens(culori));
        map.put("imagineDefault", imagineDefault);
        map.put("descriere", descriere);
        map.put("pret", pret);
        map.put("um", um);
        map.put("similare", similare);
        map.put("categorii", categorii);
        map.put("palleteType", palleteType);
        map.put("translate", translate.toJSONObject());
        return map;
    }

    public static class NameComparator implements Comparator<Produs> {

        @Override
        public int compare(Produs t1, Produs t2) {
            return t1.nume.compareToIgnoreCase(t2.nume);
        }

        public static NameComparator getInstance() {
            return new NameComparator();
        }
    }

    @Override
    public String toString() {
        try {
            return toJSONObject().toJSONString();
        } catch (ParseException ex) {
            Logger.getLogger(Produs.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
