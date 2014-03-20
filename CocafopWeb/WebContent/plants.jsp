<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" 
	version="2.0">
	
<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<jsp:text>
		<![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">]]>
		<!-- <![CDATA[<!doctype html>]]>  -->
	</jsp:text>
		<jsp:text>
		<![CDATA[<?xml version="1.0" encoding="UTF-8" ?>]]>
	</jsp:text>
<html>
<head>
<title>C O C A F O P</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/Style/portal_cocafop.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/Style/portal_cocafop_tables.css" />
<script src="${pageContext.servletContext.contextPath}/js/cocafop.js">
	/**/
</script>

<style type="text/css">
div.scroll {
	background-color: #00FFFF;
	width: 100px;
	height: 100px;
	overflow: scroll;
}
</style>
</head>
<body>

	<div id="header">
		<c:set scope="request" var="actual" value="plants" />
		<jsp:include page="header.jsp" />
	</div>

	<BR />
	<div id="mainBody" style="overflow: auto;">
		<table style="table-layout: fixed;">
			<col width="350px" />
			<col width="600px" />
			<tr>
				<td><h4>
						<f:message>calculatedPlants</f:message>
					</h4></td>
				<td><h4>
						<f:message>brandsCalculatedInPlant</f:message>
					</h4></td>
			</tr>
			<tr>
				<td valign="top">
					<table width="100%"
						style="border-style: solid; border-width: thin; border-color: black;">
						<col width="50px" />
						<col width="250px" />
						<col width="50px" />
						<tr>
							<th style="background-color: #c7c0b8"><f:message>plant</f:message></th>
							<th><f:message>plantName</f:message></th>
							<th></th>
						</tr>
						<c:forEach items="${listOfPlants}" var="i" varStatus="iterator">
							<!-- varStatus="iterator" vraci cislo rakdu na kterem zrovna je -->
							<tr
								class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">
								<!-- (iterator.index mod 2) zbytek po deleni dvojkou ... slouzi pro obarveni radku u vice tabulek vedle sebe -->
								<td align="center">${i.id}</td>
								<td><a
									href="${pageContext.servletContext.contextPath}/srv/plants/showBrands/${i.id}">${i.plantName}</a></td>
								<td align="center"><c:if test="${role['ADMINS']}">
										<a onClick="return confirm('Really?')"
											href="${pageContext.servletContext.contextPath}/srv/plants/removePlant/${i.id}"><img
											title="DELETE"
											src="${pageContext.servletContext.contextPath}/ico/zrusit.png" /></a>
									</c:if></td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td valign="top">
					<table width="100%"
						style="border-style: solid; border-width: thin; border-color: black;">
						<col width="50px" />
						<col width="100px" />
						<col width="400px" />
						<col width="50px" />
						<tr>
							<th><f:message>plant</f:message></th>
							<th><f:message>brand</f:message></th>
							<th><f:message>brandName</f:message></th>
							<th></th>
						</tr>
						<c:forEach items="${listOfBrands}" var="i" varStatus="iterator">
							<tr
								class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">
								<td align="center" style="font-weight: bold;">${idPlant}</td>
								<td>${i.brandMark}</td>
								<td>${i.brandName}</td>
								<td align="center"><c:if test="${role['ADMINS']}">
										<a onClick="return confirm('Really?')"
											href="${pageContext.servletContext.contextPath}/srv/plants/removeBrand/${i.id}"><img
											title="DELETE"
											src="${pageContext.servletContext.contextPath}/ico/zrusit.png" /></a>
									</c:if></td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		<table style="table-layout: fixed;">
			<col width="350px" />
			<col width="600px" />
			<tr>
				<td><c:if test="${role['ADMINS']}">
						<form:form
							action="${pageContext.servletContext.contextPath}/srv/plants/addPlant"
							commandName="plant" name="pla">
							<table>
								<col width="50px" />
								<col width="250px" />
								<col width="50px" />
								<tr>
									<td bgcolor="#ffd200"><form:input path="id"
											cssStyle="width:90%;" title="Plant must be unique."></form:input></td>
									<td bgcolor="#ffd200"><form:input path="plantName"
											cssStyle="width:97%;"></form:input></td>
									<td align="center"><button
											onclick="return check('pla', {'id' : /^[0-9]{1,2}$/, 'plantName' : /^[a-zA-Z0-9]{1}[,-.a-zA-Z0-9\u0020]+$/});">Add</button></td>
								</tr>
							</table>
						</form:form>
					</c:if></td>
				<td><c:if test="${role['ADMINS']}">
						<c:if test="${not(empty(idPlant))}">
							<form:form
								action="${pageContext.servletContext.contextPath}/srv/plants/addBrand/${idPlant}"
								commandName="brand" name="bra">
								<table>
									<col width="50px" />
									<col width="100px" />
									<col width="400px" />
									<col width="50px" />
									<tr>
										<td></td>
										<td bgcolor="#ffd200"><form:input path="brandMark"
												cssStyle="width:95%;"></form:input></td>
										<td bgcolor="#ffd200"><form:input path="brandName"
												cssStyle="width:98%;"></form:input></td>
										<td align="center"><button onclick="return check('bra', {'brandMark' : /^([a-zA-Z0-9])+$/, 'brandName' : /^[a-zA-Z0-9]{1}[/,-.a-zA-Z0-9\u0020]+$/});">Add</button></td>
									</tr>
								</table>
							</form:form>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>

	</div>
	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>
