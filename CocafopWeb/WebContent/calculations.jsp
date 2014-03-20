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
</head>
<body>

	<div id="header">
		<c:set scope="request" var="actual" value="calculations" />
		<jsp:include page="header.jsp" />
	</div>

	<div id="mainBody" style="overflow: auto;">
		<BR />
		<DIV
			style="background-color: #dfd9d1; padding-left: 10px; padding-top: 10px; height: 40px;">
			<TABLE width="100%" style="table-layout: fixed;">
				<TR>
					<TD><form:form commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/calculations/choosePermissionBrand/">
							<B><f:message>plant</f:message></B>:&#160;&#160;
				 				<form:select onchange="this.form.submit(); return true;"
								path="idPlant">
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
							action="${pageContext.servletContext.contextPath}/srv/calculations/chooseYear/${plantId}">
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
							action="${pageContext.servletContext.contextPath}/srv/calculations/chooseMonth/${plantId}/${brandMark}">
							<B><f:message>year</f:message></B>:&#160;&#160;
								<form:select onchange="this.form.submit(); return true;"
								path="year">
								<c:choose>
									<c:when test="${(empty(rok))}">
										<form:option value="">- - - - - -</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="">${rok}</form:option>
									</c:otherwise>
								</c:choose>
								<c:forEach var="i" items="${rokyList}">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select>&#160;&#160;
						</form:form></TD>
					<TD><form:form commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/calculations/showMkOrders/${plantId}/${brandMark}/${rok}">
							<B><f:message>month</f:message></B>:&#160;&#160;
			 					<form:select path="month">
								<c:forEach var="i" items="${monthList}">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select>&#160;&#160;
							<c:if test="${not(empty(monthList))}">
								<input type="submit" value="Execute" />
							</c:if>
						</form:form></TD>
					<TD style="color: red;">
						<c:if test="${exchangeRateNotExists}">
								<b><f:message>noExchangeRate</f:message></b>
						</c:if>
					</TD>	
				</TR>
			</TABLE>
		</DIV>
		<BR />

		<DIV>
			<TABLE width="100%">
				<col width="35px" />
				<col width="35px" />
				<col width="60px" />
				<col width="60px" />
				<col width="240px" />

				<col width="120px" />
				<col width="60px" />
				<col width="240px" />
				<col width="35px" />
				<col width="35px" />

				<col width="35px" />
				<col width="35px" />
				<col width="*px" />
				<tr>
					<th><f:message>modelNumberShort</f:message></th>
					<th><f:message>type</f:message></th>
					<th><f:message>modelKey</f:message></th>
					<th><f:message>colour</f:message></th>
					<th><f:message>options</f:message></th>

					<th><f:message>knr</f:message></th>
					<th><f:message>colour</f:message></th>
					<th><f:message>options</f:message></th>
					<th><f:message>modelYear</f:message></th>
					<th><f:message>deliveryProgramShort</f:message></th>

					<th><f:message>plant</f:message></th>
					<th><f:message>brand</f:message></th>
					<th></th>
				</tr>
				<c:forEach items="${listOfMkOrder}" var="i" varStatus="iterator">

					<tr class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">

						<td align="center">${i.modelKey.modelNumber}</td>
						<td align="center">${i.modelKey.typ}</td>
						<td>${i.modelKey.modelKey}</td>
						<td>${i.modelKey.colour}</td>
						<td>${i.modelKey.options}</td>
						<td><a title="Detail KNR"
							href="${pageContext.servletContext.contextPath}/srv/calculations/knrDetail/${i.id}">${i.order.knr}</a></td>
						<td>${i.order.colour}</td>
						<td>${i.order.options}</td>
						<td align="center">${i.order.modelYear}</td>
						<td align="center">${i.order.deliveryProgram}</td>

						<td align="center"><c:choose>
								<c:when test="${i.plantApprove}">
									<img src="${pageContext.servletContext.contextPath}/ico/ok.png" />
								</c:when>
								<c:otherwise>
									<img
										src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
								</c:otherwise>
							</c:choose></td>
						<td align="center"><c:choose>
								<c:when test="${i.brandApprove}">
									<img src="${pageContext.servletContext.contextPath}/ico/ok.png" />
								</c:when>
								<c:otherwise>
									<img
										src="${pageContext.servletContext.contextPath}/ico/zrusit.png" />
								</c:otherwise>
							</c:choose></td>
						<td><c:if test="${permPlantWrite and not(zavodJeSchvaleny)}">
								<form:form cssStyle="display:inline" commandName="modelFilter"
									action="${pageContext.servletContext.contextPath}/srv/calculations/ordersVolumes/${i.id}">
									<input type="submit" value="KNR" />
								</form:form>
							</c:if> <c:if test="${not(empty(i.order.knr))}">
								<form:form cssStyle="display:inline" commandName="modelFilter"
									action="${pageContext.servletContext.contextPath}/srv/calculations/bom/${i.id}">
									<input type="submit" value="BOM" />
								</form:form>
							</c:if> 
						    <c:if test="${not(empty(i.order.knr))}">
								<form:form cssStyle="display:inline" commandName="modelFilter"
									action="${pageContext.servletContext.contextPath}/srv/calculations/showModelComparison/${i.id}">
									<input type="submit" value="Delta" />
								</form:form>
							</c:if>
							</td>
					</tr>
				</c:forEach>

			</TABLE>
		</DIV>

		<DIV
			style="background-color: #dfd9d1; padding-left: 10px; padding-top: 10px; height: 40px;">
			<TABLE width="100%" style="table-layout: fixed;">
				<!-- <TD><form:form commandName="modelFilter"
						action="${pageContext.servletContext.contextPath}/srv/calculations/greNECO_asi jen prazdny controller s opakovanym spustenim exportu viz DELTA">
						<input type="submit" value="Month differencies" />
					</form:form></TD>
				 -->
				<TD><c:if test="${permPlantWrite and not(zavodJeSchvaleny)}">
						<c:set var="modelUpdate"><f:message>modelUpdate</f:message></c:set>
						<form:form commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/calculations/updateMkOrders/${plantId}/${brandMark}/${rok}/${mesic}">
							<input type="submit" value="${modelUpdate}" />
						</form:form>
					</c:if></TD>
				<TD><c:if test="${permPlantApprove and not(zavodJeSchvaleny)}">
						<c:set var="approvePlant"><f:message>approvePlant</f:message></c:set>
						<form:form commandName="modelFilter" 
							action="${pageContext.servletContext.contextPath}/srv/calculations/approvePlant/${plantId}/${brandMark}/${rok}/${mesic}">
							<input onClick="return confirm('Are you sure??')" type="submit" value="${approvePlant}" />
						</form:form>
					</c:if></TD>
				<TD><c:if test="${permBrandApprove and zavodJeSchvaleny and not(znackaJeSchvalena)}">
						<c:set var="approveBrand"><f:message>approveBrand</f:message></c:set>
						<form:form commandName="modelFilter"
							action="${pageContext.servletContext.contextPath}/srv/calculations/approveBrand/${plantId}/${brandMark}/${rok}/${mesic}">
							<input onClick="return confirm('Are you sure??')" type="submit" value="${approveBrand}" />
						</form:form>
					</c:if></TD>
			</TABLE>
		</DIV>

	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>