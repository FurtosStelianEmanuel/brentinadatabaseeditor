/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import models.database.AltaCuloare;
import services.interfaces.EditAlteCuloriInterface;

/**
 *
 * @author Manel
 */
public class EditAlteCuloriService implements EditAlteCuloriInterface {

    public EditAlteCuloriApplicator applicator;
    public final List<AltaCuloare> alteCuloriCopy;
    public List<String> productNames;

    public EditAlteCuloriService(List<AltaCuloare> alteCulori, List<String> productNames) {
        alteCuloriCopy = new ArrayList<>();
        alteCulori.forEach(ac -> {
            alteCuloriCopy.add(new AltaCuloare(ac));
        });
        this.productNames = new ArrayList<>(productNames);
    }

    public void setApplicator(EditAlteCuloriApplicator applicator) {
        this.applicator = applicator;
    }

    @Override
    public void addAltaCuloare(String partialName) throws Exception {
        //cauta daca exista pe undeva si daca da, bag o aci, nu baga direct orice ciorba aici ca n are rost :)
        List<String> numeMatches = productNames.stream().filter(p
                -> {
            return alteCuloriCopy.stream().filter(ac -> ac.nume.equals(p)).findAny().orElse(null) == null
                    && p.toLowerCase().contains(partialName.toLowerCase());
        }
        ).collect(Collectors.toList());

        if (numeMatches.isEmpty()) {
            throw new Exception("Nu am gasit acea culoare sau a fost deja adaugata");
        }

        String selectedMatch = numeMatches.get(0);
        if (numeMatches.size() > 0) {
            Object choice = JOptionPane.showInputDialog(null, "Culori disponibile",
                    "Alegeti o culoare", JOptionPane.INFORMATION_MESSAGE, null,
                    numeMatches.toArray(), numeMatches.toArray()[0]);
            if (choice == null) {
                throw new Exception("Ai anulat operatia");
            }
            selectedMatch = (String) choice;
        }

        final String lookFor = selectedMatch;
        if (alteCuloriCopy.stream().filter(p -> p.nume.equals(lookFor)).findAny().orElse(null) != null) {
            throw new Exception("Traducerea pentru aceasta culoare a fost deja adaugata");
        }

        alteCuloriCopy.add(AltaCuloare.fromStrings(selectedMatch, selectedMatch + "(en)", selectedMatch + "(hu)", selectedMatch + "(de)"));
        applicator.updateTable(alteCuloriCopy);
    }
}
