<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Git OAuth Application - Zach</title>
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css">
<script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
<script type="text/javascript">

    //Plug-in to fetch page data 
	jQuery.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
	{
		return {
			"iStart":         oSettings._iDisplayStart,
			"iEnd":           oSettings.fnDisplayEnd(),
			"iLength":        oSettings._iDisplayLength,
			"iTotal":         oSettings.fnRecordsTotal(),
			"iFilteredTotal": oSettings.fnRecordsDisplay(),
			"iPage":          oSettings._iDisplayLength === -1 ?
				0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
			"iTotalPages":    oSettings._iDisplayLength === -1 ?
				0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
		};
	};

	

		// '${queryType}';
	//alert(queryType);
	
$(document).ready(function() {
	//queryType
	// for main maintype
	//for a user usertype
	//for a repository repositorytype
	//for add comment commenttype
	//for diff difftype
	var queryType = "<c:out value="${queryType}"/>";

	//for maintype
	 if (queryType == "maintype"){
	
	$("#example").dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sort": "position",
        //bStateSave variable you can use to save state on client cookies: set value "true" 
        "bStateSave": false,
        //Default: Page display length
        "iDisplayLength": 20,
        //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
        "iDisplayStart": 0,
        "fnDrawCallback": function () {
            //Get page numer on client. Please note: number start from 0 So
            //for the first page you will see 0 second page 1 third page 2...
            //Un-comment below alert to see page number
        	//alert("Current page number: "+this.fnPagingInfo().iPage);    
        },  

       
            "sAjaxSource": "/springPaginationDataTables.web/${email}/",
               

        
        "aoColumns": [
            { "mData": "message" },
            { "mData": "login" },
            { "mData": "repository" },
            { "mData": "diff" },
            { "mData": "ups" },
            { "mData": "downs" },
             
        ]
    } );

	 }


	//for user or author commits
		if (queryType == "usertype"){
			$("#example").dataTable( {
		        "bProcessing": true,
		        "bServerSide": true,
		        "sort": "position",
		        //bStateSave variable you can use to save state on client cookies: set value "true" 
		        "bStateSave": false,
		        //Default: Page display length
		        "iDisplayLength": 20,
		        //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
		        "iDisplayStart": 0,
		        "fnDrawCallback": function () {
		            //Get page numer on client. Please note: number start from 0 So
		            //for the first page you will see 0 second page 1 third page 2...
		            //Un-comment below alert to see page number
		        	//alert("Current page number: "+this.fnPagingInfo().iPage);    
		        },  

		       
	            "sAjaxSource": "/springPaginationDataTables.login/${email}/${login}/",
		               

		        
		        "aoColumns": [
		            { "mData": "message" },
		            { "mData": "login" },
		            { "mData": "repository" },
		            { "mData": "diff" },
		            { "mData": "ups" },
		            { "mData": "downs" },
		             
		        ]
		    } );

			 }


	//for diff
		if (queryType == "repositorytype"){
			$("#example").dataTable( {
		        "bProcessing": true,
		        "bServerSide": true,
		        "sort": "position",
		        //bStateSave variable you can use to save state on client cookies: set value "true" 
		        "bStateSave": false,
		        //Default: Page display length
		        "iDisplayLength": 20,
		        //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
		        "iDisplayStart": 0,
		        "fnDrawCallback": function () {
		            //Get page numer on client. Please note: number start from 0 So
		            //for the first page you will see 0 second page 1 third page 2...
		            //Un-comment below alert to see page number
		        	//alert("Current page number: "+this.fnPagingInfo().iPage);    
		        },  

		       
		            "sAjaxSource": "/springPaginationDataTables.repository/${email}/${repository}/",
		               

		        
		        "aoColumns": [
		            { "mData": "message" },
		            { "mData": "login" },
		            { "mData": "repository" },
		            { "mData": "diff" },
		            { "mData": "ups" },
		            { "mData": "downs" },
		             
		        ]
		    } );

			 }

		 

} );

</script>
</head>
<body>

<h2>Git Commits</h2>


<div>
<div><h1>Welcome : ${email}</h1></div>
<div><a href="<c:out value="/logout"/>"> Logout</a>| <a href="<c:out value="/myprofile/${email}/"/>"> Profile</a> |
 <a href="<c:out value="/userprofiles/${email}/"/>"> User Profiles (Admin)</a> | <a href="<c:out value="/clientprofile/${clientId}/"/>"> Client Profile</a> | <a href="<c:out value="/pullcommits"/>"> Pull Commits</a> </div>
</div>

<div style="clear:both;"></div>

<!-- for pagination -->
<form:form action="" method="GET">
<table width="100%" style="border: 3px;background: rgb(243, 244, 248);"><tr><td>
	<table id="example" class="display" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>Message</th>
     			<th>Committer</th>
     			<th>Repository</th>
     			<th>Diff</th>
     			<th>Ups</th>
     			<th>Downs</th>
            </tr>
        </thead>       
    </table>
    </td></tr></table>
</form:form>
</body>
</html>
