<%-- 
    Document   : viewGaleria
    Created on : 22/09/2015, 10:08:50
    Author     : Rael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="../lib/header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${galeria.nome}</h1>
        <hr/>
        <c:forEach items="${imagemList}" var="imagem">
            ${imagem.id}.${imagem.extensao}<br>
        </c:forEach><br>
        
        <form action="${pageContext.request.contextPath}/galeria/addImagem" enctype="multipart/form-data" method="post">
            Imagem: <input type="file" name="file" accept="image/jpeg, image/png, image/bmp, image/gif" required/><br>
            <label for="nome">Nome</label> <input type="text" id="nome" name="imagem.nome" required><br>
            <input type="hidden" value="${galeria.id}" name="galeriaId"/>
            <input type="submit"/>
        </form>
    </body>
</html>
