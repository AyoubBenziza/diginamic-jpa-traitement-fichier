package fr.diginamic.daos;

import fr.diginamic.entities.Allergene;
import jakarta.persistence.EntityManager;

public class AllergeneDao {
    private final EntityManager em;

    public AllergeneDao(EntityManager em) {
        this.em = em;
    }

    public void save(Allergene allergene) {
        em.persist(allergene);
    }

    public Allergene findByName(String name) {
        return em.createQuery("SELECT a FROM Allergene a WHERE a.nom = :name", Allergene.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
