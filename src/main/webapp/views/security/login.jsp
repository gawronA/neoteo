<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertAttribute name="navbar"/>
<div class="main-banner"></div>

<%--<c:if test="${not empty error}">--%>
<%--    <div class="error-box">${error}</div>--%>
<%--</c:if>--%>
<%--<c:if test="${not empty msg}">--%>
<%--    <div class="msg-box">${msg}</div>--%>
<%--</c:if>--%>

<div class="ui expressive vertical borderless segment">
    <div class="ui centered grid container">
        <div class="eight wide column">
            <h1 class="ui dividing header"><spring:message code="login.login"/></h1>
            <form class="ui form <c:if test="${not empty error}">error</c:if>" name="loginForm" action="<c:url value="/login"/>" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="field">
                    <label for="email"><spring:message code="user.email"/></label>
                    <input id="email" name="email" type="text" placeholder="<spring:message code="user.email"/>">
                </div>
                <div class="field">
                    <label for="password"><spring:message code="user.password"/></label>
                    <input id="password" name="password" type="password" placeholder="<spring:message code="user.password"/>">
                </div>

                <c:if test="${not empty error}">
                    <div class="ui error message">
                        <div class="header"><spring:message code="message.error"/></div>
                        <p><spring:message code="${error}"/></p>
                    </div>
                </c:if>

                <button class="ui button" type="submit"><spring:message code="login.login"/></button>
            </form>
        </div>
    </div>
</div>


