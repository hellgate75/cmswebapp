<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<% request.setAttribute("pageType", "H"); %>
<fmt:setBundle basename="application" /> 

<html>
<head>
  <title><fmt:message key="welcome.title"/></title>
  <META HTTP-EQUIV=”Content-language” CONTENT=”en-EN”>
  <%@include file="./jsp/header.jspf" %>
</head>
<body bgcolor="white">
  <%@include file="./jsp/sidenav.jspf" %>
  <%@include file="./jsp/home.jspf" %>

<%@include file="./jsp/footer.jspf" %>

</body>
</html>