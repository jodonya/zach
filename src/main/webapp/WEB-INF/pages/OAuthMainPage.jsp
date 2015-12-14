<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>

<h2>Git Commits</h2>


<div>
<div><h4>Welcome : ${email}</h1></div>
<div><a href="<c:out value="/logout"/>"> Logout</a> </h1></div>
</div>
<div><a href="<c:out value="/myprofile/${email}/"/>"> Profile</a> </h1></div>
<div><a href="<c:out value="/userprofiles/${email}/"/>"> User Profiles (Admin)</a> </h1></div>
<br>
<div><a href="<c:out value="/clientprofile/${clientId}/"/>"> Client Profile</a> </h1></div>

<div><a href="<c:out value="/pullcommits"/>"> Pull Commits</a></h1></div>
<div style="clear:both;"></div>
<h4> </h4>
<table>
	  	<tr>
    
     <td><strong></strong></td>
     <td></td>
     <td><strong>Commit(Message)</strong></td>
     <td><strong>Committer</strong></td>
      <td><strong>Repository</strong></td>
      <td><strong>Diff</strong></td>
      <td></td>
      <td></td>
       <td></td>
      <td></td>
    </tr>
  <c:forEach items="${listTheCommits}" var="commit">

    <tr>
     <td></td>
      <td></td>
     <td><c:out value="${commit.message}" /></td>
     <td><c:out value="${commit.login}" /></td>
     <td><c:out value="${commit.repository}" /></td>
     <!-- td><c:out value="${commit.hash}" /></td -->
     <td><a href="<c:out value="/diff/${commit.hash}/${email}/"/>"> Diff</a> </td>
     <td><c:out value="     " /></td>
     <td><a href="<c:out value="/up/${commit.hash}/${email}/"/>">  +1   <c:out value="${fn:length(commit.listCommitUps)}"/> ups</a> </td>
     <td><c:out value="     " /></td>
     <td><a href="<c:out value="/down/${commit.hash}/${email}/"/>"> -1  <c:out value="${fn:length(commit.listCommitDowns)}"/> Downs</a> </td>

    </tr>
  </c:forEach>
  
  <tr>
    
     <td><strong></strong></td>
     <td></td>
     <td><strong></strong></td>
     <td><strong></strong></td>
      <td><strong></strong></td>
      <td><strong>${count} Commits</strong></td>
      <td></td>
      <td></td>
       <td></td>
      <td></td>
    </tr>
</table>
</body>
</html>
