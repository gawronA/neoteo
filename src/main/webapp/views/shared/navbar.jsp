<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="localeCode" value="${pageContext.response.locale.toLanguageTag()}"/>

<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>

<form action="/logout" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<div class="ui inverted navbar segment">
    <div class="ui inverted borderless secondary menu">
        <div class="ui container">
            <%--            <img src="<c:url value="/resources/images/logo.png"/>" height="75px" width="75px" alt="logo"/>--%>
            <div class="header item">Mobility Park</div>
            <a class="right navbar item" href="<c:url value="/"/>"><spring:message code="navbar.start"/></a>
            <a class="navbar item" href="<c:url value="/bookings"/>"><spring:message code="navbar.book"/></a>
            <a class="navbar item" href="<c:url value="/prices"/>"><spring:message code="navbar.prices"/></a>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <a class="navbar item" href="<c:url value="/login"/>"><spring:message code="navbar.login"/></a>
                <a class="ui primary navbar button" href="<c:url value="/account/register"/>"><spring:message code="navbar.signin"/></a>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <div class="menu item">
                    <div class="ui dropdown navbar item">
                        <spring:message code="navbar.account"/>
                        <i class="dropdown icon"></i>
                        <div class="menu">
                            <a class="item" href="<c:url value="/account/details"/>"><spring:message code="navbar.accountDetails"/></a>
                            <div class="divider"></div>
                            <a class="item" href="javascript:formSubmit()"><spring:message code="navbar.logout"/></a>
                        </div>
                    </div>
                </div>
            </c:if>
            <div class="menu item">
                <div class="ui dropdown navbar item">
                    <spring:message code="navbar.language"/> <i class="dropdown icon"></i>
                    <div class="menu">
                        <a class="item" href="?lang=en"><span class="flag-icon flag-icon-gb"></span> English</a>
                        <div class="divider"></div>
                        <a class="item" href="?lang=pl"><span class="flag-icon flag-icon-pl"></span> Polski</a>
                        <div class="divider"></div>
                        <a class="item" href="?lang=es"><span class="flag-icon flag-icon-es"></span> Español</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
