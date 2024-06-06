package fr.diginamic.daos;

import fr.diginamic.entities.Produit;
import jakarta.persistence.EntityManager;

public class ProduitDao {
    private final EntityManager em;

    public ProduitDao(EntityManager em) {
        this.em = em;
    }

    public void save(Produit produit) {
        em.persist(produit);
    }

    public Produit findByName(String name) {
        return em.createQuery("SELECT p FROM Produit p WHERE p.nom = :name", Produit.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
