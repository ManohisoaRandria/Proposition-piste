/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaires;

import java.sql.Connection;
import outil.GeneriqueDAO;

/**
 *
 * @author manohisoa
 */
public class Connexion {

    public static Connection getConnex() throws Exception {
        return GeneriqueDAO.getConnection("vol_db", "postgres", "m1234", "postgres", 5432, "localhost");
    }
}
