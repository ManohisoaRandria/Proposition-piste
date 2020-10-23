/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.postgresql.util.PGInterval;
import outil.GeneriqueDAO;
import outil.Utilitaire;
import utilitaires.Connexion;
import utilitaires.Outil;

/**
 *
 * @author manohisoa
 */
public class Proposition {

    private ArrayList<AttributionPiste> attribution;

    public Proposition(ArrayList<AttributionPiste> attribution) {
        this.attribution = attribution;
    }

    public Proposition() {
    }

    public void valider(Connection con) throws Exception {
        boolean conAnatiny = false;
        try {
            if (con == null) {
                con = Connexion.getConnex();
                con.setAutoCommit(false);
                conAnatiny = true;
            }

            //mjeri aloha hoe efa misy piste ve
            String condition = " vol in " + getIdIn() + " and etat!=10";
            Timestamp dateprop = Outil.getCurrentTimeStamp();
            AttributionPiste[] attrtemp = (AttributionPiste[]) GeneriqueDAO.find(AttributionPiste.class, condition, con);
            String condition2 = " id in " + getIdIn() + " order by id";
            SommDecalageVol[] decal = (SommDecalageVol[]) GeneriqueDAO.find(SommDecalageVol.class, condition2, con);
            //raha ao anaty attribution de hatao update
            for (int i = 0; i < this.attribution.size(); i++) {
                //apina daty
                this.attribution.get(i).setDateproposition(dateprop);
                this.attribution.get(i).setId(Utilitaire.formatNumber("ATR" + Utilitaire.getsequence("seq_attributionpiste", con), 8));
                String ao = contains(attrtemp, this.attribution.get(i).getVol());
                if (ao == null) {
                    //raha tsy ao de inserer
                    GeneriqueDAO.insert(this.attribution.get(i), con);
                } else {
                    //update
                    GeneriqueDAO.update("attributionPiste", " piste='" + this.attribution.get(i).getPiste() + "',datevrai='" + this.attribution.get(i).getDateVrai() + "'",
                            " id='" + ao + "'", con);
                }
                //decalage
                //alaina ny decalage actuel
                for (SommDecalageVol sommDecalageVol : decal) {
                    if (sommDecalageVol.getVol().equals(this.attribution.get(i).getVol())) {
                        //manao operation kely amniny
                        PGInterval taloha = sommDecalageVol.getSomme();
                        PGInterval amzao = this.getAttribution().get(i).getDecalage();
                        PGInterval finaldecalage = updateDecalage(taloha, amzao);
                        //atsofoka ny decalage amzao
                        GeneriqueDAO.insert(new Decalage(this.attribution.get(i).getVol(),
                                new PGInterval("00:00:00"), finaldecalage), con);
                        break;
                    }
                }

            }
            if (conAnatiny) {
                con.commit();
            }

        } catch (Exception e) {
            if (conAnatiny && con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (conAnatiny && con != null) {
                con.close();
            }
        }
    }

    //mamerina id de iny no updatena
    public String contains(AttributionPiste[] attrtemp, String test) {

        for (AttributionPiste attributionPiste : attrtemp) {
            if (test.equals(attributionPiste.getVol())) {
                return attributionPiste.getId();
            }
        }
        return null;
    }

    public String getIdIn() {
        String test = "(";
        for (int i = 0; i < this.attribution.size(); i++) {
            if (i != this.attribution.size() - 1) {
                test += "'" + this.attribution.get(i).getVol() + "',";
            } else {
                test += "'" + this.attribution.get(i).getVol() + "'";
            }
        }
        return test += ")";
    }

    public ArrayList<AttributionPiste> getAttribution() {
        return attribution;
    }

    public void setAttribution(ArrayList<AttributionPiste> attribution) {
        this.attribution = attribution;
    }

    private PGInterval updateDecalage(PGInterval taloha, PGInterval amzao) throws SQLException {
        if (taloha.equals(amzao)) {
            return new PGInterval("00:00:00");
        }
        String hour = taloha.getHours() < 10 ? "0" + taloha.getHours() : "" + taloha.getHours();
        String min = taloha.getMinutes() < 10 ? "0" + taloha.getMinutes() : "" + taloha.getMinutes();
        String sec = taloha.getSeconds() < 10 ? "0" + (int) taloha.getSeconds() : "" + taloha.getSeconds();
        PGInterval inter3 = new PGInterval("-" + hour + ":" + min + ":" + sec + "");
        amzao.add(inter3);
        return inter3;
    }

}
