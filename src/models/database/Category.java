/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.database;

import java.util.List;
import java.util.UUID;
import models.produs.TranslateCategory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Manel
 */
public class Category {

    public static Category emptyInstance() {
        return new Category();
    }

    String name;
    TranslateCategory translate;
    UUID id;
    long hierarchyIndex;
    UUID parentId;

    private Category() {
        name = "";
        translate = TranslateCategory.emptyInstance();
        id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        hierarchyIndex = -1;
        parentId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public static Category fromJSONObject(JSONObject object) {
        Category toReturn = new Category();
        toReturn.name = (String) object.get("name");
        toReturn.translate = TranslateCategory.fromJSONObject((JSONObject) object.get("translate"));
        toReturn.id = UUID.fromString((String) object.get("id"));
        toReturn.hierarchyIndex = (long) object.get("hierarchyIndex");
        toReturn.parentId = UUID.fromString((String) object.get("parentId"));
        return toReturn;
    }

    public Category(Category category) {
        this.hierarchyIndex = category.hierarchyIndex;
        this.name = category.name;
        this.id = category.id;
        this.parentId = category.parentId;
        this.translate = new TranslateCategory(category.translate);
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        object.put("name", name);
        JSONObject translateJSONObject = new JSONObject();
        translateJSONObject.put("en", translate.getEn());
        translateJSONObject.put("hu", translate.getHu());
        translateJSONObject.put("de", translate.getDe());
        object.put("translate", translateJSONObject);
        object.put("id", id.toString());
        object.put("hierarchyIndex", hierarchyIndex);
        object.put("parentId", parentId.toString());
        return object.toJSONString();
    }

    public static JSONArray Condens(List<Category> categories) {
        JSONArray toReturn = new JSONArray();
        for (Category category : categories) {
            JSONObject object = new JSONObject();
            object.put("name", category.name);
            JSONObject translateJSONObject = new JSONObject();
            translateJSONObject.put("en", category.translate.getEn());
            translateJSONObject.put("hu", category.translate.getHu());
            translateJSONObject.put("de", category.translate.getDe());
            object.put("translate", translateJSONObject);
            object.put("id", category.id.toString());
            object.put("hierarchyIndex", category.hierarchyIndex);
            object.put("parentId", category.parentId.toString());
            toReturn.add(object);
        }
        return toReturn;
    }

    public String getName() {
        return name;
    }

    public TranslateCategory getTranslate() {
        return translate;
    }

    public UUID getId() {
        return id;
    }

    public long getHierarchyIndex() {
        return hierarchyIndex;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTranslate(TranslateCategory translate) {
        this.translate = translate;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setHierarchyIndex(long hierarchyIndex) {
        this.hierarchyIndex = hierarchyIndex;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }
}
