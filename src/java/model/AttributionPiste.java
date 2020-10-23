/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotation.Colonne;
import annotation.Entite;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.postgresql.util.PGInterval;
import outil.GeneriqueDAO;

/**
 *
 * @author manohisoa
 */
@Entite(table = "attributionPiste")
public class AttributionPiste {

    @Colonne("id")
    private String id;
    @Colonne("vol")
    private String vol;
    @Colonne("piste")
    private String piste;
    @Colonne("dateVrai")
    private Timestamp dateVrai;
    @Colonne("decalage")
    private PGInterval decalage;
    @Colonne("dateproposition")
    private Timestamp dateproposition;
    @Colonne("etat")
    private int etat;

    public AttributionPiste(String id, String vol, String piste, Timestamp dateVrai, PGInterval decalage, Timestamp dateproposition, int etat) {
        this.id = id;
        this.vol = vol;
        this.piste = piste;
        this.dateVrai = dateVrai;
        this.decalage = decalage;
        this.dateproposition = dateproposition;
        this.etat = etat;
    }

    public AttributionPiste() {
    }

    public void annuler(Connection con) throws Exception {
        try {
            GeneriqueDAO.update("attributionpiste", " etat=10", " id='" + this.id + "'", con);
        } catch (Exception e) {
            throw e;
        }
    }

    public String decalageAffiche() {
        String hour = this.decalage.getHours() < 10 ? "0" + this.decalage.getHours() : "" + this.decalage.getHours();
        String min = this.decalage.getMinutes() < 10 ? "0" + this.decalage.getMinutes() : "" + this.decalage.getMinutes();
        String sec = this.decalage.getSeconds() < 10 ? "0" + (int) this.decalage.getSeconds() : "" + this.decalage.getSeconds();
        return "" + hour + ":" + min + ":" + sec + "";
    }

    public void decalerTemporaire(VolPiste vp, Piste pst) throws SQLException {
//        PGInterval marge = new PGInterval("00:00:00");
        //jerena hoe ao anatin vp.depart+temps de degagement ve le heure iny
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(vp.getDateDepart().getTime());
        pst.getTempsDegagement().add(cal);

        Timestamp occupation = new Timestamp(cal.getTimeInMillis());
        //ra ao anatiny de decaler vers la gauche

        PGInterval decalaget = moins(occupation, this.dateVrai);
//            marge.add(decalaget);
        this.setDecalage(decalaget);
        //manampy anle datevrai amzay
        Calendar cal2 = new GregorianCalendar();
        cal2.setTimeInMillis(this.dateVrai.getTime());
        decalaget.add(cal2);
        this.setDateVrai(new Timestamp(cal2.getTimeInMillis()));

        //ra tsy ao de decalerna vers la droite fa asina marge??
    }

    public PGInterval moins(Timestamp t1, Timestamp t2) throws SQLException {
        long milliseconds = t1.getTime() - t2.getTime();
        if (milliseconds < 0) {
            System.out.println("ato ee /****************************************************************qszdqzdaqzd**/");
            return new PGInterval("00:00:00");
        } else {
            double seconds = milliseconds / 1000;

            int hours = (int) seconds / 3600;
            int minutes = (int) (seconds % 3600) / 60;
            seconds = (double) (seconds % 3600) % 60;
            String h = transform(hours);
            String min = transform(minutes);
            String s = transformd(seconds);

            return new PGInterval("" + h + ":" + min + ":" + s);

        }
    }

    public String transform(int nb) {
        String res = "";
        if (nb < 10) {
            res += "0" + nb;
        } else {
            res += "" + nb;
        }
        return res;
    }

    public String transformd(double nb) {
        String res = "";
        if (nb < 10) {
            res += "0" + nb;
        } else {
            res += "" + nb;
        }
        return res;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
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

    public String getPiste() {
        return piste;
    }

    public void setPiste(String piste) {
        this.piste = piste;
    }

    public Timestamp getDateVrai() {
        return dateVrai;
    }

    public void setDateVrai(Timestamp dateVrai) {
        this.dateVrai = dateVrai;
    }

    public PGInterval getDecalage() {
        return decalage;
    }

    public void setDecalage(PGInterval decalage) {
        this.decalage = decalage;
    }

    public Timestamp getDateproposition() {
        return dateproposition;
    }

    public void setDateproposition(Timestamp dateproposition) {
        this.dateproposition = dateproposition;
    }

}
