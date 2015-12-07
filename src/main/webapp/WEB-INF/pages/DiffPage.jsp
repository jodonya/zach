<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<h2>Diff</h2>

<h4>Commit : ${commitMessage}</h1>	
<h4> </h4>
<table>
	  	<tr>
    
     <td><strong></strong></td>
     <td></td>
     <td><strong>Status</strong></td>
     <td><strong>File Name</strong></td>
      <td><strong></strong></td>
      <td><strong>Diff</strong></td>
    </tr>
  <c:forEach items="${theFiles}" var="commitFile">

    <tr>
     <td></td>
      <td></td>
     <td><c:out value="${commitFile.status}" /></td>
     <td><c:out value="${commitFile.fileName}" /></td>
     <td></td>
     <!-- td><c:out value="${commit.hash}" /></td -->
     <td><c:out value="${commitFile.patch}" /></td>

    </tr>
  </c:forEach>
</table>
</body>
</html>