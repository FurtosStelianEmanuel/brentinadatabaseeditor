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

    private DatabaseModel() {
        produseAfisate = 2;
        continut = new ArrayList<>();
        newProducts = new ArrayList<>();
        categories = new ArrayList<>();
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
            model.newProducts = (List<UUID>) database.get("produseNoi");
        }
        if (database.get("categories") != null) {
            JSONArray categories = (JSONArray) database.get("categories");
            for (Object object : categories) {
                Category category = Category.fromJSONObject((JSONObject) object);
                model.categories.add(category);
            }
        }
        return model;
    }

    public static DatabaseModel emptyInstance() {
        DatabaseModel model = new DatabaseModel();
        model.continut = new ArrayList<>();
        model.produseAfisate = 3;
        model.newProducts = new ArrayList<>();
        return model;
    }

    public JSONObject toJSONObject() {
        JSONObject ob = new JSONObject();
        ob.put("produseAfisate", produseAfisate);
        ob.put("continut", continut);
        ob.put("produseNoi", newProducts.stream().map(id -> id.toString()).collect(Collectors.toList()));
        ob.put("categories", categories);
        return ob;
    }
}
