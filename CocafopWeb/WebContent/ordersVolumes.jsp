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
		<c:set scope="request" var="actual" value="ordersVolumes" />
		<jsp:include page="header.jsp" />
	</div>
	<BR />

	<div id="mainBody">

		<DIV style="background-color: #dfd9d1; padding-left: 10px;">
			<TABLE width="100%" style="table-layout: fixed;">
				<col width="100px" />
				<col width="30px" />
				<col width="120px" />
				<col width="75px" />

				<col width="120px" />
				<col width="75px" />
				<col width="120px" />
				<col width="*" />

				<col width="10px" />
				<TR height="10px"></TR>
				<TR>
					<TD><f:message>plant</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.brand.plant.id}</TD>
					<TD style="padding-left: 45px;"><f:message>brand</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.brand.brandMark}</TD>
					<TD style="padding-left: 45px;"><f:message>year</f:message>/<f:message>month</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.rok}/${mko.mesic}</TD>
					<TD />
					<TD />
					<TD />
				</TR>
				<TR height="10px"></TR>
				<TR>
					<TD><f:message>modelNumber</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.modelNumber}</TD>
					<TD style="padding-left: 45px;"><f:message>modelKey</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.modelKey}</TD>
					<TD style="padding-left: 45px;"><f:message>colour</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.colour}</TD>
					<TD style="padding-left: 45px;"><f:message>options</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.options}</TD>
					<TD />
				</TR>
				<TR height="10px"></TR>
			</TABLE>
		</DIV>
		<BR />
		<DIV style="overflow: auto;">
			<TABLE width="65%">
				<col width="60px" />
				<col width="70px" />
				<col width="*px" />
				<col width="70px" />
				<col width="70px" />
				<col width="100px" />
				<tr>
					<th><f:message>mkVersion</f:message></th>
					<th><f:message>colour</f:message></th>
					<th><f:message>options</f:message></th>
					<th><f:message>modelYear</f:message></th>
					<th><f:message>deliveryProgramShort</f:message></th>
					<th><f:message>count</f:message></th>
				</tr>
				<c:forEach items="${orderGroup}" var="i" varStatus="iterator">
					<tr class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">
						<td align="center">${i.modelKeyVersion}</td>
						<c:choose>
							<c:when test="${mko.modelKey.colour==i.colour}">
								<td align="center"><FONT color="blue"><B>${i.colour}</B></FONT></td>
							</c:when>
							<c:otherwise>
								<td align="center">${i.colour}</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${mko.modelKey.options==i.options}">
								<td><FONT color="blue"><B>${i.options}</B></FONT></td>
							</c:when>
							<c:otherwise>
								<td>${i.options}</td>
							</c:otherwise>
						</c:choose>
						<td align="center">${i.modelYear}</td>
						<td align="center">${i.deliveryProgram}</td>
						<td align="center" title="Choose KNR"><a onClick="return confirm('Really? Choose KNR. Delete a previous BOM and create a new one...')" href="${pageContext.servletContext.contextPath}/srv/calculations/chooseKnr/${mko.id}/${i.modelKeyVersion}/${i.colour}/${i.modelYear}/${i.deliveryProgram}/${empty(i.options) ? 'noOptions' : i.options}"><B>${i.count}</B></a></td> 
				</tr>
				</c:forEach>

			</TABLE>
		</DIV>

	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>