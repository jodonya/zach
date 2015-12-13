<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<div>
<div><h4>Manage User Profiles</h1></div>
<div><a href="<c:out value="/logout"/>"> Logout</a></h1></div>
<div><a href="<c:out value="/login/"/>"> Home</a> </h1></div>
</div>
<div style="clear:both;"></div>
<h4> </h4>

<h4>Users List</h1>	
<h4> </h4>
<table>
	  	<tr>
    
     <td><strong></strong></td>
     <td></td>
     <td><strong>Login</strong></td>
     <td><strong>Email</strong></td>
     
     <td><strong>First Name</strong></td>
     <td><strong>Middle Name</strong></td>
     <td><strong>Last Name</strong></td>
     
      <td><strong></strong></td>
      <td><strong>Client ID</strong></td>
    </tr>
  <c:forEach items="${usersList}" var="user">

    <tr>
     <td></td>
      <td></td>
     <td><c:out value="${user.login}" /></td>
     <td><c:out value="${user.email}" /></td>
     <td><c:out value="${user.firstName}" /></td>
     
     <td><c:out value="${user.middleName}" /></td>
     <td><c:out value="${user.lastName}" /></td>
     
     
     <td></td>
     <!-- td><c:out value="${commit.hash}" /></td -->
     <td><c:out value="${user.clientId}" /></td>

    </tr>
  </c:forEach>
</table>

<h1>Update Users</h1>
<c:forEach items="${usersList}" var="user">
										<form:form name="userProfile" action='/addUser/' method='POST' commandName="userProfile" >
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="email" path="email" placeholder="${user.email}" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="login" path="login" placeholder="${user.login}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													
													
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="clientId" path="clientId" placeholder="${user.clientId}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="clientSecret" path="clientSecret" placeholder="${user.clientSecret}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													

													<!-- div class="space"></div>

													<div class="clearfix">
														
														<button > <b>Post</b></button></p>
													</div -->
													<label class="block clearfix">
														<button > <b>Update</b></button></p>
													</label>

													<div class="space-4"></div>
												</fieldset>
											</form:form>
 </c:forEach>											
											
											
<table>
	 <tr>
    
     <td><strong></strong></td>
     <td></td>
     <td><strong></strong></td>
     <td><strong></strong></td>
      <td><strong></strong></td>
      <td><strong></strong></td>
    </tr>
  <c:forEach items="${commitCommentList}" var="commitComment">

    <tr>
     <td></td>
      <td></td>
     <td><c:out value="${commitComment.comment}" /></td>
     <td><strong>by</strong> <c:out value="${commitComment.email}" /></td>
     <td><strong>at</strong> <c:out value="${commitComment.created}" /></td>
     <!-- td><c:out value="${commit.hash}" /></td -->
     <td></td>

    </tr>
  </c:forEach>
</table>
</body>
</html>