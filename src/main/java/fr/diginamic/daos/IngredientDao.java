package fr.diginamic.daos;

import fr.diginamic.entities.Ingredient;
import jakarta.persistence.EntityManager;

public class IngredientDao {
    private final EntityManager em;

    public IngredientDao(EntityManager em) {
        this.em = em;
    }

    public void save(Ingredient ingredient) {
        em.persist(ingredient);
    }

    public Ingredient findByName(String name) {
        return em.createQuery("SELECT i FROM Ingredient i WHERE i.nom = :name", Ingredient.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
