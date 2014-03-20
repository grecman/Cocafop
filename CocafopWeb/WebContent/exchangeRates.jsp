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
<script src="${pageContext.servletContext.contextPath}/js/cocafop.js">
	/**/
</script>	
</head>
<body>
	<div id="header">
		<c:set scope="request" var="actual" value="exchangeRates" />
		<jsp:include page="header.jsp" />
	</div>

	<BR />

	<div id="mainBody" style="overflow: auto;">

		<DIV style="background-color: #dfd9d1; padding-left: 10px; padding-top: 10px; height: 40px;">
			<TABLE width="60%" style="table-layout: fixed;">
				<TR>
					<TD><form:form commandName="plant"
							action="${pageContext.servletContext.contextPath}/srv/exchangeRates/showRates">
							<B><f:message>plant</f:message></B>:&#160;&#160;
 							<form:select path="id">
								<c:forEach var="i" items="${plantList}">
									<form:option value="${i.id}">${i.id}</form:option>
								</c:forEach>
							</form:select>
							<input type="submit" value="ok" />
						</form:form></TD>
				</TR>
			</TABLE>
		</DIV>
		<BR />

		<TABLE width="70%" style="table-layout: fixed;">
			<TR>
				<TD valign="top">
					<table width="100%"
						style="border-style: solid; border-width: thin; border-color: black;">
						<col width="130px" />
						<col width="150px" />
						<col width="100px" />
						<col width="*" />
						<tr>
							<th><f:message>year</f:message> / <f:message>month</f:message></th>
							<th><f:message>exchangeRate</f:message></th>
							<th><f:message>currency</f:message></th>
							<th></th>
						</tr>
						<c:forEach items="${listOfExchangeRates}" var="i"
							varStatus="iterator">
							<!-- varStatus="iterator" vraci cislo rakdu na kterem zrovna je -->
							<tr
								class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">
								<!-- (iterator.index mod 2) zbytek po deleni dvojkou ... slouzi pro obarveni radku u vice tabulek vedle sebe -->
								<td>${i.rok} / ${i.mesic}</td>
								<td align="right">${i.rate}</td>
								<td align="center">${i.currency}</td>
								<td align="center">
								<c:if
										test="${role['ADMINS']}">
										<c:choose>
											<c:when test="${i.used}">
												<img title="Already used"
													src="${pageContext.servletContext.contextPath}/ico/progress.gif" />
											</c:when>
											<c:otherwise>
												<a onClick="return confirm('Really?')"
													href="${pageContext.servletContext.contextPath}/srv/exchangeRates/removeExchangeRate/${i.id}"><img
													title="DELETE"
													src="${pageContext.servletContext.contextPath}/ico/zrusit.png" /></a>
											</c:otherwise>
										</c:choose>
									</c:if></td>
							</tr>
						</c:forEach>
					</table>
				</TD>
				<TD valign="top">
					<TABLE width="70%">
						<col width="100px" />
						<col width="150px" />
						<col width="80px" />
						<c:if test="${not(empty(idPlant))}">
							<c:if test="${role['ADMINS']}">
								<form:form
									action="${pageContext.servletContext.contextPath}/srv/exchangeRates/addRate/${idPlant}"
									commandName="exchangeRate" name="exchr">
									<TR>
										<TD></TD>
										<TD colspan="2"><B><f:message>createNewExchangeRate</f:message>:</B></TD>
									</TR>
									<TR>
										<TD></TD>
										<TD><f:message>year</f:message>:</TD>
										<TD bgcolor="#ffd200"><form:input path="rok" cssStyle="width:90%;" /></TD>
									</TR>
									<TR>
										<TD></TD>
										<TD><f:message>month</f:message>:</TD>
										<TD bgcolor="#ffd200"><form:input path="mesic" cssStyle="width:90%;" /></TD>
									</TR>
									<TR>
										<TD></TD>
										<TD><f:message>exchangeRate</f:message>:</TD>
										<TD bgcolor="#ffd200"><form:input path="rate" cssStyle="width:90%;" /></TD>
									</TR>
									<TR>
										<TD></TD>
										<TD><f:message>currency</f:message>:</TD>
										<TD bgcolor="#ffd200"><form:input path="currency" cssStyle="width:90%;" /></TD>
									</TR>
									<TR>
										<TD></TD>
										<TD></TD>
										<!-- <TD><input type="submit" value="Add" /></TD>  -->
										<TD><button onclick="return check('exchr', {'rok' : /[0-9]{4}/, 'mesic' : /[0-9]/, 'rate' : /^([,.0-9])+$/ , 'currency' : /^([a-zA-Z])+$/ });">Add</button></TD>
									</TR>
								</form:form>
							</c:if>
						</c:if>
					</TABLE>
				</TD>
			</TR>
		</TABLE>

	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>