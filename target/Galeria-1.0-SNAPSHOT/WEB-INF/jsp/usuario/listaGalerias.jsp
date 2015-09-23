<%-- 
    Document   : listaGalerias
    Created on : 22/09/2015, 07:01:26
    Author     : Rael
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="../lib/header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Galerias de ${nome}</h1>
        <hr/>
        
        <c:forEach items="${galeriaList}" var="galeria">
            <!--<a href="viewGaleria/${galeria.id}">${galeria.nome}</a>-->
            <a href="${linkTo[UsuarioController].viewGaleria(galeria.id)}">${galeria.nome}</a><br>
        </c:forEach><br>
        
	Nova galeria:<br>
        <form action="${linkTo[UsuarioController].addGaleria}" method="post">
	    <label for="nome">Nome</label> <input type="text" id="nome" name="galeria.nome"><br>
	    <input type="submit">
	</form>
    </body>
</html>
