<%-- 
    Document   : index
    Created on : 11/06/2014, 16:00:08
    Author     : Valdinei.Silva
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="../lib/header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            .error {
                color : red;
            }
        </style>
    </head>
    <body>
        <h2 class="error">${mensagem}</h2>
 
        Login:<br><br>
        <form action="${pageContext.request.contextPath}/index/login" method="post">
            Email: <input type="text" name="email"> <br>
            Senha: <input type="password" name="senha"> <br>
            <input type="submit">
        </form>
            
        <hr>
        
        Cadastre-se:<br>
        <form action="/index/cadastro" method="post"> <br>
            Email: <input type="text" name="usuario.email"> <br>
            Nome: <input type="text" name="usuario.nome"> <br>
            Senha: <input type="password" name="usuario.senha"> <br>
            <input type="submit">
        </form>

    </body>
</html>
