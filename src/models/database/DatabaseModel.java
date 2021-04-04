/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.database;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import models.produs.Produs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Manel
 */
public class DatabaseModel {

    public long produseAfisate;
    public List<Produs> continut;
    public List<UUID> newProducts;
    public List<Category> categories;
    public List<AltaCuloare> alteCulori;

    private DatabaseModel() {
        produseAfisate = 2;
        continut = new ArrayList<>();
        newProducts = new ArrayList<>();
        categories = new ArrayList<>();
        alteCulori = new ArrayList<>();
    }

    public static DatabaseModel fromJSONObject(JSONObject database) {
        DatabaseModel model = new DatabaseModel();
        model.produseAfisate = (long) database.get("produseAfisate");
        JSONArray continut = (JSONArray) database.get("continut");
        for (Object produsObject : continut) {
            Produs produs = Produs.fromJSONObject((JSONObject) produsObject);
            model.continut.add(produs);
        }
        if (database.get("produseNoi") != null) {
            JSONArray produseNoi = (JSONArray) database.get("produseNoi");
            for (Object o : produseNoi) {
                model.newProducts.add(UUID.fromString((String) o));
            }
        }
        if (database.get("categories") != null) {
            JSONArray categories = (JSONArray) database.get("categories");
            for (Object object : categories) {
                Category category = Category.fromJSONObject((JSONObject) object);
                model.categories.add(category);
            }
        }
        if (database.get("alteCulori") != null) {
            JSONArray alteCulori_ = (JSONArray) database.get("alteCulori");
            for (Object object : alteCulori_) {
                AltaCuloare altaCuloare = AltaCuloare.fromJSONObject((JSONObject) object);
                model.alteCulori.add(altaCuloare);
            }
        }
        return model;
    }

    public static DatabaseModel emptyInstance() {
        return new DatabaseModel();
    }

    public JSONObject toJSONObject() {
        JSONObject ob = new JSONObject();
        ob.put("produseAfisate", produseAfisate);
        ob.put("continut", continut);
        ob.put("produseNoi", newProducts.stream().map(id -> id.toString()).collect(Collectors.toList()));
        ob.put("categories", categories);
        ob.put("alteCulori", alteCulori);
        return ob;
    }
}
