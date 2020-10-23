/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotation.Colonne;
import annotation.Entite;
import java.sql.Connection;
import java.sql.Timestamp;
import model.mapping.VolPisteDetail;
import org.postgresql.util.PGInterval;
import outil.GeneriqueDAO;
import outil.Utilitaire;
import utilitaires.Connexion;
import utilitaires.Outil;

/**
 *
 * @author manohisoa
 */
@Entite(table = "vol")
public class Vol {

    @Colonne("id")
    private String id;
    @Colonne("trajet")
    private String trajet;
    @Colonne("avion")
    private String avion;
    @Colonne("datedepart")
    private Timestamp dateDepart;
    @Colonne("decalage")
    private int etat;
    private Decalage decalage;
    @Colonne("pointavol")
    private String pointAvol;
    @Colonne("pointbvol")
    private String pointBvol;
    @Colonne("anglevol")
    private Double anglevol;

    public Vol(String id, String trajet, String avion, Timestamp dateDepart, int etat, Decalage decalage, String pointAvol, String pointBvol, Double anglevol) {
        this.id = id;
        this.trajet = trajet;
        this.avion = avion;
        this.dateDepart = dateDepart;
        this.etat = etat;
        this.decalage = decalage;
        this.pointAvol = pointAvol;
        this.pointBvol = pointBvol;
        this.anglevol = anglevol;
    }

    public Vol() {
    }

    public Timestamp[] decaler(PGInterval amount) throws Exception {
        Timestamp[] daty = null;
        Connection con = null;
        try {
            con = Connexion.getConnex();
            con.setAutoCommit(false);
            AttributionPiste[] attrtemp = (AttributionPiste[]) GeneriqueDAO.find(AttributionPiste.class, " vol='" + this.id + "' and etat!=10", con);
            //raha ohatra ka tsy mbola nisy piste ni attribuerna azy de tode decalerna
            if (attrtemp.length == 0) {
                GeneriqueDAO.insert(new Decalage(this.id, amount, new PGInterval("00:00:00")), con);
            } else {
                //alaina ilay proposition rehetra ray proposition aminy iny
                attrtemp = (AttributionPiste[]) GeneriqueDAO.find(AttributionPiste.class,
                        " dateproposition='" + attrtemp[0].getDateproposition().toString() + "' and etat!=10 order by datevrai asc", con);
                //alaina le date extremiter anak roa
                daty[0] = attrtemp[0].getDateVrai();
                daty[1] = attrtemp[attrtemp.length - 1].getDateVrai();
                //de aveo insererna le decalage
                GeneriqueDAO.insert(new Decalage(this.id, amount, new PGInterval("00:00:00")), con);
            }
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return daty;
    }

    public void effectif() throws Exception {
        //insertion
        Connection con = null;
        try {
            con = Connexion.getConnex();
            con.setAutoCommit(false);
            GeneriqueDAO.insert(new Effectif(Utilitaire.formatNumber("EFF" + Utilitaire.getsequence("seq_effectif", con), 4), this.id, Outil.getCurrentTimeStamp()), con);
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public Timestamp[] annuler() throws Exception {
        Timestamp[] daty = null;
        Connection con = null;
        try {
            con = Connexion.getConnex();
            con.setAutoCommit(false);
            AttributionPiste[] attrtemp = (AttributionPiste[]) GeneriqueDAO.find(AttributionPiste.class, " vol='" + this.id + "' and etat!=10", con);
            //raha ohatra ka tsy mbola nisy piste ni attribuerna azy de tode annulerna
            if (attrtemp.length == 0) {
                GeneriqueDAO.update("vol", " etat=10", " id='" + this.id + "'", con);
            } else {

                //alaina ilay proposition rehetra ray proposition aminy iny
                attrtemp = (AttributionPiste[]) GeneriqueDAO.find(AttributionPiste.class,
                        " dateproposition='" + attrtemp[0].getDateproposition().toString() + "' and etat!=10 order by datevrai asc", con);
                //alaina le date extremiter anak roa
                daty = new Timestamp[2];
                daty[0] = attrtemp[0].getDateVrai();
                daty[1] = attrtemp[attrtemp.length - 1].getDateVrai();
                //de aveo update etat vol
                GeneriqueDAO.update("vol", " etat=10", " id='" + this.id + "'", con);
            }

            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return daty;
    }

    public VolPisteDetail getFiche() throws Exception {
        Connection con = null;
        try {
            String where = "(typevol='dec' or typevol='att') and id='" + this.id + "' and toerana='" + Outil.AEROPORT_STATIC.getId() + "' limit 1";

            VolPisteDetail[] vpd = (VolPisteDetail[]) GeneriqueDAO.find(VolPisteDetail.class, where, con);
            return vpd[0];
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void insert(Connection con) throws Exception {
        try {
            this.setEtat(1);
            this.setId(Utilitaire.formatNumber("VL" + Utilitaire.getsequence("seq_vol", con), 8));
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

    public Timestamp getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Timestamp dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Decalage getDecalage() {
        return decalage;
    }

    public void setDecalage(Decalage decalage) {
        this.decalage = decalage;
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

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

}
