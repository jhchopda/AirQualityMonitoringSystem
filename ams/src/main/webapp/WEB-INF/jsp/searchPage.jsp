<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value = "/resources/css/search.css"/>">
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#but").change(function(){
			alert("fdnjn");
			$.ajax({
				url:"btnClk",
				type: "GET",
				success: function(data){
					alert("inside ajax");
					var slctSubcat = $("#cities"), option= "";
		            slctSubcat.empty();

		            for(var sb =0; sb<data.length; sb++){
		                option = option + "<option value='" + data[sb] + "'>" +data[sb] + "</option>";
		            }
		            alert(option);
		            slctSubcat.append(option);
		         },
				error: function(e){
					alert("inside alert: "+ e);
				}
			});
		});
	});
</script>
    
    </head>
    <body>
      <!-- <h1>Hello, world!</h1> -->

      
      <div class="bg">
        <nav class="navbar navbar-toggleable-md navbar-inverse" style="background-color: none;">
          <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
          </button>
          <a class="navbar-brand" href="#" style="padding-left: 80px;">AMS</a>

          <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link" href="homepage">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
            <div class="navbar-brand"  style="padding-left: 80px;">${user.username}</div>
          </li>
          </ul>
          <!-- <a href="#"><button class="btn-change7" type="submit" disabled="disabled">${user.username}</button></a>-->
          <c:set var="userType" value="${userType}"/>
          	<c:set var="admin" value="admin"/> 
          <c:if test="${userType eq  admin}">
              	<a href="adminInput"><button class="btn-change7" type="submit">Manu</button></a>
          </c:if>
          <c:url var="logout" value="/logout"/>
          <a href="${logout}"><button class="btn-change8" type="button">Logout</button></a>
        </div>
      </nav>
          <div class="main-name">
              <h1>Search</h1>
              <center><div class="underline"></div></center>
              <div class="search-table">
                  <center>
                  	
                  	<c:url var="Url" value="/processsearch"/>
		
					<form:form action="${Url}" >
						<label>state: </label>
						<form:select id= "but" path="state">
       						<form:option value="NONE" label="--- Select state ---" />
							<form:options items="${stateList}" />
       					</form:select>
       				
       					<label>city: </label> 
       					<form:select   id="cities" path="city">
    						<form:option value="-" label="--Select city--"/>    		
						</form:select>    		
       		
       					<input type="submit" value="submit"/>
					</form:form>
                  
                  </center>
              </div>
          </div>
        </div>
  <footer class="footer">
<p style="font-size: 4vw;"><img src="<c:url value="/resources/images/tata.png" />" style="width: 13%;"> CONSULTANCY SERVICES</p>
    <p>Copyright @2017 Tata Consultancy Services</p>
  </footer>
    <!-- jQuery first, then Tether, then Bootstrap JS. -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
  </body>
</html>