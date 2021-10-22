<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="full-page flex-column">

<%--    NAVBAR--%>
<tiles:insertAttribute name="navbar"/>

<%--    MAIN BANNER--%>
    <div class="main-banner flex-column center middle">
        <h1 class="header"><spring:message code="home.header"/></h1>
<%--        <a class="ui primary button" href="<c:url value="/bookings/"/>"><spring:message code="home.bookNow"/> <i class="arrow right icon"></i></a>--%>
    </div>
</div>

<%--    ABOUT--%>
<div class="ui expressive vertical segment">
    <div class="ui very relaxed grid container">
        <div class="eight wide column">
            <h1 class="ui header"><spring:message code="home.leftColumnTitle"/></h1>
            <p>
                <spring:message code="home.leftColumnContent"/>
            </p>
        </div>
        <div class="eight wide column">
            <h1 class="ui header"><spring:message code="home.rightColumnTitle"/></h1>
            <p>
                <spring:message code="home.rightColumnContent"/>
            </p>
        </div>
    </div>
</div>
<%--

&lt;%&ndash;    HOW TO&ndash;%&gt;
<div class="ui expressive vertical how-to segment">
    <div class="ui very relaxed grid container">
        <div class="four wide column">
            <i class="google play inverted icon"></i>
            <h3 class="ui inverted header"><spring:message code="home.tutorialStepOne"/></h3>
        </div>
        <div class="four wide column">
            <i class="barcode inverted icon"></i>
            <h3 class="ui inverted header"><spring:message code="home.tutorialStepTwo"/></h3>
        </div>
        <div class="four wide column">
            <i class="map marker alternate inverted icon"></i>
            <h3 class="ui inverted header"><spring:message code="home.tutorialStepThree"/></h3>
        </div>
        <div class="four wide column">
            <i class="mobile alternate inverted icon"></i>
            <h3 class="ui inverted header"><spring:message code="home.tutorialStepFour"/></h3>
        </div>
    </div>
</div>
--%>







<%--
<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css" integrity="sha512-Cv93isQdFwaKBV+Z4X8kaVBYWHST58Xb/jVOcV9aRsGSArZsgAnFIhMpDoMDcFNoUtday1hdjn0nGp3+KZyyFw==" crossorigin="anonymous" />
    <link href="<c:url value="/resources/css/site.css"/>" rel="stylesheet">
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
</head>
<body>
    <div class="header"><tiles:insertAttribute name="header"/></div>
    <div class="navbar"><tiles:insertAttribute name="navbar"/></div>
    <div class="body">
        <div class="sidenav"><tiles:insertAttribute name="sidenav"/></div>
        <div class="content"><tiles:insertAttribute name="content"/></div>
    </div>
    <div class="footer"><tiles:insertAttribute name="footer"/></div>
</body>
<footer>

</footer>
</html>
--%>
