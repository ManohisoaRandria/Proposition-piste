/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.mapping;

import annotation.Colonne;
import annotation.Entite;
import java.sql.Timestamp;
import org.postgresql.util.PGInterval;

/**
 *
 * @author manohisoa
 */
@Entite(table = "vol_piste")
public class VolPisteDetail {

    @Colonne("id")
    private String id;
    @Colonne("trajet")
    private String trajet;
    @Colonne("avion")
    private String avion;
    @Colonne("duree")
    private PGInterval duree;
    @Colonne("datedepart")
    private Timestamp datedepart;
    @Colonne("etat")
    private int etat;
    @Colonne("pointavol")
    private String pointAvol;
    @Colonne("pointbvol")
    private String pointBvol;
    @Colonne("anglevol")
    private Double anglevol;
    @Colonne("sommedecalage")
    private PGInterval sommedecalage;
    @Colonne("decalmanuel")
    private PGInterval decalmanuel;
    @Colonne("idavion")
    private String idavion;
    @Colonne("reference")
    private String reference;
    @Colonne("longueurdecol")
    private Double longueurdecol;
    @Colonne("longueuratter")
    private Double longueuratter;
    @Colonne("idtrajet")
    private String idtrajet;
    @Colonne("depart")
    private String depart;
    @Colonne("arrivee")
    private String arrivee;
    @Colonne("ville")
    private String ville;
    @Colonne("idpiste")
    private String idpiste;
    @Colonne("longueur")
    private Double longueurPiste;
    @Colonne("tempsdegagement")
    private PGInterval tempsdegagement;
    @Colonne("typevol")
    private String typevol;
    @Colonne("toerana")
    private String toerana;
     @Colonne("pointapiste")
    private String pointApiste;
    @Colonne("pointbpiste")
    private String pointBpiste;
    @Colonne("anglepiste")
    private Double anglePiste;

    public VolPisteDetail(String id, String trajet, String avion, PGInterval duree, Timestamp datedepart, int etat, String pointAvol, String pointBvol, Double anglevol, PGInterval sommedecalage, PGInterval decalmanuel, String idavion, String reference, Double longueurdecol, Double longueuratter, String idtrajet, String depart, String arrivee, String ville, String idpiste, Double longueurPiste, PGInterval tempsdegagement, String typevol, String toerana, String pointApiste, String pointBpiste, Double anglePiste) {
        this.id = id;
        this.trajet = trajet;
        this.avion = avion;
        this.duree = duree;
        this.datedepart = datedepart;
        this.etat = etat;
        this.pointAvol = pointAvol;
        this.pointBvol = pointBvol;
        this.anglevol = anglevol;
        this.sommedecalage = sommedecalage;
        this.decalmanuel = decalmanuel;
        this.idavion = idavion;
        this.reference = reference;
        this.longueurdecol = longueurdecol;
        this.longueuratter = longueuratter;
        this.idtrajet = idtrajet;
        this.depart = depart;
        this.arrivee = arrivee;
        this.ville = ville;
        this.idpiste = idpiste;
        this.longueurPiste = longueurPiste;
        this.tempsdegagement = tempsdegagement;
        this.typevol = typevol;
        this.toerana = toerana;
        this.pointApiste = pointApiste;
        this.pointBpiste = pointBpiste;
        this.anglePiste = anglePiste;
    }

   

   

    public VolPisteDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrajet() {
        return trajet;
    }

    public void setTrajet(String trajet) {
        this.trajet = trajet;
    }

    public String getAvion() {
        return avion;
    }

    public void setAvion(String avion) {
        this.avion = avion;
    }

    public PGInterval getDuree() {
        return duree;
    }

    public void setDuree(PGInterval duree) {
        this.duree = duree;
    }

    public Timestamp getDatedepart() {
        return datedepart;
    }

    public void setDatedepart(Timestamp datedepart) {
        this.datedepart = datedepart;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public PGInterval getSommedecalage() {
        return sommedecalage;
    }

    public void setSommedecalage(PGInterval sommedecalage) {
        this.sommedecalage = sommedecalage;
    }

    public String getIdavion() {
        return idavion;
    }

    public void setIdavion(String idavion) {
        this.idavion = idavion;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getLongueurdecol() {
        return longueurdecol;
    }

    public void setLongueurdecol(Double longueurdecol) {
        this.longueurdecol = longueurdecol;
    }

    public Double getLongueuratter() {
        return longueuratter;
    }

    public void setLongueuratter(Double longueuratter) {
        this.longueuratter = longueuratter;
    }

    public String getIdtrajet() {
        return idtrajet;
    }

    public void setIdtrajet(String idtrajet) {
        this.idtrajet = idtrajet;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getIdpiste() {
        return idpiste;
    }

    public void setIdpiste(String idpiste) {
        this.idpiste = idpiste;
    }

    public Double getLongueurPiste() {
        return longueurPiste;
    }

    public void setLongueurPiste(Double longueurPiste) {
        this.longueurPiste = longueurPiste;
    }

    public PGInterval getTempsdegagement() {
        return tempsdegagement;
    }

    public void setTempsdegagement(PGInterval tempsdegagement) {
        this.tempsdegagement = tempsdegagement;
    }

    public PGInterval getDecalmanuel() {
        return decalmanuel;
    }

    public void setDecalmanuel(PGInterval decalmanuel) {
        this.decalmanuel = decalmanuel;
    }

    public String getTypevol() {
        return typevol;
    }

    public void setTypevol(String typevol) {
        this.typevol = typevol;
    }

    public String getToerana() {
        return toerana;
    }

    public void setToerana(String toerana) {
        this.toerana = toerana;
    }

    public String getPointAvol() {
        return pointAvol;
    }

    public void setPointAvol(String pointAvol) {
        this.pointAvol = pointAvol;
    }

    public String getPointBvol() {
        return pointBvol;
    }

    public void setPointBvol(String pointBvol) {
        this.pointBvol = pointBvol;
    }

    public Double getAnglevol() {
        return anglevol;
    }

    public void setAnglevol(Double anglevol) {
        this.anglevol = anglevol;
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

    public Double getAnglePiste() {
        return anglePiste;
    }

    public void setAnglePiste(Double anglePiste) {
        this.anglePiste = anglePiste;
    }
}
