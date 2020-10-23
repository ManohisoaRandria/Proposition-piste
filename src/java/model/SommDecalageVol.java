/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotation.Colonne;
import annotation.Entite;
import org.postgresql.util.PGInterval;

/**
 *
 * @author manohisoa
 */
@Entite(table = "sum_decalage_vol")
public class SommDecalageVol {
    
    @Colonne("id")
    private String vol;
    @Colonne("somme")
    private PGInterval somme;
    @Colonne("decalmanuel")
    private PGInterval decalmanuel;

    public SommDecalageVol() {
    }

    public SommDecalageVol(String vol, PGInterval somme, PGInterval decalmanuel) {
        this.vol = vol;
        this.somme = somme;
        this.decalmanuel = decalmanuel;
    }

    public PGInterval getDecalmanuel() {
        return decalmanuel;
    }

    public void setDecalmanuel(PGInterval decalmanuel) {
        this.decalmanuel = decalmanuel;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public PGInterval getSomme() {
        return somme;
    }

    public void setSomme(PGInterval somme) {
        this.somme = somme;
    }
    
}
