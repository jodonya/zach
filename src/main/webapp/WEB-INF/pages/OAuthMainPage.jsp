<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<h2>Git Commits</h2>

<h4>Welcom : ${email}</h1>	
<h4> </h4>
<table>
	  	<tr>
    
     <td><strong>Email</strong></td>
     <td></td>
     <td><strong>Commit #.</strong></td>
     <td><strong></strong></td>
      <td><strong></strong></td>
      <td><strong></strong></td>
    </tr>
  <!--c:forEach items="${listTransaction}" var="transaction" -->

    <tr>
    
     <td><!-- c:out value="${transaction.createdStamp}" / --></td>
     <td></td>
     <td></td>
     <td></td>
      <td></td>
      <td></td>
    </tr>
  <!-- /c:forEach -->
</table>
</body>
</html>
