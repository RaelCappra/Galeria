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
        <link rel="stylesheet" type="text/css" href="<c:url value="/js/slick/slick.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/js/slick/slick-theme.css"/>"/>
        <script src="<c:url value="/js/slick/slick.min.js"/>" type="text/javascript" ></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('.galeria').slick({
                    infinite: true,
                    arrows: true,
                });
            });
        </script>
        <style>
            .slick-prev:before, .slick-next:before { 
                color:green !important;
            }/*
            .slick-prev {
                margin-left: 40px;
            }

            .slick-next {
                margin-right: 40px;
            }*/
            
            .slick-slide{
                margin-left: 2%;
                //margin-right: 5%;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="content container">
            <h1 class="error">
                ${mensagem}
            </h1>
            <h1>${galeria.nome}</h1>
            <hr/>

            <div class="galeria">
                <c:forEach items="${imagemList}" var="imagem">

                    <div class="slick-slide">
                        <form action="${linkTo[GaleriaController].editImagem}" id="edit${imagem.id}" method="post">
                            <span>
                                <input type="text" name="imagem.nome" value="${imagem.nome}">
                            </span>
                            <span>
                                <button type="submit" form="edit${imagem.id}">
                                    <span title="editar titulo" class="glyphicon glyphicon-pencil"/>
                                </button>
                            </span>
                            <input type="hidden" value="${imagem.id}" name="imagem.id">
                        </form>
                        <span>
                            <a href="${linkTo[GaleriaController].deleteImagem(imagem.id)}">
                                <button>
                                    <span title="deletar" class="glyphicon glyphicon-trash"></span>
                                </button>
                            </a>

                        </span>
                        <br>
                        <img alt="${imagem.nome}"  src="../../uploads/${imagem.fileName}">
                    </div>
                    </form>
                </c:forEach>
            </div>

            <form action="${pageContext.request.contextPath}/galeria/addImagem" enctype="multipart/form-data" method="post">
                Imagem: <input type="file" name="file" accept="image/jpeg, image/png, image/bmp, image/gif" required/><br>
                <label for="nome">Nome</label> <input type="text" id="nome" name="imagem.nome" required><br>
                <input type="hidden" value="${galeria.id}" name="galeriaId"/>
                <input type="submit"/>
            </form><br>
            <hr>
            <a href="${linkTo[GaleriaController].zipGaleria(galeria.id)}">Download como zip</a><br>
            <a href="${linkTo[UsuarioController].listaGalerias}">Voltar</a>
        </div>
    </body>
</html>
