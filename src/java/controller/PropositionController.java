/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Aeroport;
import model.Proposition;
import model.VolPiste;
import utilitaires.Connexion;

/**
 *
 * @author manohisoa
 */
@WebServlet(name = "PropositionController", urlPatterns = {"/proposition"})
public class PropositionController extends HttpServlet {

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
        if (request.getParameter("validationProp") != null) {
            Connection con = null;
            try {
                con = Connexion.getConnex();
                con.setAutoCommit(false);
                Aeroport ar = new Aeroport("AER0001", "Tamatave");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date parsedDate = dateFormat.parse(request.getParameter("debut"));
                Timestamp debut = new Timestamp(parsedDate.getTime());
                java.util.Date parsedDate2 = dateFormat.parse(request.getParameter("fin"));
                Timestamp fin = new Timestamp(parsedDate2.getTime());

                VolPiste[] vp = ar.getVols(debut, fin, "normal", con);
                VolPiste[] triage = ar.trier(vp);
                ar.setListevols(triage);
                Proposition prop = ar.proposer();
                prop.valider(con);
                con.commit();

                RequestDispatcher rdsp = request.getServletContext().getRequestDispatcher("/views/aeroport.jsp");
                rdsp.forward(request, response);
            } catch (Exception e) {
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException ex) {
                        Logger.getLogger(PropositionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                e.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(PropositionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } else {
            try {
                Aeroport ar = new Aeroport("AER0001", "Tamatave");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date parsedDate = dateFormat.parse(request.getParameter("debut"));
                Timestamp debut = new Timestamp(parsedDate.getTime());
                java.util.Date parsedDate2 = dateFormat.parse(request.getParameter("fin"));
                Timestamp fin = new Timestamp(parsedDate2.getTime());

                VolPiste[] vp = ar.getVols(debut, fin, "normal");
                VolPiste[] triage = ar.trier(vp);
                ar.setListevols(triage);
                Proposition prop = ar.proposer();

                request.setAttribute("proposition", prop);
                request.setAttribute("debut", debut);
                request.setAttribute("fin", fin);

                RequestDispatcher rdsp = request.getServletContext().getRequestDispatcher("/views/proposition.jsp");
                rdsp.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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

    }

}
