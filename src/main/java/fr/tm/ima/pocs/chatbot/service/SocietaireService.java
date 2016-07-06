package fr.tm.ima.pocs.chatbot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SocietaireService {
    
    public Societaire getSocietaire(String numeroSocietaire){
        List<Societaire> societaires = new  ArrayList<Societaire>();
        societaires.add(new Societaire("1", "Mr", "GIRARD", "Alexandre"));
        societaires.add(new Societaire("2", "Mr", "DUPOND", "Jean"));
        societaires.add(new Societaire("3", "Mr", "DUPONT", "Rene"));
        societaires.add(new Societaire("4", "Mm", "DURAND", "Catherine"));
        
        int index = 0;
        
        try {
            index = Integer.parseInt(numeroSocietaire);
        } catch (Exception e) {
            index = 1;
        }
        
        Societaire societaire = null;
        try {
            societaire = societaires.get(index-1);    
        } catch (IndexOutOfBoundsException e) {
            societaire = societaires.get(2);
        }
        
        return societaire;
    }
}
