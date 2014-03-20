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
<script src="${pageContext.servletContext.contextPath}/js/cocafop.js">
	/**/
</script>
</head>
<body>

	<div id="header">
		<c:set scope="request" var="actual" value="models" />
		<jsp:include page="header.jsp" />
	</div>
	<BR />

	<div id="mainBody">
		<DIV
			style="background-color: #dfd9d1; padding-left: 10px; padding-top: 10px; height: 40px;">
			<TABLE width="60%" style="table-layout: fixed;">
				<TR>
					<TD><form:form commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/models/chooseBrand/">
							<B><f:message>plant</f:message></B>:&#160;&#160;
				 				<form:select onchange="this.form.submit(); return true;" path="idPlant">
								<!-- ten IF slouzi k tomu, aby ze zacatku combo neukazovalo nic a posleze jen tu vybranou hodnotu -->
								<c:if test="${(empty(plantId))}">
									<form:option value="">- -</form:option>
								</c:if>
								<c:forEach var="i" items="${plantList}">
									<form:option value="${i.id}">${i.id}</form:option>
								</c:forEach>
							</form:select>
						</form:form></TD>
					<TD><form:form commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/models/chooseYear/${plantId}">
							<B><f:message>brand</f:message></B>:&#160;&#160;
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
						</form:form></TD>
					<TD><form:form commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/models/showModels/${plantId}/${brandMark}">
							<B><f:message>year</f:message></B>:&#160;&#160;
			 					<form:select path="year">
								<c:forEach var="i" items="${rokyList}">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select>&#160;&#160;
							<c:if test="${not(empty(rokyList))}">
								<input type="submit" value="Execute" />
							</c:if>
						</form:form></TD>
				</TR>
			</TABLE>
		</DIV>
		<BR />
		<DIV style="overflow: auto;">
			<TABLE width="100%">
				<col width="35px" />
				<col width="35px" />
				<col width="6px" />
				<col width="60px" />
				<col width="50px" />
				<col width="15px" />
				<col width="50px" />
				<col width="15px" />

				<col width="*" />
				<col width="180px" />
				<col width="70px" />
				<col width="130px" />
				<col width="50px" />
				<tr>
					<th><f:message>modelNumberShort</f:message></th>
					<th><f:message>type</f:message></th>
					<th><f:message>modelKey</f:message></th>
					<th><f:message>colour</f:message></th>
					<th colspan="2"><f:message>validFrom</f:message></th>
					<th colspan="2"><f:message>validTo</f:message></th>


					<th><f:message>options</f:message></th>
					<th><f:message>comment</f:message></th>
					<th><f:message>userName</f:message></th>
					<th><f:message>date</f:message> / <f:message>time</f:message></th>
					<th></th>
				</tr>
				<c:forEach items="${listOfModels}" var="i" varStatus="iterator">
					<!-- varStatus="iterator" vraci cislo rakdu na kterem zrovna je -->
					<tr class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">
						<!-- (iterator.index mod 2) zbytek po deleni dvojkou ... slouzi pro obarveni radku u vice tabulek vedle sebe -->
						<td align="center">${i.modelNumber}</td>
						<td align="center">${i.typ}</td>
						<td><a title="Local content"
							href="${pageContext.servletContext.contextPath}/srv/models/showLocalContent/${i.id}">${i.modelKey}</a></td>
						<td>${i.colour}</td>
						<td colspan="2">${i.validFromYear}/${i.validFromMonth}</td>
						<td colspan="2">${i.validToYear}/${i.validToMonth}</td>
						<td>${i.options}</td>
						<td>${i.commentUser}</td>
						<td align="center">${i.uuser}</td>
						<td align="center"><f:formatDate value="${i.utime}"
								pattern="dd/MM/yyyy hh:mm" /></td>
						<td align="left"><c:if test="${permWrite==i.typ}">
								<a
									href="${pageContext.servletContext.contextPath}/srv/models/showModelKey/${i.id}"><img
									title="Set Model Key"
									src="${pageContext.servletContext.contextPath}/ico/edit1.png" />&#160;</a>
							</c:if> <c:if test="${permWrite==i.typ}">
								<c:choose>
									<c:when test="${i.used}">
										<img title="Already used"
											src="${pageContext.servletContext.contextPath}/ico/progress.gif" />
									</c:when>
									<c:otherwise>
										<a onClick="return confirm('Really?')"
											href="${pageContext.servletContext.contextPath}/srv/models/removeModel/${i.id}/${rok}"><img
											title="DELETE"
											src="${pageContext.servletContext.contextPath}/ico/smazat.png" /></a>
									</c:otherwise>
								</c:choose>
							</c:if></td>
					</tr>
				</c:forEach>

				<c:if test="${not(empty(permWrite))}">
					<tr>
						<td colspan="13" style="width: 100%"><hr /></td>
					</tr>
					<tr>
						<form:form
							action="${pageContext.servletContext.contextPath}/srv/models/addModel/${plantId}/${brandMark}/${permWrite}/${rok}"
							commandName="modelKey" name="models">
							<td bgcolor="#ffd200"><form:input path="modelNumber" size="2"
									title="This number must be unique."></form:input></td>
							<td align="center">${permWrite}</td>
							<td bgcolor="#ffd200"><form:input path="modelKey" size="5"></form:input></td>
							<td bgcolor="#ffd200"><form:input path="colour" size="5"></form:input></td>
							<td align="right">${rok}-</td>
							<td bgcolor="#ffd200"><form:input path="validFromMonth" size="1"></form:input></td>
							<td align="right">${rok}-</td>
							<td bgcolor="#ffd200"><form:input path="validToMonth" size="1"></form:input></td>
							<td><form:input path="options" size="50"
									title="Example: 'L0L A8G 3FE PK0'"></form:input></td>
							<td><form:input path="commentUser" size="25"></form:input></td>
							<!-- <td><input type="submit" value="Add" /></td>  -->
							<td><button
									onclick="return check('models', {'modelNumber' : /[0-9]/, 'modelKey' : /^[a-zA-Z0-9\u0020]{6}$/, 'colour' : /^[A-Za-z0-9\u0020]{6}$/, 'validFromMonth' : /^[0-9]{1,2}$/, 'validToMonth' : /^[0-9]{1,2}$/, 'options' : /(^((.{0})|([A-Za-z0-9]{3}))(\u0020[A-Za-z0-9]{3})*)$/, 'commentUser' : /^[a-zA-Z0-9\u0020]{0,50}$/});">Set</button></td>
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