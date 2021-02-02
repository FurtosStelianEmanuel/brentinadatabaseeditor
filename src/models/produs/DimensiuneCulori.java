/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.produs;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Manel
 */
public class DimensiuneCulori {

    String dimensiune;
    List<CuloareCodPret> data;

    public void setDimensiune(String dimensiune) {
        this.dimensiune = dimensiune;
    }

    public void setData(List<CuloareCodPret> data) {
        this.data = data;
    }

    public List<CuloareCodPret> getData() {
        return data;
    }

    private DimensiuneCulori() {
        data = new ArrayList<>();
        dimensiune = "";
    }

    public static List<DimensiuneCulori> fromJSONObject(JSONObject coduriSiPreturi) {
        List<DimensiuneCulori> toReturn = new ArrayList<>();
        for (Object key : coduriSiPreturi.keySet()) {
            DimensiuneCulori dc = new DimensiuneCulori();
            JSONObject culoareCoduriSiPret = (JSONObject) coduriSiPreturi.get(key);
            dc.dimensiune = (String) key;
            dc.data = CuloareCodPret.fromJSONObject(culoareCoduriSiPret);
            toReturn.add(dc);
        }
        return toReturn;
    }

    DimensiuneCulori(DimensiuneCulori source) {
        data = new ArrayList<>();
        dimensiune = source.dimensiune;

        source.data.forEach((CuloareCodPret culoareCodPret) -> {
            data.add(new CuloareCodPret(culoareCodPret));
        });
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        JSONObject culoriCuCoduriSiPret = new JSONObject();
        for (CuloareCodPret ccp : data) {
            JSONArray codSiPret = new JSONArray();
            codSiPret.add(ccp.getCod());
            codSiPret.add(ccp.getPret());
            culoriCuCoduriSiPret.put(ccp.getNume(), codSiPret);
        }
        obj.put(dimensiune, culoriCuCoduriSiPret);
        return culoriCuCoduriSiPret;
    }

    public static JSONObject Condens(List<DimensiuneCulori> array) {
        JSONObject obj = new JSONObject();
        for (DimensiuneCulori dc : array) {
            JSONObject manipulated = new JSONObject();
            for (CuloareCodPret ccp : dc.data) {
                JSONArray codSiPret = new JSONArray();
                codSiPret.add(ccp.getCod());
                codSiPret.add(ccp.getPret());
                manipulated.put(ccp.getNume(), codSiPret);
            }
            obj.put(dc.dimensiune, manipulated);
        }
        return obj;
    }

    public static List<DimensiuneCulori> copyFromAnotherInstance(List<DimensiuneCulori> original) {
        List<DimensiuneCulori> toReturn = new ArrayList<>();
        original.forEach(dimensiuneCulori -> {
            toReturn.add(new DimensiuneCulori(dimensiuneCulori));
        });
        return toReturn;
    }

    public static DimensiuneCulori withDimensiuneName(String dimensiuneName){
        DimensiuneCulori dimensiuneCulori=new DimensiuneCulori();
        dimensiuneCulori.setDimensiune(dimensiuneName);
        return dimensiuneCulori;
    }
    
    @Override
    public String toString() {
        return toJSONObject().toJSONString();
    }

    public String getDimensiune() {
        return dimensiune;
    }
}
