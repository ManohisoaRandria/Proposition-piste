/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Aeroport;
import utilitaires.Connexion;

/**
 *
 * @author manohisoa
 */
public class Test extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession(false) == null) {
            Aeroport ar = new Aeroport("AER0001", "Tamatave");
            HttpSession sess = request.getSession();
            sess.setAttribute("aeroport", ar);
            RequestDispatcher rdsp = request.getServletContext().getRequestDispatcher("/views/aeroport.jsp");
            rdsp.forward(request, response);
        } else {
            RequestDispatcher rdsp = request.getServletContext().getRequestDispatcher("/views/aeroport.jsp");
            rdsp.forward(request, response);
        }

//        Connection con = null;
//        try {
////            con = Connexion.getConnex();
////            con.setAutoCommit(false);
////            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
////            Date parsedDate = Date.valueOf("2020-09-09");
////            Date date1 = new Date(parsedDate.getTime());
//////            System.out.println("ex:" + date1.toString());
////            Date parsedDate2 = Date.valueOf("2020-09-09");
////            Date date2 = new Date(parsedDate2.getTime());
////
////
//////            System.out.println("ex2:" + date2.toString());
////
////            Aeroport ar = new Aeroport("AER0001", "Tamatave");
////            VolPiste[] vp = ar.getVols(date1, date2, "normal", con);
////            VolPiste[] triage = ar.trier(vp);
////            System.out.println("trier size: " + triage.length);
////            for (VolPiste volPiste : triage) {
////                System.out.println("id:" + volPiste.getId());
////                System.out.println("date vol:" + volPiste.getDateDepart().toString());
////                for (Piste piste : volPiste.getPistepossible()) {
////                    System.out.println("    id-piste:" + piste.getId());
////                    System.out.println("    long-piste:" + piste.getLongueur());
////                    System.out.println("    temps degagement:" + piste.getTempsDegagement().toString());
////                }
////
////                System.out.println("/**************///***************/");
////            }
////            System.out.println("--------------");
////            System.out.println("-------------- ");
////            ar.setListevols(triage);
////            Proposition prop = ar.proposer();
////            System.out.println("proposition");
////            for (AttributionPiste volPiste : prop.getAttribution()) {
////                System.out.println("    vol: " + volPiste.getVol());
////                System.out.println("    piste: " + volPiste.getPiste());
////                System.out.println("    date: " + volPiste.getDateVrai());
////                System.out.println("/////////////");
////            }
//////            prop.valider(con);
//////            System.out.println("done");
////            con.commit();
//        } catch (Exception e) {
//            try {
//                if (con != null) {
//                    con.rollback();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            e.printStackTrace();
//        } finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        try {
            con = Connexion.getConnex();
            System.out.println(request.getParameter("datemin"));
            System.out.println(request.getParameter("datemax"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
