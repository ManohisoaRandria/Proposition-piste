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
@Entite(table = "decalage")
public class Decalage {

    @Colonne("vol")
    private String vol;
    @Colonne("decalagemanuel")
    private PGInterval decalageManuel;
    @Colonne("decalage")
    private PGInterval decalage;

    public Decalage(String vol, PGInterval decalageManuel, PGInterval decalage) {
        this.vol = vol;
        this.decalageManuel = decalageManuel;
        this.decalage = decalage;
    }

    public PGInterval getDecalage() {
        return decalage;
    }

    public Decalage() {
    }

    public void setDecalage(PGInterval decalage) {
        this.decalage = decalage;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public PGInterval getDecalageManuel() {
        return decalageManuel;
    }

    public void setDecalageManuel(PGInterval decalageManuel) {
        this.decalageManuel = decalageManuel;
    }
}
