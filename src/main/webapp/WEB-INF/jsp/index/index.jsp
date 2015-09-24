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

    </head>
    <body>
        <div class="content container">
            <h2 class="error">${mensagem}</h2>

            <h2>Login:</h2><br><br>
            <form action="${pageContext.request.contextPath}/index/login" method="post" id="login">
                <div class="form-group-lg">
                    <div class="form-group" >
                        <label for="email">Email:</label> <input type="text" name="email" id="email"> <br>
                    </div>
                    <div class="form-group" >
                        <label for="senha">Senha: </label><input type="password" name="senha" id="senha"> <br>
                    </div>
                    <div class="form-group" >
                        <button type="submit" class="btn-default btn" form="login">Enviar</button>
                    </div>
                </div>
            </form>
    </body>
</html>
