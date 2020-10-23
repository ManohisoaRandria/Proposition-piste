<%--
    Document   : aeroport
    Created on : Sep 7, 2020, 7:59:03 AM
    Author     : manohisoa
--%>

<%@page import="model.ListeVolAffiche"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
        table{
            border: 1px solid black;
            border-collapse: collapse;
            text-align: center;
            width: 500px;
            margin-top: 10px;
        }
        table th,td{
            border: 1px solid black;
        }
        button{
            margin-top: 10px;
        }
    </style>
    <body>
        <a href="">add Piste</a>
        <h1>AEROPORT</h1>
        <h3>Lister Vol:</h3>
        <form action="/Vol/liste" method="post">
            <label for="datemin">Date Min</label>
            <input type="date" name="dateMin">
            <input type="text" name="dateMinTime" value="00:00:00" style="width: 100px;text-align: center">
            <label for="datemas">Date Max</label>
            <input type="date" name="dateMax">
            <input type="text" name="dateMaxTime" value="00:00:00" style="width: 100px;text-align: center">
            <input type="submit" value="recherche">
        </form>

        <table>
            <thead>
                <tr>
                    <th>Vol</th>
                    <th>Avion</th>
                    <th>Date</th>
                    <th>Type</th>
                    <td>Fiche</td>
                </tr>
            </thead>
            <tbody>
                <%if (request.getAttribute("listevol") != null) {
                        ListeVolAffiche[] aff = (ListeVolAffiche[]) request.getAttribute("listevol");%>
                <%for (ListeVolAffiche elem : aff) {%>
                <tr>
                    <td><%= elem.getIdVol()%></td>
                    <td><%= elem.getAvion()%></td>
                    <td><%= elem.getDateHeure()%></td>
                    <td><%= elem.getType()%></td>
                    <td><a href="views/ficheVol.jsp?id=<%= elem.getIdVol()%>" ><button>voir</button></a></td>
                </tr>
                <%}%>
                <%}%>
            </tbody>
        </table>
        <%if (request.getAttribute("listevol") != null) {%>
        <a href="/Vol/proposition?debut=<%= request.getAttribute("datedebut")%>&fin=<%= request.getAttribute("datefin")%>" ><button>Proposer</button></a>
        <%}%>
    </body>
</html>
