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
<script src="${pageContext.servletContext.contextPath}/js/cocafop.js">/**/</script>	
</head>
<body>

	<div id="header">
		<c:set scope="request" var="actual" value="showBom" />
		<jsp:include page="header.jsp" />
	</div>
	<BR />

	<div id="mainBody">

		<DIV style="background-color: #dfd9d1; padding-left: 10px;">
			<TABLE width="100%" style="table-layout: fixed;">
				<col width="50px" />
				<col width="30px" />
				<col width="80px" />
				<col width="75px" />

				<col width="120px" />
				<col width="75px" />
				<col width="120px" />
				<col width="75px" />

				<col width="120px" />
				<col width="75px" />
				<col width="80px" />
				<col width="*" />

				<col width="10px" />
				<TR height="10px"></TR>
				<TR>
					<TD><f:message>plant</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.brand.plant.id}</TD>
					<TD style="padding-left: 35px;"><f:message>brand</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.brand.brandMark}</TD>

					<TD style="padding-left: 35px;"><f:message>year</f:message>/<f:message>month</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.rok}/${mko.mesic}</TD>
					<TD style="padding-left: 35px;"><f:message>modelKey</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.modelKey}</TD>

					<TD style="padding-left: 35px;"><f:message>modelNumberShort</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.modelNumber}</TD>
					<TD style="padding-left: 35px;"><f:message>knr</f:message></TD>
					<TD bgcolor="WHITE">${mko.order.knr}</TD>

					<TD />
				</TR>
				<TR height="10px"></TR>
				<TR>
					<TD><f:message>type</f:message></TD>
					<TD bgcolor="WHITE">${mko.modelKey.typ}</TD>
					<TD style="padding-left: 35px;"><f:message>colour</f:message></TD>
					<TD bgcolor="WHITE">${mko.order.colour}</TD>

					<TD style="padding-left: 35px;"><f:message>deliveryProgramShort</f:message></TD>
					<TD bgcolor="WHITE">${mko.order.deliveryProgram}</TD>
					<TD style="padding-left: 35px;"><f:message>modelYear</f:message></TD>
					<TD bgcolor="WHITE">${mko.order.modelYear}</TD>

					<TD style="padding-left: 35px;"><f:message>options</f:message></TD>
					<TD bgcolor="WHITE" colspan="3">${mko.modelKey.options}</TD>

					<TD />
				</TR>
				<TR height="10px"></TR>
			</TABLE>
		</DIV>
		<BR />

		<TABLE>
			<col width="150px" />
			<col width="200px" />
			<form:form
				action="${pageContext.servletContext.contextPath}/srv/calculations/addBom/${mko.id}"
				commandName="bom" name="boms">
				<TR>
					<TD><B><f:message>partNumber</f:message></B></TD>
					<TD bgcolor="#ffd200"><form:input path="partNumber" size="30"></form:input></TD>
				</TR>
				<TR>
					<TD><B><f:message>partDescription</f:message></B></TD>
					<TD bgcolor="#ffd200"><form:input path="partDescEng" size="30"></form:input></TD>
				</TR>
				<TR>
					<TD><B><f:message>unitOfMaterial</f:message></B></TD>
					<TD  bgcolor="#ffd200" title="1-piece, 2-gram, 3-ml, 4-mm, 5-cm3"><form:input path="uom" size="30"></form:input></TD>
				</TR>
				<TR>
					<TD><B><f:message>quantity</f:message></B></TD>
					<TD bgcolor="#ffd200"><form:input path="quantity" size="30"></form:input></TD>
				</TR>
				<TR>
					<TD />
					<!-- <TD><input type="submit" value="Save" /></TD>  -->
					<!-- v teto syntaxi se \s (tzn prazdne znaky)... nahrazuje unikodem \u0020 -->
					<TD><button onclick="return check('boms', {'partNumber' : /^[a-zA-Z0-9\u0020]{1}[a-zA-Z0-9\u0020]{0,14}$/, 'partDescEng' : /^[a-zA-Z0-9]{1}[/,-.a-zA-Z0-9\u0020]{0,35}$/, 'uom' : /^[1-5]{1}$/,'quantity' : /^([,.0-9])+$/});">Set</button></TD> 
				</TR>
			</form:form>
		</TABLE>

	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>