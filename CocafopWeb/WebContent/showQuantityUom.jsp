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
		<c:set scope="request" var="actual" value="showQuantityUom" />
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
					<TD bgcolor="WHITE">${oneBom.mkOrder.modelKey.brand.plant.id}</TD>
					<TD style="padding-left: 35px;"><f:message>brand</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.modelKey.brand.brandMark}</TD>

					<TD style="padding-left: 35px;"><f:message>year</f:message>/<f:message>month</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.modelKey.rok}/${oneBom.mkOrder.mesic}</TD>
					<TD style="padding-left: 35px;"><f:message>modelKey</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.modelKey.modelKey}</TD>

					<TD style="padding-left: 35px;"><f:message>modelNumberShort</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.modelKey.modelNumber}</TD>
					<TD style="padding-left: 35px;"><f:message>knr</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.order.knr}</TD>

					<TD />
				</TR>
				<TR height="10px"></TR>
				<TR>
					<TD><f:message>type</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.modelKey.typ}</TD>
					<TD style="padding-left: 35px;"><f:message>colour</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.order.colour}</TD>

					<TD style="padding-left: 35px;"><f:message>deliveryProgramShort</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.order.deliveryProgram}</TD>
					<TD style="padding-left: 35px;"><f:message>modelYear</f:message></TD>
					<TD bgcolor="WHITE">${oneBom.mkOrder.order.modelYear}</TD>

					<TD style="padding-left: 35px;"><f:message>options</f:message></TD>
					<TD bgcolor="WHITE" colspan="3">${oneBom.mkOrder.modelKey.options}</TD>

					<TD />
				</TR>
				<TR height="10px"></TR>
			</TABLE>
		</DIV>
		<BR />

		<c:choose>
			<c:when test='${permPlantWrite}'>
				<h3>
					<f:message>plant</f:message>
				</h3>
				<TABLE>
					<col width="150px" />
					<col width="50px" />
					<col width="100px" />
					<TR>
						<TD><B><f:message>partNumber</f:message></B></TD>
						<TD colspan="2">${oneBom.partNumber}</TD>
					</TR>
					<TR>
						<TD><B><f:message>partDescription</f:message></B></TD>
						<TD colspan="2">${oneBom.partDescEng}</TD>
					</TR>
					<TR>
						<TD><B><f:message>price</f:message></B></TD>
						<TD>${oneBom.pricePlant}</TD>
						<TD />
					</TR>
					<TR>
						<TD><B><f:message>unitOfMaterial</f:message></B></TD>
						<TD>${oneBom.uom}</TD>
						<TD />
					</TR>
					<TR>
						<TD style="border-bottom-width: thin; border-bottom-style: solid;"><B><f:message>quantity</f:message></B></TD>
						<TD style="border-bottom-width: thin; border-bottom-style: solid;">${oneBom.quantity}</TD>
						<TD style="border-bottom-width: thin; border-bottom-style: solid;" />
					</TR>
					<TR />
					<form:form
						action="${pageContext.servletContext.contextPath}/srv/calculations/changeQuantityUomPlant/${oneBom.id}"
						commandName="bom">
						<TR>
							<TD><B><f:message>unitOfMaterial</f:message></B></TD>
							<TD style="color: gray;">${oneBom.uomPlant}</TD>
							<TD title="1-piece, 2-gram, 3-ml, 4-mm, 5-cm3"><form:input path="uomPlant" size="1"></form:input></TD>
						</TR>
						<TR>
							<TD><B><f:message>quantity</f:message></B></TD>
							<TD style="color: gray;">${oneBom.quantityPlant}</TD>
							<TD><form:input path="quantityPlant" size="10"></form:input></TD>
						</TR>

						<TR>
							<TD />
							<TD />
							<TD><input type="submit" value="Save" /></TD>
						</TR>
					</form:form>
				</TABLE>
			</c:when>
			<c:otherwise>
				<td></td>
				<td></td>
			</c:otherwise>
		</c:choose>


		<c:choose>
			<c:when test='${permBrandWrite}'>
				<h3>
					<f:message>brand</f:message>
				</h3>
				<TABLE>
					<col width="150px" />
					<col width="50px" />
					<col width="100px" />
					<TR>
						<TD><B><f:message>partNumber</f:message></B></TD>
						<TD>${oneBom.partNumber}</TD>
						<TD />
					</TR>
					<TR>
						<TD><B><f:message>partDescription</f:message></B></TD>
						<TD>${oneBom.partDescEng}</TD>
						<TD />
					</TR>
					<TR>
						<TD><B><f:message>price</f:message></B></TD>
						<TD>${oneBom.priceBrand}</TD>
						<TD />
					</TR>
					<TR>
						<TD><B><f:message>unitOfMaterial</f:message></B></TD>
						<TD>${oneBom.uom}</TD>
						<TD />
					</TR>
					<TR>
						<TD style="border-bottom-width: thin; border-bottom-style: solid;"><B><f:message>quantity</f:message></B></TD>
						<TD style="border-bottom-width: thin; border-bottom-style: solid;">${oneBom.quantity}</TD>
						<TD style="border-bottom-width: thin; border-bottom-style: solid;" />
					</TR>
					<TR />
					<form:form
						action="${pageContext.servletContext.contextPath}/srv/calculations/changeQuantityUomBrand/${oneBom.id}"
						commandName="bom">
						<TR>
							<TD><B><f:message>unitOfMaterial</f:message></B></TD>
							<TD style="color: gray;">${oneBom.uomBrand}</TD>
							<TD title="1-piece, 2-gram, 3-ml, 4-mm, 5-cm3"><form:input path="uomBrand" size="1"></form:input></TD>
						</TR>
						<TR>
							<TD><B><f:message>quantity</f:message></B></TD>
							<TD style="color: gray;">${oneBom.quantityBrand}</TD>
							<TD><form:input path="quantityBrand" size="10"></form:input></TD>
						</TR>

						<TR>
							<TD />
							<TD />
							<TD><input type="submit" value="Save" /></TD>
						</TR>
					</form:form>
				</TABLE>
			</c:when>
			<c:otherwise>
				<td></td>
				<td></td>
			</c:otherwise>
		</c:choose>

<BR /><BR /><BR /><BR /><BR /><BR />
		<TABLE width="60%" style="table-layout: fixed;">
			<TR>
				<TD>
					<h3>
						<f:message>createTime</f:message>
					</h3>
					<TABLE>
						<col width="50px" />
						<col width="150px" />
						<TR>
							<TD><B><f:message>user</f:message></B></TD>
							<TD>${oneBom.uuser}</TD>
						</TR>
						<TR>
							<TD><B><f:message>date</f:message></B></TD>
							<TD>${oneBom.utime}</TD>
						</TR>
					</TABLE>
				</TD>
				<TD><c:if test='${permPlantWrite}'>
						<h3>
							<f:message>updateTime</f:message>
						</h3>
						<TABLE>
							<col width="50px" />
							<col width="150px" />
							<TR>
								<TD><B><f:message>user</f:message></B></TD>
								<TD>${oneBom.uuserQuantityPlant}</TD>
							</TR>
							<TR>
								<TD><B><f:message>date</f:message></B></TD>
								<TD>${oneBom.utimeQuantityPlant}</TD>
							</TR>
						</TABLE>
					</c:if> <c:if test='${permBrandWrite}'>
						<h3>
							<f:message>updateTime</f:message>
						</h3>
						<TABLE>
							<col width="50px" />
							<col width="150px" />
							<TR>
								<TD><B><f:message>user</f:message></B></TD>
								<TD>${oneBom.uuserQuantityBrand}</TD>
							</TR>
							<TR>
								<TD><B><f:message>date</f:message></B></TD>
								<TD>${oneBom.utimeQuantityBrand}</TD>
							</TR>
						</TABLE>
					</c:if></TD>
			</TR>
		</TABLE>


	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>