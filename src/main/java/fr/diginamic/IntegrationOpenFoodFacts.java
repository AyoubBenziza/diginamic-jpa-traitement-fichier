package fr.diginamic;

import fr.diginamic.utils.ParseurFood;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.Objects;

public class IntegrationOpenFoodFacts {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("open-food-facts");
    private static final EntityManager em = emf.createEntityManager();
    private static final String dataPath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("open-food-facts.csv")).getPath();

    public static void main(String[] args) {
        ParseurFood.ajoutLigne(em,dataPath);
        if(em != null) {
            em.close();
        }
        if(emf != null) {
            emf.close();
        }
    }
}