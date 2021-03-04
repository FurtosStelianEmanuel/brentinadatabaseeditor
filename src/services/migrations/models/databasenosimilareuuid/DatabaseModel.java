/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.migrations.models.databasenosimilareuuid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.migrations.models.produsnosimilareuuid.Produs;

/**
 *
 * @author Manel
 */
public class DatabaseModel {

    public long produseAfisate;
    public List<Produs> continut;
    public List<UUID> newProducts;

    private DatabaseModel() {
        produseAfisate = 2;
        continut = new ArrayList<>();
        newProducts = new ArrayList<>();
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
        List<String> uuids = newProducts.stream().map(id -> id.toString()).collect(Collectors.toList());
        ob.put("produseNoi", uuids);
        return ob;
    }
}
