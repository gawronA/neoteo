<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
    <link href="<c:url value="/resources/semantic/semantic.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/semantic/Semantic-UI-Alert.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/site.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css" integrity="sha512-Cv93isQdFwaKBV+Z4X8kaVBYWHST58Xb/jVOcV9aRsGSArZsgAnFIhMpDoMDcFNoUtday1hdjn0nGp3+KZyyFw==" crossorigin="anonymous" />
    <tiles:insertAttribute name="stylesheets"/>
    <title><tiles:insertAttribute name="title"/></title>
</head>
<body>

<c:if test="${not empty info}">
    <input id="infoToast" type="hidden" value="<spring:message code="${info}"/>"/>
</c:if>
<c:if test="${not empty success}">
    <input id="successToast" type="hidden" value="<spring:message code="${success}"/>"/>
</c:if>
<c:if test="${not empty error}">
    <input id="errorToast" type="hidden" value="<spring:message code="${error}"/>"/>
</c:if>

<main class="flex-column">
    <tiles:insertAttribute name="body"/>
</main>
<tiles:insertAttribute name="footer"/>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"
        integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/semantic/semantic.min.js"/>"></script>
<script src="<c:url value="/resources/semantic/Semantic-UI-Alert.js"/>"></script>
<script src="<c:url value="/resources/scripts/site.js"/>"></script>
<tiles:insertAttribute name="scripts"/>
</body>
</html>
