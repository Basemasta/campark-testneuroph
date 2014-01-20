<%-- 
    Document   : header.jps
    Created on : 4 janv. 2014, 22:58:35
    Author     : Steve Cancès <steve.cances@gmail.com>
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="masthead">
    <h3 class="muted">
        <spring:message code='header.message' text="header.message" />
    </h3>

    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
	    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
		<span class="sr-only">Toggle navigation</span>
		<span class="icon-bar"></span>
	    </button>
	    <a class="navbar-brand" href="#"><spring:message code="property.appname" text="Campark test neuroph" /></a>
	</div>

	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	    <ul class="nav navbar-nav">
		<li><a href="<spring:url value="/index" />"><spring:message code="message.Home" text="Home" /></a></li>
	    </ul>

	    <ul class="nav navbar-nav navbar-right">
		<li class="dropdown">
		    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="message.Language" text="Language" /> <b class="caret"></b> &nbsp; </a>
		    <ul class="dropdown-menu">
			<li><a href="<spring:url value="?lang=en" />">English</a></li>
			<li><a href="<spring:url value="?lang=fr" />">Français</a></li>
		    </ul>
		</li>
	    </ul>
	</div><!-- /.navbar-collapse -->
    </nav>

</div>
