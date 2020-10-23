/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.postgresql.util.PGInterval;

/**
 *
 * @author manohisoa
 */
public class Piste {

    private String id;
    private String aeroport;
    private Double longueur;
    private PGInterval tempsDegagement;
    private String pointApiste;
    private String pointBpiste;
    private Double anglepiste;
    private VolPiste volefarany;

    public Piste(String id, String aeroport, Double longueur, PGInterval tempsDegagement, String pointApiste, String pointBpiste, Double anglepiste) {
        this.id = id;
        this.aeroport = aeroport;
        this.longueur = longueur;
        this.tempsDegagement = tempsDegagement;
        this.pointApiste = pointApiste;
        this.pointBpiste = pointBpiste;
        this.anglepiste = anglepiste;
    }

    public Piste() {
    }

    public void insert() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAeroport() {
        return aeroport;
    }

    public void setAeroport(String aeroport) {
        this.aeroport = aeroport;
    }

    public Double getLongueur() {
        return longueur;
    }

    public void setLongueur(Double longueur) {
        this.longueur = longueur;
    }

    public PGInterval getTempsDegagement() {
        return tempsDegagement;
    }

    public void setTempsDegagement(PGInterval tempsDegagement) {
        this.tempsDegagement = tempsDegagement;
    }

    public VolPiste getVolefarany() {
        return volefarany;
    }

    public void setVolefarany(VolPiste volefarany) {
        this.volefarany = volefarany;
    }

    public String getPointApiste() {
        return pointApiste;
    }

    public void setPointApiste(String pointApiste) {
        this.pointApiste = pointApiste;
    }

    public String getPointBpiste() {
        return pointBpiste;
    }

    public void setPointBpiste(String pointBpiste) {
        this.pointBpiste = pointBpiste;
    }

    public Double getAnglepiste() {
        return anglepiste;
    }

    public void setAnglepiste(Double anglepiste) {
        this.anglepiste = anglepiste;
    }

}
