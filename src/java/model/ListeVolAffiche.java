/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author manohisoa
 */
public class ListeVolAffiche {

    private String idVol;
    private String avion;
    private Timestamp dateHeure;
    private String type;

    public ListeVolAffiche(String idVol, String avion, Timestamp dateHeure, String type) {
        this.idVol = idVol;
        this.avion = avion;
        this.dateHeure = dateHeure;
        this.type = type;
    }

    

    public Timestamp getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Timestamp dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getIdVol() {
        return idVol;
    }

    public void setIdVol(String idVol) {
        this.idVol = idVol;
    }

    public String getAvion() {
        return avion;
    }

    public void setAvion(String avion) {
        this.avion = avion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
   
}
