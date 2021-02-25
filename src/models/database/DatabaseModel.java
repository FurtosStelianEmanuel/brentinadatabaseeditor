/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.database;

import java.util.ArrayList;
import java.util.List;
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

    private DatabaseModel() {
        produseAfisate = 2;
        continut = new ArrayList<>();
    }

    public static DatabaseModel fromJSONObject(JSONObject database) {
        DatabaseModel model = new DatabaseModel();
        model.produseAfisate = (long) database.get("produseAfisate");
        JSONArray continut = (JSONArray) database.get("continut");
        for (Object produsObject : continut) {
            Produs produs = Produs.fromJSONObject((JSONObject) produsObject);
            model.continut.add(produs);
        }
        return model;
    }

    public static DatabaseModel emptyInstance() {
        DatabaseModel model = new DatabaseModel();
        model.continut = new ArrayList<>();
        model.produseAfisate = 3;
        return model;
    }

    public JSONObject toJSONObject() {
        JSONObject ob = new JSONObject();
        ob.put("produseAfisate", produseAfisate);
        ob.put("continut", continut);
        return ob;
    }
}
