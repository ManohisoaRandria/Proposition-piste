/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotation.Colonne;
import annotation.Entite;
import java.sql.Timestamp;

/**
 *
 * @author manohisoa
 */
@Entite(table = "effectif")
public class Effectif {

    @Colonne("id")
    private String id;
    @Colonne("vol")
    private String vol;
    @Colonne("dateeffectif")
    private Timestamp dateEffectif;

    public Effectif(String id, String vol, Timestamp dateEffectif) {
        this.id = id;
        this.vol = vol;
        this.dateEffectif = dateEffectif;
    }

    public Effectif() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public Timestamp getDateEffectif() {
        return dateEffectif;
    }

    public void setDateEffectif(Timestamp dateEffectif) {
        this.dateEffectif = dateEffectif;
    }
}
