<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib uri="/WEB-INF/functions.tld" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css" />
<script language="text/javascript" src="resources/bootstrap/js/jquery-1.8.2.min.js"></script>
<script language="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
<title>OTRS Auditor</title>
</head>
<body>
<h1>OTRS Auditor</h1>

<table class="table">
	<thead>
		<th>N&uacute;mero do chamado</th>
		<th>T&iacute;tulo</th>
		<th>Total de dias em aberto</th>
		<th>Estado</th>
		<th>Data de abertura</th>
		<th>Primeira resposta</th>
		<th>Fila</th>
	</thead>
	<tbody>
		<c:forEach items="${tickets}" var="ticket">
			<tr>
				<td><c:out value="${ticket.getNumber()}" /></td>
				<td><c:out value="${ticket.getTitle()}" /></td>
				<td><c:out value="${ticket.getTotalOpenDays()}" /></td>
				<td><c:out value="${ticket.getCurrentState().getState()}" /></td>
				<td><c:out value="${f:decorateDate(ticket.getOpenDate())}" /></td>
				<td>
				<c:choose>
				      <c:when test="${not empty ticket.getFirstResponseDate()}"><c:out value="${f:decorateDate(ticket.getFirstResponseDate())}" /></c:when>
				      <c:otherwise> --- </c:otherwise>
				</c:choose>
				</td>
				<td><c:out value="${ticket.getCurrentState().getQuee().getName()}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</body>
</html>