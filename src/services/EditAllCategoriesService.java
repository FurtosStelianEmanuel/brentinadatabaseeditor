/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import models.database.Category;

/**
 *
 * @author Manel
 */
public class EditAllCategoriesService {

    List<Category> categories;

    public EditAllCategoriesService(List<Category> categories) {
        this.categories = new ArrayList<>();
        categories.forEach(category -> {
            this.categories.add(new Category(category));
        });
    }
}
