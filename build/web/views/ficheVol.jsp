<%--
    Document   : ficheVol
    Created on : Sep 7, 2020, 8:17:44 AM
    Author     : manohisoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Fiche du vol VL003</h1>
        <ul>
            <li>Identifiant: </li>
            <li>Type Avion: </li>
            <li>Depart: </li>
            <li>Arrivee:</li>
            <li>Temps de vol éstimé:</li>
        </ul>

        <a href="#" ><button>Annuler</button></a>
        <form>
            <label for="decalage">Decalage</label>
            <input type="text" name="decalage">
            <input type="submit" value="Decaler">
        </form>
        <a href="#" ><button>Effectif</button></a>
    </body>
</html>
