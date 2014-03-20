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
		<c:set scope="request" var="actual" value="bom" />
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

		<DIV
			style="background-color: #dfd9d1; padding-left: 10px; padding-top: 10px; height: 40px;">
			<TABLE width="100%" style="table-layout: fixed;">
				<col width="110px" />
				<col width="40px" />
				<col width="330px" />

				<col width="65px" />
				<col width="40px" />
				<col width="100px" />
				<col width="100px" />

				<col width="65px" />
				<col width="40px" />
				<col width="100px" />
				<col width="100px" />

				<col width="*px" />

				<TR>
					<form:form
						action="${pageContext.servletContext.contextPath}/srv/calculations/bom/${mko.id}"
						commandName="bomFilter">
						<TD><form:input path="partNumber" size="12"></form:input></TD>
						<TD><form:select path="typ">
								<c:forEach var="i" items="${typeList}">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select></TD>
						<TD><form:input path="partDescEng" size="35"></form:input></TD>
						<TD></TD>
						<TD></TD>
						<TD><form:input title="${bomFilter.pricePlantFrom}" path="pricePlantFrom" size="8"></form:input></TD>
						<TD><form:input	path="pricePlantTo" size="8"></form:input></TD>
						<TD></TD>
						<TD></TD>
						<TD><form:input path="priceBrandFrom" size="8"></form:input></TD>
						<TD><form:input	path="priceBrandTo" size="8"></form:input></TD>
						<TD><input type="submit" value="Execute" /></TD>
					</form:form>
				</TR>
			</TABLE>
		</DIV>


		<DIV class="scroll" style="height: 415px;">
			<TABLE width="100%" style="table-layout: fixed;">
				<col width="120px" />
				<col width="40px" />
				<col width="330px" />

				<col width="65px" />
				<col width="40px" />
				<col width="100px" />
				<col width="100px" />

				<col width="65px" />
				<col width="40px" />
				<col width="100px" />
				<col width="100px" />

				<col width="*px" />
				<tr>
					<th colspan="3"></th>
					<th colspan="4"><f:message>plant</f:message></th>
					<th colspan="4"><f:message>brand</f:message></th>
					<th></th>
				</tr>
				<tr>
					<th><f:message>partNumber</f:message></th>
					<th><f:message>type</f:message></th>
					<th><f:message>partDescription</f:message></th>

					<th><f:message>quantity</f:message></th>
					<th><f:message>uom</f:message></th>
					<th><f:message>price</f:message>/<f:message>uom</f:message></th>
					<th><f:message>price</f:message>/<f:message>car</f:message></th>

					<th><f:message>quantity</f:message></th>
					<th><f:message>uom</f:message></th>
					<th><f:message>price</f:message>/<f:message>uom</f:message></th>
					<th><f:message>price</f:message>/<f:message>car</f:message></th>

					<th></th>
				</tr>

				<c:forEach items="${bomList}" var="i" varStatus="iterator">
					<tr class="${ (iterator.index mod 2) == 0 ? 'rowOdd': 'rowEven' }">
						<c:choose>
							<c:when
								test='${(i.typ=="M" and (permPlantApprove or permBrandApprove) and not(zavodJeSchvaleny))}'>
								<td title="DELETE">&#160;<a
									onClick="return confirm('Really DELETE?')"
									href="${pageContext.servletContext.contextPath}/srv/calculations/deleteOneBom/${i.id}">${i.partNumber}</a>
								</td>
							</c:when>
							<c:otherwise>
								<td>&#160;${i.partNumber}</td>
							</c:otherwise>
						</c:choose>
						<td align="center">${i.typ}</td>
						<td title="${i.partDescEng}"
							style="overflow: hidden; white-space: nowrap;">${i.partDescEng}</td>
						<c:choose>
							<c:when test='${permPlantRead}'>
								<c:choose>
									<c:when test="${not(empty(i.quantityPlant))}">
										<td align="right" title="Orig.: ${i.quantity}"><FONT
											color="red"><B><f:formatNumber
														pattern="######0.00" value="${i.quantityPlant}" /></B></FONT></td>
									</c:when>
									<c:otherwise>
										<td align="right"><f:formatNumber pattern="######0.00"
												value="${i.quantity}" /></td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${not(empty(i.uomPlant))}">
										<td align="center" title="Orig.: ${i.uom}"><FONT
											color="red"><B>${i.uomPlant}</B></FONT></td>
									</c:when>
									<c:otherwise>
										<td align="center">${i.uom}</td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${not(empty(i.pricePlantNew))}">
										<td align="right" title="Orig.: ${i.pricePlant/exchRate.rate}"><FONT
											color="red"><B><f:formatNumber
														pattern="######0.00000"
														value="${i.pricePlantNew/exchRate.rate}" /></B></FONT></td>
									</c:when>
									<c:otherwise>
										<td align="right"><f:formatNumber pattern="######0.00000"
												value="${i.pricePlant/exchRate.rate}" /></td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${not(empty(i.pricePlantNew))}">
										<c:choose>
											<c:when test="${not(empty(i.quantityPlant))}">
												<td align="right"><f:formatNumber
														pattern="######0.00000"
														value="${i.pricePlantNew*i.quantityPlant/exchRate.rate}" /></td>
											</c:when>
											<c:otherwise>
												<td align="right"><f:formatNumber
														pattern="######0.00000"
														value="${i.pricePlantNew*i.quantity/exchRate.rate}" /></td>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${not(empty(i.quantityPlant))}">
												<td align="right"><f:formatNumber
														pattern="######0.00000"
														value="${i.pricePlant*i.quantityPlant/exchRate.rate}" /></td>
											</c:when>
											<c:otherwise>
												<td align="right"><f:formatNumber
														pattern="######0.00000"
														value="${i.pricePlant*i.quantity/exchRate.rate}" /></td>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test='${permBrandRead}'>
								<c:choose>
									<c:when test="${not(empty(i.quantityBrand))}">
										<td align="right" title="Orig.: ${i.quantity}"><FONT
											color="red"><B><f:formatNumber
														pattern="######0.00" value="${i.quantityBrand}" /></B></FONT></td>
									</c:when>
									<c:otherwise>
										<td align="right"><f:formatNumber pattern="######0.00"
												value="${i.quantity}" /></td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${not(empty(i.uomBrand))}">
										<td align="center" title="Orig.: ${i.uom}"><FONT
											color="red"><B>${i.uomBrand}</B></FONT></td>
									</c:when>
									<c:otherwise>
										<td align="center">${i.uom}</td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${not(empty(i.priceBrandNew))}">
										<td align="right" title="Orig.: ${i.priceBrand}"><FONT
											color="red"><B><f:formatNumber
														pattern="######0.00000" value="${i.priceBrandNew}" /></B></FONT></td>
									</c:when>
									<c:otherwise>
										<td align="right"><f:formatNumber pattern="######0.00000"
												value="${i.priceBrand}" /></td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${not(empty(i.priceBrandNew))}">
										<c:choose>
											<c:when test="${not(empty(i.quantityBrand))}">
												<td align="right"><f:formatNumber
														pattern="######0.00000"
														value="${i.priceBrandNew*i.quantityBrand}" /></td>
											</c:when>
											<c:otherwise>
												<td align="right"><f:formatNumber
														pattern="######0.00000"
														value="${i.priceBrandNew*i.quantity}" /></td>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${not(empty(i.quantityBrand))}">
												<td align="right"><f:formatNumber
														pattern="######0.00000"
														value="${i.priceBrand*i.quantityBrand}" /></td>
											</c:when>
											<c:otherwise>
												<td align="right"><f:formatNumber
														pattern="######0.00000" value="${i.priceBrand*i.quantity}" /></td>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>

						<TD>&#160;<c:if
								test="${(permPlantWrite and not(zavodJeSchvaleny)) or (permBrandWrite and not(znackaJeSchvalena))}">
								<form:form cssStyle="display:inline" commandName="modelFilter"
									action="${pageContext.servletContext.contextPath}/srv/calculations/showQuantityUom/${i.id}">
									<input type="submit" value="Q" />
								</form:form>
							</c:if>&#160; <c:if
								test="${(permPlantWrite and not(zavodJeSchvaleny)) or (permBrandWrite and not(znackaJeSchvalena))}">
								<form:form cssStyle="display:inline" commandName="modelFilter"
									action="${pageContext.servletContext.contextPath}/srv/calculations/showPrice/${i.id}">
									<input type="submit" value="$" />
								</form:form>
							</c:if></TD>
					</tr>
				</c:forEach>
			</TABLE>
		</DIV>
		<HR />
		<TABLE width="100%" style="table-layout: fixed;">
			<col width="120px" />
			<col width="40px" />
			<col width="330px" />

			<col width="65px" />
			<col width="40px" />
			<col width="100px" />
			<col width="100px" />

			<col width="65px" />
			<col width="40px" />
			<col width="100px" />
			<col width="100px" />

			<col width="*px" />
			<TR>
				<TD><f:message>localCurrency</f:message>:</TD>
				<c:choose>
					<c:when test="${not(empty(exchRate.currency))}">
						<TD bgcolor="white" align="center">${exchRate.currency}</TD>
					</c:when>
					<c:otherwise>
						<TD bgcolor="red"></TD>
					</c:otherwise>
				</c:choose>
				<TD align="right"><f:message>exchangeRate</f:message>(EUR):</TD>
				<c:choose>
					<c:when test="${not(empty(exchRate.rate))}">
						<TD bgcolor="white" align="center">${exchRate.rate}</TD>
					</c:when>
					<c:otherwise>
						<TD bgcolor="red"></TD>
					</c:otherwise>
				</c:choose>
				<TD />
				<TD align="right"><B>Total</B>:</TD>
				<c:choose>
					<c:when
						test="${permPlantRead or permPlantWrite or permPlantApprove}">
						<TD bgcolor="white" align="right"><f:formatNumber
								pattern="######0.00000" value="${totalPricePlant/exchRate.rate}" /></TD>
					</c:when>
					<c:otherwise>
						<TD bgcolor="white" align="right"></TD>
					</c:otherwise>
				</c:choose>
				<TD />
				<TD />
				<TD />
				<c:choose>
					<c:when
						test="${permBrandRead or permBrandWrite or permBrandApprove}">
						<TD bgcolor="white" align="right"><f:formatNumber
								pattern="######0.00000" value="${totalPriceBrand}" /></TD>
					</c:when>
					<c:otherwise>
						<TD bgcolor="white" align="right"></TD>
					</c:otherwise>
				</c:choose>
				<TD />
			</TR>
		</TABLE>
		<HR />
		<DIV
			style="background-color: #dfd9d1; padding-left: 10px; padding-top: 1px; height: 40px;">
			<TABLE width="100%" style="table-layout: fixed;">
				<TR>
					<TD><c:if
							test="${(permPlantWrite and not(zavodJeSchvaleny)) or (permBrandWrite and not(zavodJeSchvaleny))}">
							<form:form commandName="bom"
								action="${pageContext.servletContext.contextPath}/srv/calculations/showAddBom/${mko.id}">
								<input type="submit" value="Add part number" />
							</form:form>
						</c:if></TD>
					<TD><a
						href="${pageContext.servletContext.contextPath}/srv/calculations/exportXls/${mko.id}">&#160;<img
							src="${pageContext.servletContext.contextPath}/ico/excel32.png"
							alt="Export EXCEL" /></a></TD>
				</TR>
			</TABLE>
		</DIV>

	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>