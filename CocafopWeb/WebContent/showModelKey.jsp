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
	
<script src="${pageContext.servletContext.contextPath}/js/cocafop.js">/**/</script>
</head>
<body>

	<div id="header">
		<c:set scope="request" var="actual" value="showModelKey" />
		<jsp:include page="header.jsp" />
	</div>

	<div id="mainBody" style="overflow: auto;">
		<TABLE>
			<col width="150px" />
			<col width="70px" />
			<TR>
				<TD><B><f:message>modelNumber</f:message></B></TD>
				<TD>${oneMk.modelNumber}</TD>
			</TR>
			<TR>
				<TD><B><f:message>type</f:message></B></TD>
				<TD>${oneMk.typ}</TD>
			</TR>
			<TR>
				<TD><B><f:message>modelKey</f:message></B></TD>
				<TD>${oneMk.modelKey}</TD>
			</TR>
		</TABLE>

		<BR />
		<TABLE>
			<col width="150px" />
			<col width="*px" />
			<col width="200px" />
			<form:form
				action="${pageContext.servletContext.contextPath}/srv/models/setModelKey/${oneMk.id}"
				commandName="modelKey" name="showMK">
				<TR>
					<TD><B><f:message>colour</f:message></B></TD>
					<TD bgcolor="#ffd200"><form:input path="colour" size="30"></form:input></TD>
					<TD style="color: gray;">${oneMk.colour}</TD>
				</TR>
				<TR>
					<TD><B><f:message>comment</f:message></B></TD>
					<TD><form:input path="commentUser" size="30"></form:input></TD>
					<TD style="color: gray;">${oneMk.commentUser}</TD>
				</TR>
				<TR>
					<TD><B><f:message>validFrom</f:message></B></TD>
					<TD>${oneMk.validFromYear}-<form:input path="validFromMonth" size="2"></form:input></TD>
					<TD style="color: gray;">${oneMk.validFromYear}-${oneMk.validFromMonth}</TD>
				</TR>
				<TR>
					<TD><B><f:message>validTo</f:message></B></TD>
					<TD>${oneMk.validToYear}-<form:input path="validToMonth" size="2"></form:input></TD>
					<TD style="color: gray;">${oneMk.validToYear}-${oneMk.validToMonth}</TD>
				</TR>
				<TR>
					<TD><B><f:message>options</f:message></B></TD>
					<TD><form:input path="options" size="30"></form:input></TD>
					<TD style="color: gray;">${oneMk.options}</TD>
				</TR>
				<TR>
					<TD />
					<TD />
					<!-- <TD><input type="submit" value="Set" /></TD> -->
					<TD><button onclick="return check('showMK', {'colour' : /^[A-Za-z0-9\u0020]{6}$/});">Set</button></TD> 
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
