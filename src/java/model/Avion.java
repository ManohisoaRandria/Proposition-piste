/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotation.Colonne;
import annotation.Entite;
import java.sql.Connection;
import outil.GeneriqueDAO;
import outil.Utilitaire;

/**
 *
 * @author manohisoa
 */
@Entite(table = "avion")
public class Avion {

    @Colonne("id")
    private String id;
    @Colonne("reference")
    private String reference;
    @Colonne("longueurdecol")
    private Double longueurDecol;
    @Colonne("longueuratter")
    private Double longueurAtter;

    public Avion(String id, String reference, Double longueurDecol, Double longueurAtter) {
        this.id = id;
        this.reference = reference;
        this.longueurDecol = longueurDecol;
        this.longueurAtter = longueurAtter;
    }

    public Avion() {
    }

    public void insert(Connection con) throws Exception {
        try {
            this.setId(Utilitaire.formatNumber("AVN" + Utilitaire.getsequence("seq_avion", con), 8));
            GeneriqueDAO.insert(this, con);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLongueurDecol() {
        return longueurDecol;
    }

    public void setLongueurDecol(Double longueurDecol) {
        this.longueurDecol = longueurDecol;
    }

    public Double getLongueurAtter() {
        return longueurAtter;
    }

    public void setLongueurAtter(Double longueurAtter) {
        this.longueurAtter = longueurAtter;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

}
