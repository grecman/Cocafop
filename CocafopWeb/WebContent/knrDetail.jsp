<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt">

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
		<c:set scope="request" var="actual" value="knrDetail" />
		<jsp:include page="header.jsp" />
	</div>
	<BR />

	<div id="mainBody">
		<TABLE width="100%" style="table-layout: fixed;">
			<TR>
				<TD><f:message>plant</f:message></TD>
				<TD bgcolor="WHITE">${mko.modelKey.brand.plant.id}</TD>
				<TD style="padding-left: 50px;"><f:message>brand</f:message></TD>
				<TD bgcolor="WHITE">${mko.modelKey.brand.brandMark}</TD>
				<TD style="padding-left: 50px;"><f:message>year</f:message> / <f:message>month</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.rok} / ${oneOrder.mesic}</TD>
				<TD />
				<TD />
			</TR>
			<TR>
				<TD colspan="8"></TD>
			</TR>
			<TR>
				<TD colspan="2" title="idOrder: ${mko.modelKey.id}"><H4>
						<f:message>modelDescription</f:message>
					</H4></TD>
			</TR>
			<TR>
				<TD><f:message>modelKey</f:message></TD>
				<TD bgcolor="WHITE">${mko.modelKey.modelKey}</TD>
				<TD style="padding-left: 50px;"><f:message>colour</f:message></TD>
				<TD bgcolor="WHITE">${mko.modelKey.colour}</TD>
				<TD style="padding-left: 50px;"><f:message>approval</f:message>-<f:message>plant</f:message></TD>
				<TD style="padding-left: 30px;"><c:choose>
						<c:when test="${mko.plantApprove}">
							<img src="${pageContext.servletContext.contextPath}/ico/ok.png" />
						</c:when>
						<c:otherwise>
							<img
								src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
						</c:otherwise>
					</c:choose></TD>
				<TD />
				<TD />
			</TR>
			<TR>
				<TD><f:message>modelNumber</f:message></TD>
				<TD bgcolor="WHITE">${mko.modelKey.modelNumber}</TD>
				<TD style="padding-left: 50px;"><f:message>type</f:message></TD>
				<TD bgcolor="WHITE">${mko.modelKey.typ}</TD>
				<TD style="padding-left: 50px;"><f:message>approval</f:message>-<f:message>brand</f:message></TD>
				<TD style="padding-left: 30px;"><c:choose>
						<c:when test="${mko.brandApprove}">
							<img src="${pageContext.servletContext.contextPath}/ico/ok.png" />
						</c:when>
						<c:otherwise>
							<img
								src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
						</c:otherwise>
					</c:choose></TD>
				<TD />
				<TD />
			</TR>
			<TR>
				<TD><f:message>options</f:message></TD>
				<TD bgcolor="WHITE" colspan="3">${mko.modelKey.options}</TD>
				<TD />
				<TD />
				<TD />
				<TD />
			</TR>
			<TR>
			</TR>
			<TR>
				<TD colspan="2" title="idOrder: ${oneOrder.id}"><H4>
						<f:message>orderDescription</f:message>
					</H4></TD>
			</TR>
			<TR>
				<TD><f:message>knr</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.knr}</TD>
				<TD style="padding-left: 50px;"><f:message>ifa</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.kifa}</TD>
				<TD style="padding-left: 50px;"><f:message>vin</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.vin}</TD>
				<TD style="padding-left: 50px;"><f:message>productionDateShort</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.productionDate}</TD>
			</TR>
			<TR>
				<TD><f:message>mkVersion</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.modelKeyVersion}</TD>
				<TD style="padding-left: 50px;"><f:message>colour</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.colour}</TD>
				<TD style="padding-left: 50px;"><f:message>modelYear</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.modelYear}</TD>
				<TD style="padding-left: 50px;"><f:message>deliveryProgramShort</f:message></TD>
				<TD bgcolor="WHITE">${oneOrder.deliveryProgram}</TD>
			</TR>
			<TR>
				<TD><f:message>options</f:message></TD>
				<TD bgcolor="WHITE" colspan="3">${oneOrder.options}</TD>
				<TD />
				<TD />
				<TD />
				<TD />
			</TR>
		</TABLE>
		<TABLE width="94%" style="table-layout: fixed;">
			<TR>
				<TD><f:message>prDescription</f:message></TD>
			</TR>
			<TR>
				<TD bgcolor="WHITE" style="word-wrap: break-word;"><font
					size="4px" face="Courier New">${oneOrder.prDescription}</font></TD>
			</TR>

		</TABLE>
		<BR />
		<TABLE width="100%" style="table-layout: fixed;">
			<TR>
				<TD colspan="2"><H4>
						<f:message>lastUpdate</f:message>
					</H4></TD>
			</TR>
			<TR>
				<TD><f:message>userName</f:message></TD>
				<TD bgcolor="WHITE">${mko.uuser}</TD>
				<TD />
				<TD />
				<TD />
				<TD />
				<TD />
				<TD />
			</TR>
			<TR>
				<TD><f:message>date</f:message> / <f:message>time</f:message></TD>
				<TD bgcolor="WHITE">${mko.utime}</TD>
				<TD />
				<TD />
				<TD />
				<TD />
				<TD />
				<TD />
			</TR>
		</TABLE>

		<div>
		<BR />
			<form:form commandName="modelFilter"
				action="${pageContext.servletContext.contextPath}/srv/calculations/showMkOrders/${mko.modelKey.brand.plant.id}/${mko.modelKey.brand.brandMark}/${mko.modelKey.rok}?month=${mko.mesic}">
				<input type="submit" value="Back" />
			</form:form>
		</div>

	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>