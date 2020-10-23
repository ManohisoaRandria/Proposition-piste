/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author manohisoa
 */
public class VolPiste extends Vol {

    private ArrayList<Piste> pistepossible;
    private int nbPiste;
    private String typeVol;

    public VolPiste(String id, String trajet, String avion, Timestamp dateDepart, int etat, Decalage decalage, String pointAvol, String pointBvol, Double anglevol) {
        super(id, trajet, avion, dateDepart, etat, decalage, pointAvol, pointBvol, anglevol);
    }

    public ArrayList<Piste> getPistepossible() {
        return pistepossible;
    }

    public void setPistepossible(ArrayList<Piste> pistepossible) {
        this.pistepossible = pistepossible;
    }

    public int getNbPiste() {
        return nbPiste;
    }

    public void setNbPiste(int nbPiste) {
        this.nbPiste = nbPiste;
    }

    public String getTypeVol() {
        return typeVol;
    }

    public void setTypeVol(String typeVol) {
        this.typeVol = typeVol;
    }
}
