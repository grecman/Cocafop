<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
    xmlns:jsp="http://java.sun.com/JSP/Page"  
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt"
	version="2.0">
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text><![CDATA[<?xml version="1.0" encoding="UTF-8" ?>]]></jsp:text>
	<jsp:text><![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">]]></jsp:text>
	<!--<jsp:text><![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">]]></jsp:text>-->
<html>
<head>
<title>C O C A F O P</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/Style/portal_cocafop.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/Style/portal_cocafop_tables.css"/>
</head>
<body>

	<div id="header">
		<c:set scope="request" var="actual" value="homePage"/>
		<jsp:include page="header.jsp"/>
	</div> 

	<div id="mainBody">
		<img src="${pageContext.servletContext.contextPath}/images/cocafop_logo.png" align="top"/> <br/> <br/>
		<p>The CoCaFoP application is used for creation of material
			calculations for cars produced in external assembly plants. The
			source comes from execution of orders produced in the parent brands
			and the price list of purchased parts of the calculated plant. The
			application defines calculation models; real orders are selected for
			these models on a monthly basis. The resulting material calculations
			arise from evaluation of these orders with prices from the price
			list. These calculations can be subsequently corrected (quantity of
			parts, prices). The approved calculations are archived. The produced
			calculations can be mutually compared.</p>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div> 
</body>
</html>
</jsp:root>
