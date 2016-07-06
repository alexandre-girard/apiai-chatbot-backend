package fr.tm.ima.pocs.chatbot.service;

public class Societaire {
    String numeroSocietaire;
    
    String civilite;
    
    String nom;
    
    String prenom;

    
    
    public Societaire(String numeroSocietaire, String civilite, String nom, String prenom) {
        super();
        this.numeroSocietaire = numeroSocietaire;
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNumeroSocietaire() {
        return numeroSocietaire;
    }

    public void setNumeroSocietaire(String numeroSocietaire) {
        this.numeroSocietaire = numeroSocietaire;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
