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
<script src="${pageContext.servletContext.contextPath}/js/cocafop.js">
	/**/
</script>
</head>
<body>

	<div id="header">
		<c:set scope="request" var="actual" value="localContent" />
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
				<col width="75px" />
				<col width="120px" />
				<col width="*" />
				<col width="10px" />
				<TR height="10px"></TR>
				<TR>
					<TD><f:message>plant</f:message></TD>
					<TD bgcolor="WHITE">${mk.brand.plant.id}</TD>
					<TD style="padding-left: 45px;"><f:message>brand</f:message></TD>
					<TD bgcolor="WHITE">${mk.brand.brandMark}</TD>
					<TD />
					<TD />
					<TD style="padding-left: 45px;"><f:message>modelType</f:message></TD>
					<TD bgcolor="WHITE">${mk.typ}</TD>
					<TD style="padding-left: 45px;"><f:message>valid</f:message></TD>
					<TD bgcolor="WHITE">${mk.validFromYear}/${mk.validFromMonth} -
						${mk.validToYear}/${mk.validToMonth}</TD>
					<TD />
				</TR>
				<TR height="10px"></TR>
				<TR>
					<TD><f:message>modelNumber</f:message></TD>
					<TD bgcolor="WHITE">${mk.modelNumber}</TD>
					<TD style="padding-left: 45px;"><f:message>modelKey</f:message></TD>
					<TD bgcolor="WHITE">${mk.modelKey}</TD>
					<TD style="padding-left: 45px;"><f:message>colour</f:message></TD>
					<TD bgcolor="WHITE">${mk.colour}</TD>
					<TD style="padding-left: 45px;"><f:message>year</f:message></TD>
					<TD bgcolor="WHITE">${mk.rok}</TD>
					<TD style="padding-left: 45px;"><f:message>options</f:message></TD>
					<TD bgcolor="WHITE">${mk.options}</TD>
					<TD />
				</TR>
				<TR height="10px"></TR>
			</TABLE>
		</DIV>
		<BR />
		<DIV style="overflow: auto;">
			<TABLE width="100%">
				<col width="100px" />
				<col width="30px" />
				<col width="*px" />
				<col width="60px" />
				<col width="30px" />
				<col width="100px" />
				<col width="150px" />
				<col width="30px" />
				<tr>
					<th><f:message>partNumber</f:message></th>
					<th><f:message>type</f:message></th>
					<th><f:message>partDescription</f:message></th>
					<th><f:message>quantity</f:message></th>
					<th><f:message>uom</f:message></th>
					<th><f:message>userName</f:message></th>
					<th><f:message>date</f:message> / <f:message>time</f:message></th>
					<th></th>
				</tr>
				<c:forEach items="${listOfLocalContent}" var="i"
					varStatus="iterator">
					<tr class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">
						<td style="white-space: pre;">${i.partNumber}</td>
						<td align="center">${i.typ}</td>
						<td>${i.partDescription}</td>
						<td align="right">${i.quantity}</td>
						<td align="center">${i.uom}</td>
						<td align="center">${i.uuser}</td>
						<td align="center"><fmt:formatDate value="${i.utime}"
								pattern="dd/MM/yyyy hh:mm" /></td>
						<td align="center"><c:if test="${permWrite==i.typ}">
								<c:choose>
									<c:when test="${i.used}">
										<img title="Already used"
											src="${pageContext.servletContext.contextPath}/ico/progress.gif" />
									</c:when>
									<c:otherwise>
										<a onClick="return confirm('Really?')"
											href="${pageContext.servletContext.contextPath}/srv/models/removeLocalContent/${i.id}"><img
											title="DELETE"
											src="${pageContext.servletContext.contextPath}/ico/smazat.png" /></a>
									</c:otherwise>
								</c:choose>
							</c:if></td>
					</tr>
				</c:forEach>

				<c:if test='${permWrite=="L"}'>
					<tr>
						<td colspan="8" style="width: 100%"><hr /></td>
					</tr>
					<tr>
						<form:form
							action="${pageContext.servletContext.contextPath}/srv/models/addLocalContent/${mk.id}"
							commandName="localContent" name="loc">
							<td bgcolor="#ffd200"><form:input path="partNumber" size="15"
									title="WARNING: First letter of part number is usually blank (free space)!"></form:input></td>
							<td align="center">${permWrite}</td>
							<td bgcolor="#ffd200"><form:input path="partDescription" size="100"></form:input></td>
							<td bgcolor="#ffd200" align="right"><form:input path="quantity" size="6"></form:input></td>
							<td bgcolor="#ffd200" align="center" title="1-piece, 2-gram, 3-ml, 4-mm, 5-cm3"><form:input
									path="uom" size="1"></form:input></td>
							<td><button onclick="return check('loc', {'partNumber' : /^[a-zA-Z0-9\u0020]{1}[a-zA-Z0-9\u0020]{0,14}$/, 'partDescription' : /^[a-zA-Z0-9]{1}[/,-.a-zA-Z0-9\u0020]{0,35}$/,'quantity' : /^([,.0-9])+$/, 'uom' : /^[1-5]{1}$/});">Add</button></td> 
							<td></td>																								
							<td></td>
						</form:form>
					</tr>
				</c:if>

			</TABLE>
		</DIV>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>