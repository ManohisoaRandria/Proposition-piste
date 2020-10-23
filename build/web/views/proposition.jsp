<%--
    Document   : proposition
    Created on : Sep 8, 2020, 9:39:05 AM
    Author     : manohisoa
--%>

<%@page import="model.AttributionPiste"%>
<%@page import="model.Proposition"%>
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
            width:700px;
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

        <h4>Debut: <%=request.getAttribute("debut")%></h4>
        <h4>Fin: <%=request.getAttribute("fin")%></h4>

        <h2>Propositions</h2>
        <table>
            <thead>
                <tr>
                    <th>Vol</th>
                    <th>Piste</th>
                    <th>Date</th>
                    <th>Decalage</th>
                </tr>
            </thead>
            <tbody>
                <%if (request.getAttribute("proposition") != null) {
                        Proposition prop = (Proposition) request.getAttribute("proposition");%>
                <%for (AttributionPiste elem : prop.getAttribution()) {%>
                <tr>
                    <td><%= elem.getVol()%></td>
                    <td><%= elem.getPiste()%></td>
                    <td><%= elem.getDateVrai()%></td>
                    <td><%= elem.decalageAffiche()%></td>

                </tr>
                <%}%>
                <%}%>
            </tbody>
        </table>
        <a href="/Vol/proposition?debut=<%=request.getAttribute("debut")%>&fin=<%=request.getAttribute("fin")%>&validationProp=1" ><button>Valider</button></a>
    </body>
</html>
