package fr.diginamic.utils;

import fr.diginamic.daos.*;
import fr.diginamic.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ParseurFood {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ParseurFood.class);
    private static final Logger errorLogger = org.slf4j.LoggerFactory.getLogger("errorLogger");
    public static void ajoutLigne(EntityManager em, String path) {
        try {
            Reader in = new FileReader(path);
            Iterable<CSVRecord> records = CSVFormat.Builder
            .create(CSVFormat.EXCEL)
            .setDelimiter('|')
            .setHeader("categorie", "marque", "nom", "nutritionGradeFr", "ingredients", "energie100g","graisse100g","sucres100g","fibres100g","proteines100g","sel100g","vitA100g","vitD100g","vitE100g","vitK100g","vitC100g","vitB1100g","vitB2100g","vitPP100g","vitB6100g","vitB9100g","vitB12100g","calcium100g","magnesium100g","iron100g","fer100g","betaCarotene100g","presenceHuilePalme","allergenes") // specify all the headers explicitly
            .setSkipHeaderRecord(true)
            .build()
            .parse(in);

            CategorieDao categorieDao = new CategorieDao(em);
            MarqueDao marqueDao = new MarqueDao(em);
            ProduitDao produitDao = new ProduitDao(em);
            IngredientDao ingredientDao = new IngredientDao(em);
            AllergeneDao allergeneDao = new AllergeneDao(em);

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            long startTime = System.currentTimeMillis();
            int count = 0;

            for (CSVRecord record : records) {
                String recordCategory = trimAndLowercase(record.get("categorie"));
                Categorie categorie = categorieDao.findByName(recordCategory);
                if (categorie == null) {
                    categorie = new Categorie(recordCategory);
                    categorieDao.save(categorie);
                }

                String recordBrand = trimAndLowercase(record.get("marque"));
                Marque marque = marqueDao.findByName(recordBrand);
                if (marque == null) {
                    marque = new Marque(recordBrand);
                    marqueDao.save(marque);
                }

                String name = trimAndLowercase(record.get("nom"));

                String nutriscore = trimAndLowercase(record.get("nutritionGradeFr"));

                Set<Ingredient> ingredients = Arrays.stream(trimAndLowercase(record.get("ingredients")).replaceAll("[._*]", "").split(",|-|:"))
                .map(String::trim) // to remove any leading or trailing spaces after split
                .map(ingredientName -> {
                    if (ingredientName.length() > 50) {
                        errorLogger.error("Ingredient name '{}' exceeds the maximum length", ingredientName);
                        return null;
                    }
                    Ingredient ingredient = ingredientDao.findByName(ingredientName);
                    if (ingredient == null) {
                        ingredient = new Ingredient(ingredientName);
                        ingredientDao.save(ingredient);
                    }
                    return ingredient;
                })
                .filter(ingredient -> ingredient != null) // ignore ingredients that exceed the column length
                .collect(Collectors.toSet());

                Set<Allergene> allergens = Arrays.stream(trimAndLowercase(record.get("allergenes")).split(","))
                .map(allergen -> allergen.contains(":") ? allergen.split(":") : new String[]{allergen, "fr"})
                .map(parts -> {
                    String langue = parts[0];
                    String allergeneName = parts[1];
                    Allergene allergene = allergeneDao.findByName(allergeneName);
                    if (allergene == null) {
                        allergene = new Allergene(allergeneName, langue);
                        allergeneDao.save(allergene);
                    }
                    return allergene;
                })
                .collect(Collectors.toSet());

                Produit produit = new Produit(name, marque, nutriscore, categorie, ingredients, allergens);
                produitDao.save(produit);
            }

            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;

            long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
            long milliseconds = duration - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));
            String output = String.format("%-15s %-15d %-15d %-15d %-15d %-15d", "Execution time:", minutes,
                    seconds,
                    milliseconds, count, count % 500);

            // Pad the output with spaces to a fixed length
            output = String.format("%-80s", output);

            System.out.print("\r" + output);

            transaction.commit();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private static String trimAndLowercase(String str) {
        return str.trim().toLowerCase();
    }
}
