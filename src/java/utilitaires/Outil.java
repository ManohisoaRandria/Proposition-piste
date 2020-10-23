/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaires;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.Aeroport;
import model.Point;
import org.postgresql.util.PGInterval;

/**
 *
 * @author manohisoa
 */
public class Outil {

    public static final Aeroport AEROPORT_STATIC = new Aeroport("", "");

    public static Double getAngleFromPoint(Point a, Point b) {
        Double coefDir = (a.getY() - b.getY()) / (a.getX() - b.getX());
        Double angle = Math.toDegrees(Math.atan(coefDir));
        return angle;
    }

    public static int ComparePGInterval(PGInterval t1, PGInterval t2) {
        int res = 0;
        Double d1 = t1.getHours() * 3600 + t1.getMinutes() * 60 + t1.getSeconds();
        Double d2 = t2.getHours() * 3600 + t2.getMinutes() * 60 + t2.getSeconds();
        if (d1 > d2) {
            res = 1;
        } else if (d1 < d2) {
            res = -1;
        } else {
            res = 0;
        }
        return res;
    }

    public static Point getPointFromString(String points) {
        String[] split = points.split("[;]");
        return new Point(Double.valueOf(split[0]), Double.valueOf(split[1]));
    }
    ///Date Actuel

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        return date;
    }

    ///Date et Heure Actuel
    public static Timestamp getCurrentTimeStamp() {
        java.util.Date date = new java.util.Date();
        long time = date.getTime();
        return new Timestamp(time);
    }

    public static String formatNumber(String seq, int ordre) throws Exception {
        if (seq.split("").length > ordre) {
            throw new Exception("Format impossible !");
        }
        String ret = "";
        for (int i = 0; i < ordre - seq.split("").length; i++) {
            ret += "0";
        }
        return ret + seq;
    }

    public static Timestamp[] convertdaty(Date datemin2, String heuremin, Date datemax2, String heuremax) throws ParseException {
        Timestamp[] retour = new Timestamp[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date parsedDate = dateFormat.parse(datemin2.toString().trim() + " " + heuremin);
        Timestamp min = new Timestamp(parsedDate.getTime());
        java.util.Date parsedDate2 = dateFormat.parse(datemax2.toString().trim() + " " + heuremax);
        Timestamp max = new Timestamp(parsedDate2.getTime());
        retour[0] = min;
        retour[1] = max;

        return retour;
    }
}
