/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotation.Colonne;
import annotation.Entite;
import annotation.PrimaryKey;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import model.mapping.VolPisteDetail;
import org.postgresql.util.PGInterval;
import outil.GeneriqueDAO;
import utilitaires.Connexion;
import utilitaires.Outil;

/**
 *
 * @author manohisoa
 */
@Entite(table = "aeroport")
public class Aeroport {

    @PrimaryKey
    @Colonne("id")
    private String id;
    @Colonne("ville")
    private String ville;
    private VolPiste[] listevols;

    public Aeroport(String id, String ville) {
        this.id = id;
        this.ville = ville;
    }

    public Proposition proposer() throws SQLException {
        Proposition prop = new Proposition();
        ArrayList<AttributionPiste> attr = new ArrayList<>();
        ArrayList<Piste> pistetemp = new ArrayList<>();

        int a = 0;
        boolean efatao = true;
        for (int i = 0; i < this.getListevols().length; i++) {
            if (i == 0) {
                AttributionPiste ptemporaire = new AttributionPiste("", this.getListevols()[i].getId(),
                        this.getListevols()[i].getPistepossible().get(0).getId(),
                        this.getListevols()[i].getDateDepart(),
                        new PGInterval("00:00:00"),
                        null, 1);
                attr.add(ptemporaire);

                Piste hah = this.getListevols()[i].getPistepossible().get(0);
                hah.setVolefarany(this.getListevols()[i]);
                pistetemp.add(hah);
            } else {
                //raha ray ihany no ao de mjery ary anaty pistetemp
                if (this.getListevols()[i].getNbPiste() == 1) {
                    //raha efa any anaty pistetemp de decalerna
                    int testcount = contain(pistetemp, this.getListevols()[i].getPistepossible().get(0));
                    if (testcount != (-1)) {
                        AttributionPiste ptemporaire = new AttributionPiste("", this.getListevols()[i].getId(),
                                this.getListevols()[i].getPistepossible().get(0).getId(),
                                this.getListevols()[i].getDateDepart(),
                                null,
                                null, 1);
                        ptemporaire.decalerTemporaire(pistetemp.get(testcount).getVolefarany(), this.getListevols()[i].getPistepossible().get(0));
                        //apina ny degagement anlery
                        this.getListevols()[i].setDateDepart(ptemporaire.getDateVrai());
                        attr.add(ptemporaire);
                        pistetemp.get(testcount).setVolefarany(this.getListevols()[i]);
                    } else {
                        //raha tsy any de ampidirina ao de mampiditra proposition
                        attr.add(new AttributionPiste("", this.getListevols()[i].getId(),
                                this.getListevols()[i].getPistepossible().get(0).getId(),
                                this.getListevols()[i].getDateDepart(),
                                new PGInterval("00:00:00"),
                                null, 1));
                        Piste hah = this.getListevols()[i].getPistepossible().get(0);
                        hah.setVolefarany(this.getListevols()[i]);
                        pistetemp.add(hah);
                    }
                } else {
                    //raha bdb de bouclena
                    //de jerena hoe efa tao dol ve
                    for (Piste piste : this.getListevols()[i].getPistepossible()) {
                        int testcount = contain(pistetemp, piste);
                        if (testcount == (-1)) {
                            //raha tsy tao de apdirina tsisy decalage
                            attr.add(new AttributionPiste("", this.getListevols()[i].getId(),
                                    piste.getId(),
                                    this.getListevols()[i].getDateDepart(),
                                    new PGInterval("00:00:00"),
                                    null, 1));
                            piste.setVolefarany(this.getListevols()[i]);
                            pistetemp.add(piste);
                            efatao = false;
                            break;
                        }
                    }
                    //de ra efa tao dol de jerena hoe iza no optimal
                    //de decalerna
                    if (efatao) {
                        AttributionPiste ptemporaire2 = null;
//                        int testcount = 0;
                        Piste pisteTenaIdirany = null;
                        //alaina ze decalage kely ndrindra
                        for (int j = 0; j < this.getListevols()[i].getPistepossible().size(); j++) {
                            //maka ny index anle piste io oa anaty pistetemp
                            int index = getIndex(pistetemp, this.getListevols()[i].getPistepossible().get(j));
                            //raha vo voalohany de atsofoka anaty temp aloha iny voalohany iny
                            //sady iny index iny akana anle vol farany amle filaharana tao anaty piste ray
                            if (j == 0) {
                                ptemporaire2 = new AttributionPiste("", this.getListevols()[i].getId(),
                                        this.getListevols()[i].getPistepossible().get(j).getId(),
                                        this.getListevols()[i].getDateDepart(),
                                        null,
                                        null, 1);
                                //manova anle decalage mba ho any  arinanle vol farany ao
                                ptemporaire2.decalerTemporaire(pistetemp.get(index).getVolefarany(),
                                        this.getListevols()[i].getPistepossible().get(j));
                                pisteTenaIdirany = this.getListevols()[i].getPistepossible().get(j);
                            } else {
                                //raha tsy le piste voalohany de jerena ndray ny decalage amniny piste iny
                                AttributionPiste test = new AttributionPiste("", this.getListevols()[i].getId(),
                                        this.getListevols()[i].getPistepossible().get(j).getId(),
                                        this.getListevols()[i].getDateDepart(),
                                        null,
                                        null, 1);
                                test.decalerTemporaire(pistetemp.get(index).getVolefarany(), this.getListevols()[i].getPistepossible().get(j));
                                //jerena de raha kely le azo iny de iny ndray aloha no mety
                                if (Outil.ComparePGInterval(test.getDecalage(), ptemporaire2.getDecalage()) < 0) {
                                    ptemporaire2 = test;
                                    pisteTenaIdirany = this.getListevols()[i].getPistepossible().get(j);
                                } //raha egale kosa de maka ny angle anle piste sy ny angle d'attaque
                                //de jerena ze kely ndrindra ny difference an angle entre anle piste sy le vol
                                else if (Outil.ComparePGInterval(test.getDecalage(), ptemporaire2.getDecalage()) == 0
                                        && this.getListevols()[i].getTypeVol().equalsIgnoreCase("att")) {
                                    //alaina le piste teo aloha de alaina ny angle any amniny
                                    //alaina le pisteamzao de jerena kou ny angle any
                                    //alaina ny angle anlery de hatao moins amnizy roa de ze kely no avoka

                                    //ty  no code raha ohatra hoe le points no ampiasaina amle piste fa tsy me angle direct
                                    if (isVaovaoMBolaKelyNohoTaloha(getPisteById(pistetemp, ptemporaire2.getPiste()).getPointApiste(),
                                            getPisteById(pistetemp, ptemporaire2.getPiste()).getPointBpiste(),
                                            getPisteById(pistetemp, test.getPiste()).getPointApiste(),
                                            getPisteById(pistetemp, test.getPiste()).getPointBpiste(), this.getListevols()[i].getAnglevol())) {
                                        ptemporaire2 = test;
                                        pisteTenaIdirany = this.getListevols()[i].getPistepossible().get(j);
                                    }
                                    //ty ndray raha ohatra hoe tode le angle no hatao
//                                     if (isVaovaoMBolaKelyNohoTaloha(getPisteById(pistetemp, ptemporaire2.getPiste()).getAnglepiste(),
//                                            getPisteById(pistetemp, test.getPiste()).getAnglepiste(),
//                                             this.getListevols()[i].getAnglevol())) {
//                                        ptemporaire2 = test;
//                                        pisteTenaIdirany = this.getListevols()[i].getPistepossible().get(j);
//                                    }

                                }
                            }
                        }

                        //atsofoka ny date de depart anlery
                        this.getListevols()[i].setDateDepart(ptemporaire2.getDateVrai());
                        //atsofoka anaty proposition le vole iny
                        attr.add(ptemporaire2);
                        //ovaina ny vol farany tao amle piste nchoisisserna hidiranlery teo
                        //ovaina anlery
                        pistetemp.get(getIndex(pistetemp, pisteTenaIdirany)).setVolefarany(this.getListevols()[i]);
                    }
                }

            }
            efatao = true;
        }

        prop.setAttribution(attr);
        return prop;
    }

    //raha le teo aloha no kely de mamoka false
    public boolean isVaovaoMBolaKelyNohoTaloha(String pointOldPisteA, String pointOldPisteB,
            String pointPisteA, String pointPisteB,
            Double angleAttaqueVol) {
        Point ppoA = Outil.getPointFromString(pointOldPisteA);
        Point ppoB = Outil.getPointFromString(pointOldPisteB);
        Double angleOld = Outil.getAngleFromPoint(ppoA, ppoB);

        Point ppA = Outil.getPointFromString(pointPisteA);
        Point ppB = Outil.getPointFromString(pointPisteB);
        Double anglecurrent = Outil.getAngleFromPoint(ppA, ppB);

        Double diffold = angleAttaqueVol - angleOld;
        Double diffnew = angleAttaqueVol - anglecurrent;
        //raha le vao2 no kely de tokony iny no raisina zany hoe true
        return diffnew < diffold;
    }

    public boolean isVaovaoMBolaKelyNohoTaloha(Double angleOld,
            Double anglecurrent,
            Double angleAttaqueVol) {
        Double diffold = angleAttaqueVol - angleOld;
        Double diffnew = angleAttaqueVol - anglecurrent;
        //raha le vao2 no kely de tokony iny no raisina zany hoe true
        return diffnew < diffold;
    }

    public Piste getPisteById(ArrayList<Piste> piste, String idpiste) {
        Piste pt = null;
        for (int i = 0; i < piste.size(); i++) {
            if (piste.get(i).getId().equals(idpiste)) {
                return piste.get(i);
            }
        }
        return pt;
    }

    public int contain(ArrayList<Piste> piste, Piste pistetest) {
        int b = -1;
        for (int i = 0; i < piste.size(); i++) {

            if (piste.get(i).getId().equals(pistetest.getId())) {
                return i;
            }
        }
        return b;
    }

    public int getIndex(ArrayList<Piste> piste, Piste pistetest) {
        for (int i = 0; i < piste.size(); i++) {
            if (piste.get(i).getId().equals(pistetest.getId())) {
                return i;
            }
        }
        return -1;
    }

    public VolPiste[] getVols(Timestamp datemin, Timestamp datemax, String type) throws Exception {
        Connection con = null;
        try {
            con = Connexion.getConnex();
            return getVols(datemin, datemax, type, con);
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public VolPiste[] getVols(Timestamp datemin2, Timestamp datemax2, String type, Connection con) throws Exception {

        ArrayList<VolPiste> vp = new ArrayList<>();
        try {
            //maka vol piste detail
            String where = "";
            if (type.equals("normal")) {
                where = "(typevol='dec' or typevol='att') and toerana='" + this.id + "' and (datedepart >= '" + datemin2.toString() + "' and datedepart<='" + datemax2.toString() + "') order by id";
            } else {
                where = "(typevol='dec' or typevol='att') and toerana='" + this.id + "' and (datedepart >= '" + datemin2.toString() + "') order by id";
            }
            VolPisteDetail[] vpd = (VolPisteDetail[]) GeneriqueDAO.find(VolPisteDetail.class, where, con);
            String id = "";

            VolPiste temp = null;
            Decalage dec;
            boolean test = false;
            ArrayList<Piste> piste = null;

            for (int i = 0; i < vpd.length; i++) {
                if (i == 0) {
                    id = vpd[i].getId();
                }
                if (vpd[i].getId().equals(id) && i != (vpd.length - 1)) {
                    if (!test) {
                        dec = new Decalage("", vpd[i].getDecalmanuel(), vpd[i].getSommedecalage());
                        temp = new VolPiste(vpd[i].getId(), vpd[i].getTrajet(), vpd[i].getAvion(), vpd[i].getDatedepart(), vpd[i].getEtat(), dec,
                                vpd[i].getPointAvol(), vpd[i].getPointBvol(), vpd[i].getAnglevol());
                        temp.setTypeVol(vpd[i].getTypevol());
                        piste = new ArrayList<>();
                        test = true;
                    }
                    piste.add(new Piste(vpd[i].getIdpiste(), vpd[i].getId(), vpd[i].getLongueurPiste(), vpd[i].getTempsdegagement(),
                            vpd[i].getPointApiste(), vpd[i].getPointBpiste(), vpd[i].getAnglePiste()));
                } else if (!vpd[i].getId().equals(id) && i != (vpd.length - 1)) {
                    temp.setPistepossible(piste);
                    temp.setNbPiste(piste.size());
                    vp.add(temp);

                    dec = new Decalage("", vpd[i].getDecalmanuel(), vpd[i].getSommedecalage());
                    temp = new VolPiste(vpd[i].getId(), vpd[i].getTrajet(), vpd[i].getAvion(), vpd[i].getDatedepart(), vpd[i].getEtat(), dec,
                            vpd[i].getPointAvol(), vpd[i].getPointBvol(), vpd[i].getAnglevol());
                    temp.setTypeVol(vpd[i].getTypevol());
                    piste = new ArrayList<>();
                    piste.add(new Piste(vpd[i].getIdpiste(), vpd[i].getId(), vpd[i].getLongueurPiste(), vpd[i].getTempsdegagement(),
                            vpd[i].getPointApiste(), vpd[i].getPointBpiste(), vpd[i].getAnglePiste()));

                    id = vpd[i].getId();
                    test = true;
                } else if (!vpd[i].getId().equals(id) && i == (vpd.length - 1)) {
                    temp.setPistepossible(piste);
                    temp.setNbPiste(piste.size());
                    vp.add(temp);

                    dec = new Decalage("", vpd[i].getDecalmanuel(), vpd[i].getSommedecalage());
                    temp = new VolPiste(vpd[i].getId(), vpd[i].getTrajet(), vpd[i].getAvion(), vpd[i].getDatedepart(), vpd[i].getEtat(), dec,
                            vpd[i].getPointAvol(), vpd[i].getPointBvol(), vpd[i].getAnglevol());
                    temp.setTypeVol(vpd[i].getTypevol());
                    piste = new ArrayList<>();
                    piste.add(new Piste(vpd[i].getIdpiste(), vpd[i].getId(), vpd[i].getLongueurPiste(), vpd[i].getTempsdegagement(),
                            vpd[i].getPointApiste(), vpd[i].getPointBpiste(), vpd[i].getAnglePiste()));
                    temp.setPistepossible(piste);
                    temp.setNbPiste(piste.size());
                    vp.add(temp);
                } else {
                    piste.add(new Piste(vpd[i].getIdpiste(), vpd[i].getId(), vpd[i].getLongueurPiste(), vpd[i].getTempsdegagement(),
                            vpd[i].getPointApiste(), vpd[i].getPointBpiste(), vpd[i].getAnglePiste()));
                    temp.setPistepossible(piste);
                    temp.setNbPiste(piste.size());
                    vp.add(temp);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return vp.toArray(new VolPiste[vp.size()]);
    }

    public ListeVolAffiche[] getVolsAffiche(Timestamp datemin, Timestamp datemax, Connection con) throws Exception {
        ArrayList<ListeVolAffiche> lv = new ArrayList<>();
        try {

            String where = "(typevol='dec' or typevol='att') and toerana='" + this.id + "' and (datedepart >= '" + datemin.toString() + "' and datedepart<='" + datemax.toString() + "') order by id";
            VolPisteDetail[] vpd = (VolPisteDetail[]) GeneriqueDAO.find(VolPisteDetail.class, where, con);

            //mamadik azy ho affiche
            String id = "";
            boolean test = false;
            for (int i = 0; i < vpd.length; i++) {
                if (i == 0) {
                    id = vpd[i].getId();
                }
                if (vpd[i].getId().equals(id)) {
                    if (!test) {
                        String type = vpd[i].getDepart().equals(this.id) ? "Depart" : "Arrivee";
                        lv.add(new ListeVolAffiche(vpd[i].getId(), vpd[i].getReference(), vpd[i].getDatedepart(), type));
                        test = true;
                    }
                } else {
                    String type = vpd[i].getDepart().equals(this.id) ? "Depart" : "Arrivee";
                    lv.add(new ListeVolAffiche(vpd[i].getId(), vpd[i].getReference(), vpd[i].getDatedepart(), type));
                    test = true;
                    id = vpd[i].getId();
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return lv.toArray(new ListeVolAffiche[lv.size()]);
    }

    public VolPiste[] trier(VolPiste[] volepiste) {
        //avadika array list
        ArrayList<VolPiste> temp = new ArrayList<>();

        temp.addAll(Arrays.asList(volepiste));
        //sort par date de vol
        Collections.sort(temp,
                new Comparator<VolPiste>() {
            @Override
            public int compare(VolPiste o1, VolPiste o2) {
                return o1.getDateDepart().compareTo(o2.getDateDepart());
            }
        });
        //sort par nb piste
        ArrayList<ArrayList<VolPiste>> temp2 = new ArrayList<>();
        ArrayList<VolPiste> interieur = new ArrayList<>();
        Timestamp tempsTemporaire = null;
        for (int i = 0; i < temp.size(); i++) {
            if (i == 0) {
                tempsTemporaire = temp.get(i).getDateDepart();
            }
            if (temp.get(i).getDateDepart().compareTo(tempsTemporaire) == 0) {
                interieur.add(temp.get(i));
                if (i == temp.size() - 1) {
                    temp2.add(interieur);
                }
            } else {
                temp2.add(interieur);
                interieur = new ArrayList<>();
                interieur.add(temp.get(i));
                if (i == temp.size() - 1) {
                    temp2.add(interieur);
                }
                tempsTemporaire = temp.get(i).getDateDepart();
            }
        }
        //trier par nombre piste
        for (ArrayList<VolPiste> arrayList : temp2) {
            Collections.sort(arrayList,
                    new Comparator<VolPiste>() {
                @Override
                public int compare(VolPiste o1, VolPiste o2) {
                    return o1.getNbPiste() - o2.getNbPiste();
                }
            });
        }
        ArrayList<VolPiste> finale = new ArrayList<>();
        //averina apetaka
        temp2.forEach((arrayList) -> {
            arrayList.forEach((volPiste) -> {
                finale.add(volPiste);
            });
        });
        //trierna par rapport amn longueur piste
        for (int i = 0; i < finale.size(); i++) {
            Collections.sort(finale.get(i).getPistepossible(),
                    new Comparator<Piste>() {
                @Override
                public int compare(Piste o1, Piste o2) {
                    return Double.compare(o1.getLongueur(), o2.getLongueur());
                }
            });
        }
        return finale.toArray(new VolPiste[finale.size()]);
    }

    public Piste[] getPistes(Connection con) {

        return null;
    }

    public void insert() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public VolPiste[] getListevols() {
        return listevols;
    }

    public void setListevols(VolPiste[] listevols) {
        this.listevols = listevols;
    }

}
