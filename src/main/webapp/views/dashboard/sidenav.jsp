<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<security:authorize access="hasRole('ROLE_ADMIN')" var="authAdmin"/>
<security:authorize access="hasRole('ROLE_EMPLOYEE') and not hasRole('ROLE_ADMIN')" var="authEmployee"/>

<c:if test="${authAdmin}">
    <a class="item" href="<c:url value="/users"/>"><spring:message code="dashboard.sidenav.users"/></a>
    <a class="item" href="<c:url value="/payments"/>"><spring:message code="dashboard.sidenav.payments"/></a>
    <a class="item" href="<c:url value="/bookings"/>"><spring:message code="dashboard.sidenav.bookings"/></a>
    <a class="item" href="<c:url value="/subscriptions"/>"><spring:message code="dashboard.sidenav.subscriptions"/></a>
    <a class="item" href="<c:url value="/roles"/>"><spring:message code="dashboard.sidenav.roles"/></a>
</c:if>

<c:if test="${authEmployee}">
    <a class="item" href="<c:url value="/bookings"/>"><spring:message code="dashboard.sidenav.bookings"/></a>
</c:if>

<c:if test="${not authAdmin and not authEmployee}">
    <a class="item" href="<c:url value="/payments"/>"><spring:message code="dashboard.sidenav.payments"/></a>
    <a class="item" href="<c:url value="/bookings"/>"><spring:message code="dashboard.sidenav.bookings"/></a>
    <a class="item" href="<c:url value="/subscriptions"/>"><spring:message code="dashboard.sidenav.subscriptions"/></a>
</c:if>
