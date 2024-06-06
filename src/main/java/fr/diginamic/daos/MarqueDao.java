package fr.diginamic.daos;

import fr.diginamic.entities.Marque;
import jakarta.persistence.EntityManager;

public class MarqueDao {
    private EntityManager em;

    public MarqueDao(EntityManager em) {
        this.em = em;
    }

    public void save(Marque categorie) {
        em.persist(categorie);
    }

    public Marque findByName(String name) {
        return em.createQuery("SELECT m FROM Marque m WHERE m.nom = :name", Marque.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}