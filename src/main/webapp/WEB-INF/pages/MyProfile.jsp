<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<div>
<div><h4>${email} Profile</h1></div>
<div><a href="<c:out value="/logout"/>"> Logout</a> </h1></div>
</div>
<div style="clear:both;"></div>
<h4> </h4>

<h4>My Repositories</h1>	
<h4> </h4>
<table>
	  	<tr>
    
     <td><strong></strong></td>
     <td></td>
     <td><strong>Name</strong></td>
     <td><strong>Commits Count</strong></td>
      <td><strong></strong></td>
      <td><strong>Commits</strong></td>
    </tr>
  <c:forEach items="${repositories}" var="repository">

    <tr>
     <td></td>
      <td></td>
     <td><c:out value="${repository.name}" /></td>
     <td><c:out value="${repository.commitCount}" /></td>
     <td></td>
     <!-- td><c:out value="${commit.hash}" /></td -->
     <td><c:out value="${commits}" /></td>

    </tr>
  </c:forEach>
</table>

<h1>Update User</h1>
										<form:form name="userProfile" action='/updateUserProfile/${email}/' method='POST' commandName="userProfile" >
												<fieldset>
													Email
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="email" path="email" placeholder="${user.email}" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													<br/>
													Login
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="login" path="login" placeholder="${user.login}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													<br/>
													
													
													First Name
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="firstName" path="firstName" placeholder="${user.firstName}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													<br/>
													
													Middle Name
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="middleName" path="middleName" placeholder="${user.middleName}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													<br/>
													
													Last Name
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="lastName" path="lastName" placeholder="${user.lastName}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													<br/>
													
													
													Client ID
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="clientId" path="clientId" placeholder="${user.clientId}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													<br>
													Client Secret
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:input type="text" class="form-control" name="clientSecret" path="clientSecret" placeholder="${user.clientSecret}"/>
															
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													
													<br>
													<div class="space"></div>

													<div class="clearfix">
														
														<button > <b>Update</b></button></p>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form:form>
											
<table>
	 <tr>
    
     <td><strong></strong></td>
     <td></td>
     <td><strong></strong></td>
     <td><strong></strong></td>
      <td><strong></strong></td>
      <td><strong></strong></td>
    </tr>
</table>
</body>
</html>