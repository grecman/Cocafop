<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[<?xml version="1.0" encoding="UTF-8" ?>]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">]]>
	</jsp:text>

	<html>
<head>
<title>C O C A F O P</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/Style/portal_cocafop.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/Style/portal_cocafop_tables.css" />
</head>
<body>

	<div id="header">
		<c:set scope="request" var="actual" value="showPermission" />
		<jsp:include page="header.jsp" />
	</div>

	<div id="mainBody" style="overflow: auto;">
		<BR /> <B><f:message>user</f:message>:</B>
		${onePermP.userApl.netUserName}<BR /> <B><f:message>plant</f:message>:</B>
		${onePermP.plant.id}<BR /> <BR />

		<form:form commandName="permissionPlant"
			action="${pageContext.servletContext.contextPath}/srv/administration/setPermissionPlant/${onePermP.id}/${onePermB.id}">
			<table>
				<tr>
					<td><f:message>read</f:message>:</td>
					<td><form:checkbox path="read" /></td>
				</tr>
				<tr>
					<td><f:message>write</f:message>:</td>
					<td><form:checkbox path="write" /></td>
				</tr>
				<tr>
					<td><f:message>approve</f:message>:</td>
					<td><form:checkbox path="approve" /></td>
				</tr>
			</table>
			<BR />
			<BR />
			<c:if test="${empty(commitPermissionPlant)}">
				<input type="submit" value="Set" /> 
			</c:if>
		</form:form>

		<BR />
		<BR />
		<BR />
		<c:if test="${not(empty(commitPermissionPlant))}">
			<B><f:message>brand</f:message>:</B> ${onePermB.brand.brandMark}<BR />
			<form:form commandName="permissionBrand"
				action="${pageContext.servletContext.contextPath}/srv/administration/setPermissionBrand/${onePermP.id}/${onePermB.id}">
				<BR />
				<table>
					<tr>
						<td><f:message>read</f:message>:</td>
						<td><form:checkbox path="read" /></td>
					</tr>
					<tr>
						<td><f:message>write</f:message>:</td>
						<td><form:checkbox path="write" /></td>
					</tr>
					<tr>
						<td><f:message>approve</f:message>:</td>
						<td><form:checkbox path="approve" /></td>
					</tr>
				</table>
				<BR />
				<BR />
				<input type="submit" value="Done" />
			</form:form>
		</c:if>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>
