package fr.diginamic.daos;

import fr.diginamic.entities.Categorie;
import jakarta.persistence.EntityManager;

public class CategorieDao {
    private final EntityManager em;

    public CategorieDao(EntityManager em) {
        this.em = em;
    }

    public void save(Categorie categorie) {
        em.persist(categorie);
    }

    public Categorie findByName(String name) {
        return em.createQuery("SELECT c FROM Categorie c WHERE c.nom = :name", Categorie.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
