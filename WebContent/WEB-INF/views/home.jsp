<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OTRS Auditor</title>
</head>
<body>
<h1>OTRS Auditor</h1>

<table>
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
			</tr>
		</c:forEach>
	</tbody>
</table>

</body>
</html>