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
        <div class="content container">
            <%--<h1 class="error">
                ${mensagem}
            </h1>--%>
            <h1>Galerias de ${nome}</h1>
            <hr/>

            <c:forEach items="${galeriaList}" var="galeria">
                <!--<a href="viewGaleria/${galeria.id}">${galeria.nome}</a>-->
                <form action="${linkTo[UsuarioController].editGaleria}" id="edit${galeria.id}" method="post">
                    <span>
                        <input type="text" name="galeria.nome" value="${galeria.nome}">
                    </span>
                    <span>
                        <button type="submit" form="edit${galeria.id}">
                            <span title="editar titulo" class="glyphicon glyphicon-pencil"/>
                        </button>
                    </span>
                    <input type="hidden" value="${galeria.id}" name="galeria.id">
                </form>

                <a href="${linkTo[UsuarioController].deleteGaleria(galeria.id)}">
                    <button>
                        <span title="deletar" class="glyphicon glyphicon-trash"></span>
                    </button>
                </a>

                <a href="${linkTo[UsuarioController].viewGaleria(galeria.id)}">
                    <img alt="thumbnail" class="thumbnail" width="50" height="50" src="${pageContext.request.contextPath}/${galeria.thumbnail}">
                </a><br>
            </c:forEach><br>

            Nova galeria:<br>
            <form action="${linkTo[UsuarioController].addGaleria}" method="post">
                <label for="nome">Nome</label> <input type="text" id="nome" name="galeria.nome"><br>
                <input type="submit">
            </form>

            <br>
            <a href="${linkTo[UsuarioController].logout}">Logout</a>
        </div>
    </body>
</html>
