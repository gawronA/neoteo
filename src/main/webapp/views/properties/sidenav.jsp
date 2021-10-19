<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<security:authorize access="hasRole('ROLE_ADMIN')" var="authAdmin"/>
<security:authorize access="hasRole('ROLE_EMPLOYEE') and not hasRole('ROLE_ADMIN')" var="authEmployee"/>

<a class="item" href="<c:url value="/properties/properties"/>"><spring:message code="dashboard.sidenav.properties"/></a>
<security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')">
    <a class="item" href="<c:url value="/properties/utilities"/>"><spring:message code="dashboard.sidenav.utilities"/></a>
</security:authorize>

<c:if test="${authEmployee}">
    <a class="item" href="<c:url value="/bookings"/>"><spring:message code="dashboard.sidenav.bookings"/></a>
</c:if>

<c:if test="${not authAdmin and not authEmployee}">
    <a class="item" href="<c:url value="/payments"/>"><spring:message code="dashboard.sidenav.payments"/></a>
    <a class="item" href="<c:url value="/bookings"/>"><spring:message code="dashboard.sidenav.bookings"/></a>
    <a class="item" href="<c:url value="/subscriptions"/>"><spring:message code="dashboard.sidenav.subscriptions"/></a>
</c:if>
