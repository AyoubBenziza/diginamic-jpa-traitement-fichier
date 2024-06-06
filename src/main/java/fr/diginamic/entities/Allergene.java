package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "allergene")
public class Allergene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", unique = true)
    private String nom;

    @Column(name = "langue", columnDefinition = "VARCHAR(2) DEFAULT 'fr'")
    private String langue = "fr";

    @ManyToMany
    @JoinTable(name = "produit_allergene",
            joinColumns = @JoinColumn(name = "id_allergene", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_produit", referencedColumnName = "id"))
    private Set<Produit> produits;

    public Allergene(String nom, String langue) {
        this.nom = nom;
        this.langue = langue;
    }

    public Allergene() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
