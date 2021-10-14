<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')" var="auth"/>

<div id="bookingModal" class="ui modal">
    <i class="close icon"></i>
    <div class="header" data-add="<spring:message code="dashboard.bookings.addBooking"/>" data-edit="<spring:message code="dashboard.bookings.editBooking"/>"></div>
    <div class="content"></div>
    <div class="actions">
        <button id="delete" class="ui left floated secondary button"><i class="ui inverted trash icon"></i><spring:message code="delete"/></button>
        <div class="ui cancel button"><spring:message code="cancel"/></div>
        <c:if test="${auth}">
            <button id="confirm" class="ui black button"><spring:message code="dashboard.bookings.confirmAndMakePayment"/></button>
        </c:if>
        <div class="ui approve primary button"><spring:message code="save"/></div>
    </div>
</div>
<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="dashboard.bookings.title"/></h2>
        <table class="ui striped celled table">
            <thead>
            <tr>
                <th><spring:message code="user"/></th>
                <th><spring:message code="booking.fromDate"/></th>
                <th><spring:message code="booking.toDate"/></th>
                <th><spring:message code="booking.accepted"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <%--@elvariable id="booking" type="pl.local.neoteo.entity.Booking"--%>
            <c:forEach items="${bookings}" var="booking">
                <tr>
                    <td>${booking.appUser.firstName} ${booking.appUser.lastName}</td>
                    <td>${booking.fromTime}</td>
                    <td>${booking.toTime}</td>
                    <td>
                        <c:if test="${booking.accepted}">Tak</c:if>
                        <c:if test="${not booking.accepted}">Nie</c:if>
                    </td>
                    <td>
                        <button class="ui grey button bookings"
                                data-id="${booking.id}"
                                data-name="${booking.fromTime} - ${booking.toTime}">
                            <spring:message code="edit"/>
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <button class="ui grey button bookings" data-id="0"><spring:message code="dashboard.bookings.addBooking"/></button>
    </div>
</div>
