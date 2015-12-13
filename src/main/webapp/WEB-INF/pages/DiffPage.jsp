<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<div>
<div><h4>Diff User Mail: ${email}</h1></div>
<div><a href="<c:out value="/logout"/>"> Logout</a> </h1></div>
</div>
<div><a href="<c:out value="/myprofile/${email}/"/>"> Profile</a> </h1></div>

<div><a href="<c:out value="/login/"/>"> Home</a> </h1></div>
<div style="clear:both;"></div>
<h4> </h4>

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

<h1>Comments</h1>
										<form:form name="commitComment" action='/addComment/${commitHash}/${email}/' method='POST' commandName="commitComment" >
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<form:textarea type="text" class="form-control" name="comment" path="comment" placeholder="Comment" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														
														<button > <b>Post</b></button></p>
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