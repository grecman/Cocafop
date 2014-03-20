<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" 
	version="2.0">
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
		<c:set scope="request" var="actual" value="administration" />
		<jsp:include page="header.jsp" />
	</div>

	<div id="mainBody" style="overflow: auto;">
		<BR />
		<DIV
			style="background-color: #dfd9d1; padding-left: 10px; padding-top: 10px; height: 40px;">
			<TABLE width="60%" style="table-layout: fixed;">
				<TR>
					<TD><form:form commandName="userApl"
							action="${pageContext.servletContext.contextPath}/srv/administration/showUser">
							<B><f:message>userName</f:message></B>:&#160;&#160;
 							<form:select path="netUserName">
								<c:if test="${(not(empty(selectedUser)))}">
									<form:option value="">${selectedUser}</form:option>
								</c:if>
								<c:forEach var="i" items="${uzivatelList}">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select>
							<input type="submit" value="ok" />
						</form:form></TD>
				</TR>
			</TABLE>
		</DIV>
		<BR />
		<TABLE width="100%" style="table-layout: auto; overflow: auto;">
			<TR>
				<TD><f:message>userName</f:message>:</TD>
				<TD bgcolor="WHITE"><b>${ldapUser.id}</b></TD>
				<TD style="padding-left: 45px;"><f:message>firstName</f:message>:</TD>
				<TD bgcolor="WHITE">${ldapUser.name}</TD>
				<TD style="padding-left: 45px;"><f:message>surname</f:message>:</TD>
				<TD bgcolor="WHITE">${ldapUser.surname}</TD>
				<TD style="padding-left: 45px;"><f:message>email</f:message>:</TD>
				<TD bgcolor="WHITE">${ldapUser.email}</TD>
			</TR>
			<TR>
				<TD><f:message>department</f:message>:</TD>
				<TD bgcolor="WHITE">${ldapUser.department}</TD>
				<TD style="padding-left: 45px;"><f:message>building</f:message>:</TD>
				<TD bgcolor="WHITE">${ldapUser.roomNumber}</TD>
				<TD style="padding-left: 45px;"><f:message>language</f:message>:</TD>
				<TD bgcolor="WHITE">${ldapUser.prefLang}</TD>
				<TD style="padding-left: 45px;"><f:message>phone</f:message>:</TD>
				<TD bgcolor="WHITE">${ldapUser.phone}</TD>
			</TR>
		</TABLE>
		<BR />

		<TABLE style="table-layout: auto; overflow: auto;">
			<TR>
				<TH><f:message>plant</f:message>:</TH>
				<c:forEach items="${listOfPermissionPlant}" var="i">
					<td align="center" width="40px" bgcolor="WHITE"><a
						href="${pageContext.servletContext.contextPath}/srv/administration/showBrands/${i.plant.id}/${i.userApl.netUserName}">${i.plant.id}</a></td>
				</c:forEach>
			</TR>
		</TABLE>

		<TABLE width="100%" style="table-layout: auto; overflow: auto;">
			<TR>
				<TD><H4>
						<f:message>calculatedPlants</f:message>
					</H4></TD>
				<TD><H4>
						<f:message>calculatedBrands</f:message>
					</H4></TD>
			</TR>
			<TR>
				<TD valign="top">
					<TABLE width="100%" style="table-layout: auto; overflow: auto;">
						<TR>
							<TH><f:message>plant</f:message></TH>
							<TH><f:message>read</f:message></TH>
							<TH><f:message>write</f:message></TH>
							<TH><f:message>approve</f:message></TH>
						</TR>
						<c:if test="${not(empty(onePermPlant.plant.id))}">
							<TR>
								<TD bgcolor="WHITE" align="center">${onePermPlant.plant.id}</TD>
								<TD bgcolor="WHITE" align="center"><c:choose>
										<c:when test="${onePermPlant.read}">
											<img
												src="${pageContext.servletContext.contextPath}/ico/ok.png" />
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
										</c:otherwise>
									</c:choose></TD>
								<TD bgcolor="WHITE" align="center"><c:choose>
										<c:when test="${onePermPlant.write}">
											<img
												src="${pageContext.servletContext.contextPath}/ico/ok.png" />
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
										</c:otherwise>
									</c:choose></TD>
								<TD bgcolor="WHITE" align="center"><c:choose>
										<c:when test="${onePermPlant.approve}">
											<img
												src="${pageContext.servletContext.contextPath}/ico/ok.png" />
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
										</c:otherwise>
									</c:choose></TD>
							</TR>
						</c:if>
						<TR>
							<TD colspan="4"><BR /> <BR /> <f:message>descPermPlantRead</f:message></TD>
						</TR>
						<TR>
							<TD colspan="4"><BR /> <f:message>descPermPlantWrite</f:message></TD>
						</TR>
						<TR>
							<TD colspan="4"><BR /> <f:message>descPermPlantApprove</f:message></TD>
						</TR>
					</TABLE>
				</TD>
				<TD valign="top">
					<TABLE width="100%" style="table-layout: auto; overflow: auto;">
						<TR>
							<TH><f:message>brand</f:message></TH>
							<TH><f:message>read</f:message></TH>
							<TH><f:message>write</f:message></TH>
							<TH><f:message>approve</f:message></TH>
							<TH></TH>
						</TR>
						<c:forEach items="${listOfPermissionBrand}" var="i"
							varStatus="iterator">
							<tr
								class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">
								<td title="ID: ${i.brand.id}">${i.brand.brandMark}</td>
								<td align="center"><c:choose>
										<c:when test="${i.read}">
											<img
												src="${pageContext.servletContext.contextPath}/ico/ok.png" />
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
										</c:otherwise>
									</c:choose></td>
								<td align="center"><c:choose>
										<c:when test="${i.write}">
											<img
												src="${pageContext.servletContext.contextPath}/ico/ok.png" />
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
										</c:otherwise>
									</c:choose></td>
								<td align="center"><c:choose>
										<c:when test="${i.approve}">
											<img
												src="${pageContext.servletContext.contextPath}/ico/ok.png" />
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
										</c:otherwise>
									</c:choose></td>
								<td align="center"><c:if test="${role['ADMINS']}">
										<a
											href="${pageContext.servletContext.contextPath}/srv/administration/showPermission/${onePermPlant.id}/${i.id}">
											<img title="EDIT"
											src="${pageContext.servletContext.contextPath}/ico/edit.gif" />
										</a>
									</c:if></td>
							</tr>
						</c:forEach>
						<TR>
							<TD colspan="5"><BR /> <BR /> <f:message>descPermBrandRead</f:message></TD>
						</TR>
						<TR>
							<TD colspan="5"><BR /> <f:message>descPermBrandWrite</f:message></TD>
						</TR>
						<TR>
							<TD colspan="5"><BR /> <f:message>descPermBrandApprove</f:message></TD>
						</TR>
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
