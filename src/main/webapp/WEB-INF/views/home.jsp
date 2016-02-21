<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html class="user">
<head>

<!--  -->
<%@include file="includes/metas.jsp" %>

<script type="text/javascript">
	$(function() {
		
	});
</script>

<title>Title der Seite</title>

</head>
<body>
<table>
  <thead>
  <tr>
    <th>path</th>
    <th>methods</th>
    <th>consumes</th>
    <th>produces</th>
    <th>params</th>
    <th>headers</th>
    <th>custom</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${handlerMethods}" var="endPoint">
    <tr>
      <td>${endPoint.patternsCondition}</td>
      <td>${endPoint.methodsCondition}</td>
      <td>${endPoint.consumesCondition}</td>
      <td>${endPoint.producesCondition}</td>
      <td>${endPoint.paramsCondition}</td>
      <td>${endPoint.headersCondition}</td>
      <td>${empty endPoint.customCondition ? "none" : endPoint.customCondition}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
