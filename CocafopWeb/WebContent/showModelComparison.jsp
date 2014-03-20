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
		<c:set scope="request" var="actual" value="modelComparison" />
		<jsp:include page="header.jsp" />
	</div>
	<BR />

	<div id="mainBody">

		<DIV>
			<TABLE style="table-layout: fixed;">
				<COL width="120px" />
				<COL width="100px" />
				<COL width="200px" />
				<COL width="250px" />
				<TR>
					<TD></TD>
					<TD><B><f:message>currentModel</f:message></B></TD>
					<TD style="padding-left: 30px;"><B><f:message>2ndModel</f:message></B></TD>
					<TD></TD>
				</TR>
				<TR>
					<TD><B><f:message>plant</f:message></B></TD>
					<TD bgcolor="WHITE" style="padding-left: 5px;">${mko.modelKey.brand.plant.id}</TD>
					<TD style="padding-left: 30px;"><form:form
							commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/calculations/showModelComparisonChooseBrand/${mko.id}">
							<form:select onchange="this.form.submit(); return true;"
								path="idPlant">
								<!-- ten WHEN slouzi k tomu, aby ze zacatku combo neukazovalo nic a posleze jen tu vybranou hodnotu -->
								<c:choose>
									<c:when test="${(empty(plantId))}">
										<form:option value="">- -</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="">${plantId}</form:option>
									</c:otherwise>
								</c:choose>
								<c:forEach var="i" items="${plantList}">
									<form:option value="${i.id}">${i.id}</form:option>
								</c:forEach>
							</form:select>
						</form:form></TD>
					<TD><form:form cssStyle="display:inline"
							commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/calculations/showModelComparison/${idMkOrder}">
							<input type="submit" value="Clear" />
						</form:form></TD>
				</TR>
				<TR>
					<TD><B><f:message>brand</f:message></B></TD>
					<TD bgcolor="WHITE" style="padding-left: 5px;">${mko.modelKey.brand.brandMark}</TD>
					<TD style="padding-left: 30px;"><c:if
							test="${(not(empty(plantId)))}">
							<form:form commandName="modelFilter"
								action="${pageContext.servletContext.contextPath}/srv/calculations/showModelComparisonChooseYMkMn/${mko.id}/${plantId}">
								<form:select onchange="this.form.submit(); return true;"
									path="brandMark">
									<c:choose>
										<c:when test="${(empty(brandMark))}">
											<form:option value="">- - - - - -</form:option>
										</c:when>
										<c:otherwise>
											<form:option value="">${brandMark}</form:option>
										</c:otherwise>
									</c:choose>
									<c:forEach var="i" items="${brandList}">
										<form:option value="${i.brandMark}">${i.brandMark}</form:option>
									</c:forEach>
								</form:select>
							</form:form>
						</c:if></TD>
					<TD></TD>
				</TR>
				<TR>
					<TD><B><f:message>modelKey</f:message></B></TD>
					<TD bgcolor="WHITE" style="padding-left: 5px;">${mko.modelKey.modelKey}</TD>
					<TD style="padding-left: 30px;">
						<c:if test="${(not(empty(brandMark)) and (empty(wrongPermissions)))}">
							<form:form commandName="modelFilter"
								action="${pageContext.servletContext.contextPath}/srv/calculations/showModelComparisonMonth/${mko.id}/${plantId}/${brandMark}">
								<form:select onchange="this.form.submit(); return true;"
									path="mix">
									<c:choose>
										<c:when test="${(empty(modelKey))}">
											<form:option value="">- - - - - -</form:option>
										</c:when>
										<c:otherwise>
											<form:option value="">${modelKey}</form:option>
										</c:otherwise>
									</c:choose>
									<c:forEach var="i" items="${mkList}">
										<form:option value="${i.id}">${i.rok} - ${i.modelKey} - ${i.modelNumber}</form:option>
									</c:forEach>
								</form:select>
							</form:form>
						</c:if></TD>
					<TD></TD>
				</TR>
				<TR>
					<TD><B><f:message>modelNumber</f:message></B></TD>
					<TD bgcolor="WHITE" style="padding-left: 5px;">${mko.modelKey.modelNumber}</TD>
					<TD style="padding-left: 30px;">${modelNumber}</TD>
					<TD></TD>
				</TR>
				<TR>
					<TD><B><f:message>year</f:message></B></TD>
					<TD bgcolor="WHITE" style="padding-left: 5px;">${mko.modelKey.rok}</TD>
					<TD style="padding-left: 30px;">${year}</TD>
					<TD></TD>
				</TR>
				<TR>
					<TD><B><f:message>month</f:message></B></TD>
					<TD bgcolor="WHITE" style="padding-left: 5px;">${mko.mesic}</TD>
					<TD style="padding-left: 30px;">
						<c:if test="${not(empty(modelKey))}">
							<form:form commandName="mkOrder"
								action="${pageContext.servletContext.contextPath}/srv/calculations/exportXlsComparison/${mko.id}">
								<form:select onchange="this.form.submit(); return true;"
									path="id">
									<c:choose>
										<c:when test="${(empty(month))}">
											<form:option value="">- - - - - -</form:option>
										</c:when>
										<c:otherwise>
											<form:option value="">${month}</form:option>
										</c:otherwise>
									</c:choose>
									<c:forEach var="i" items="${mkOrderList}">
										<form:option value="${i.id}">${i.mesic}</form:option>
									</c:forEach>
								</form:select>
							</form:form>
						</c:if></TD>
					<TD></TD>
				</TR>
			</TABLE>
		</DIV>


		<BR /> <BR /> <BR />
		<P>
		<BR /> <B><f:message>yourPermissionForCurrentModel</f:message></B>:&#160;${yourPermissionForCurrentModel}
		<BR /> <B><f:message>yourPermissionFor2ndModel</f:message></B>:&#160;${yourPermissionFor2ndModel}
				<c:if test="${(not(empty(wrongPermissions)))}">
					<span style="color: red;"><B>!!!!!!!!!!!!!!!!!!!!!!!!!</B></span>
				</c:if>
		</P>




	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>