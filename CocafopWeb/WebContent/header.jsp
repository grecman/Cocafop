<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">

	<table width="1275px">
		<tr>
			<td colspan="3" width="100%">
				<div id="headerLogo" align="left">
					<a href="${pageContext.servletContext.contextPath}/srv/index">&#160;<img
						src="${pageContext.servletContext.contextPath}/images/logoSubPageGre.png"
						alt="LOGO" style="padding-left: 15px" /></a>
				</div>
			</td>
		</tr>
		<tr>
			<td align="left" style="padding-left: 25px">COCAFOP </td>
			<td align="center"><b><f:message>${actual}</f:message></b></td>
			<td title=" ${role['ADMINS'] ? 'ADMINS' : ''} ${role['USERS'] ? 'USERS' : ''}" align="right">User:&#160;<b>${pageContext.request.userPrincipal.name}</b>&#160;&#160;&#160;<a
					href="#" onClick="switchLocale('ENG');">EN</a>
			</td>
			
		</tr>

		<tr>
			<td colspan="3">
				<div id="headerBar">
					<span style="padding-left: 25px"> <a
						class="${actual eq 'models' ? 'active' : 'passive'}"
						href="${pageContext.servletContext.contextPath}/srv/models"><f:message>models</f:message></a></span>
					<span> <a
						class="${actual eq 'calculations' ? 'active' : 'passive'}"
						href="${pageContext.servletContext.contextPath}/srv/calculations"><f:message>calculations</f:message></a></span>
					<span> <a
						class="${actual eq 'plants' ? 'active' : 'passive'}"
						href="${pageContext.servletContext.contextPath}/srv/plants"><f:message>plants</f:message></a></span>
					<span> <a
						class="${actual eq 'administration' ? 'active' : 'passive'}"
						href="${pageContext.servletContext.contextPath}/srv/administration"><f:message>administrations</f:message></a></span>
					<span> <a
						class="${actual eq 'exchangeRates' ? 'active' : 'passive'}"
						href="${pageContext.servletContext.contextPath}/srv/exchangeRates"><f:message>exchangeRates</f:message></a></span>
					<span> <a class="${actual eq 'help' ? 'active' : 'passive'}"
						href="${pageContext.servletContext.contextPath}/srv/help"><f:message>help</f:message></a></span>
				</div>
			</td>
		</tr>
	</table>


</jsp:root>