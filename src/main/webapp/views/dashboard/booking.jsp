<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<spring:message code="booking.fromDate" var="msgBookingFromDate"/>
<spring:message code="booking.toDate" var="msgBookingToDate"/>
<spring:message code="booking.accepted" var="msgBookingAccepted"/>

<security:authorize access="hasRole('ROLE_ADMIN')" var="authAdmin"/>
<security:authorize access="hasRole('ROLE_EMPLOYEE') and not hasRole('ROLE_ADMIN')" var="authEmployee"/>

<c:set var="action" value="account/book"/>
<c:if test="${authAdmin or authEmployee}">
    <c:set var="action" value="bookings"/>
</c:if>


<form:form id="bookingModalForm" class="ui form" action="${action}" method="post" modelAttribute="booking">
    <form:input type="hidden" path="id"/>
    <div class="field">
        <div class="three fields">
            <div class="field">
                <label>${msgBookingFromDate}</label>
                <form:input path="fromTime" type="datetime-local" placeholder="${msgBookingFromDate}"/>
            </div>
            <div class="field">
                <label>${msgBookingToDate}</label>
                <form:input path="toTime" type="datetime-local" placeholder="${msgBookingToDate}"/>
            </div>
                <div class="field">
                    <c:if test="${auth}">
                        <div class="ui toggle checkbox">
                            <form:checkbox path="accepted"/>
                            <label>${msgBookingAccepted}</label>
                        </div>
                    </c:if>
                </div>
        </div>
    </div>
</form:form>
<form id="deleteModalForm" action="<c:url value="deleteBooking"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${booking.id}"/>
</form>
<form id="confirmBookingForm" action="<c:url value="/account/confirmBooking"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${booking.id}"/>
</form>
