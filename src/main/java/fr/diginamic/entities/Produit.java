package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "nutriscore", length = 1)
    private String nutriscore;

    @ManyToOne
    private Marque marque;

    @ManyToOne
    private Categorie categorie;

    @ManyToMany
    @JoinTable(name = "produit_allergene",
            joinColumns = @JoinColumn(name = "id_produit", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_allergene", referencedColumnName = "id"))
    private Set<Allergene> allergene = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "produit_ingredient",
            joinColumns = @JoinColumn(name = "id_produit", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_ingredient", referencedColumnName = "id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    public Produit(String nom, Marque marque, String nutriscore, Categorie categorie, Set<Ingredient> ingredients, Set<Allergene> allergene) {
        this.nom = nom;
        this.marque = marque;
        this.nutriscore = nutriscore;
        this.categorie = categorie;
        this.ingredients = ingredients;
        this.allergene = allergene;
    }

    public Produit() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNutriscore(String nutriscore) {
        this.nutriscore = nutriscore;
    }

    public String getNutriscore() {
        return nutriscore;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setAllergene(Set<Allergene> allergene) {
        this.allergene = allergene;
    }

    public Set<Allergene> getAllergene() {
        return allergene;
    }

    public void addAllergene(Allergene allergene) {
        this.allergene.add(allergene);
    }

    public void removeAllergene(Allergene allergene) {
        this.allergene.remove(allergene);
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
    }
}
