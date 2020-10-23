/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Aeroport;
import model.ListeVolAffiche;
import utilitaires.Connexion;
import utilitaires.Outil;

/**
 *
 * @author manohisoa
 */
@WebServlet(name = "ListerVol", urlPatterns = {"/liste"})
public class ListerVolController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
            Date datemin = Date.valueOf(request.getParameter("dateMin"));
            System.out.println(datemin.toString());

            String heureMin = request.getParameter("dateMinTime");
            System.out.println(heureMin);

            Date datemax = Date.valueOf(request.getParameter("dateMax"));
            System.out.println(datemax.toString());

            String heureMax = request.getParameter("dateMaxTime");
            System.out.println(heureMax);

            Timestamp[] entre = Outil.convertdaty(datemin, heureMin, datemax, heureMax);
            System.out.println(entre[0].toString());
            System.out.println(entre[1].toString());
            Aeroport ar = new Aeroport("AER0001", "Tamatave");
            ListeVolAffiche[] aff = ar.getVolsAffiche(entre[0], entre[1], con);

            request.setAttribute("listevol", aff);
            request.setAttribute("datedebut", entre[0]);
            request.setAttribute("datefin", entre[1]);
            RequestDispatcher rdsp = request.getServletContext().getRequestDispatcher("/views/aeroport.jsp");
            rdsp.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ListerVolController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
