<%-- 
    Document   : master
    Created on : 4 janv. 2014, 22:59:09
    Author     : Steve Cancès <steve.cances@gmail.com>
--%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:importAttribute name="cssFiles" />  
<tiles:importAttribute name="jsFiles" />

<!doctype html>
<html>
    <head>
        <title><spring:message code="property.appname" text="Campark test neuroph" /></title>
	<c:forEach var="cssFile" items="${cssFiles}">  
	    <link type="text/css" rel="stylesheet" href="<spring:url value="${cssFile}" />"  />  
	</c:forEach>
    </head>
    <body>
        <div class="container">
            <tiles:insertAttribute name="header" />
            <tiles:insertAttribute name="body" />
	    <tiles:insertAttribute name="footer" />
	</div>
	<c:forEach var="jsFile" items="${jsFiles}">  
	    <script type="text/javascript" src="<spring:url value="${jsFile}" />" ></script>   
	</c:forEach> 
    </body>
</html>