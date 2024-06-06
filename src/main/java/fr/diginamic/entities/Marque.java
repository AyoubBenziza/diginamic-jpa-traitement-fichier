package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "marque")
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", unique = true)
    private String nom;

    @OneToMany(mappedBy = "marque")
    private Set<Produit> produits;

    public Marque(String nom) {
        this.nom = nom;
    }

    public Marque() {
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
