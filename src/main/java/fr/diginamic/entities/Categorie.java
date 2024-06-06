package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", unique = true)
    private String nom;

    @OneToMany(mappedBy = "categorie")
    private Set<Produit> produits = new HashSet<>();

    public Categorie(String nom) {
        this.nom = nom;
    }

    public Categorie() {
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

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public void addProduit(Produit produit) {
        produits.add(produit);
    }

    public void removeProduit(Produit produit) {
        produits.remove(produit);
    }
}
