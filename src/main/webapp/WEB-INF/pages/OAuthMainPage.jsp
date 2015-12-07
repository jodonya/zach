<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<h2>Git Commits</h2>

<h4>Welcome : ${email}</h1>	
<h4> </h4>
<table>
	  	<tr>
    
     <td><strong></strong></td>
     <td></td>
     <td><strong>Commit(Message)</strong></td>
     <td><strong>Committer</strong></td>
      <td><strong>Repository</strong></td>
      <td><strong>Diff</strong></td>
    </tr>
  <c:forEach items="${listTheCommits}" var="commit">

    <tr>
     <td></td>
      <td></td>
     <td><c:out value="${commit.message}" /></td>
     <td><c:out value="${commit.login}" /></td>
     <td><c:out value="${commit.repository}" /></td>
     <!-- td><c:out value="${commit.hash}" /></td -->
     <td><a href="<c:out value="/diff/${commit.hash}"/>"> Diff</a> </td>

    </tr>
  </c:forEach>
</table>
</body>
</html>
