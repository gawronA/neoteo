<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="dashboard.admin"/>, ${name}</h1>

<div class="ui centered two column very relaxed grid">
    <div class="row">
        <div class="center aligned column">
            <div class="ui tiny statistic">
                <div class="value">
                    ${usersCount}
                </div>
                <div class="label">
                    <spring:message code="dashboard.registeredUsersCount"/>
                </div>
            </div>
        </div>
        <div class="center aligned column">
            <div class="ui tiny statistic">
                <div class="value">
                    ${inactiveUsersCount}
                </div>
                <div class="label">
                    <spring:message code="dashboard.inactiveUsersCount"/>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="center aligned column">
            <div class="ui tiny statistic">
                <div class="value">
                    ${totalBalance} zł
                </div>
                <div class="label">
                    <spring:message code="dashboard.paymentsCount"/>
                </div>
            </div>
        </div>
        <div class="center aligned column">
            <div class="ui tiny statistic">
                <div class="value">
                    ${totalPaidBalance} zł
                </div>
                <div class="label">
                    <spring:message code="dashboard.paidPaymentsCount"/>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="center aligned column">
            <div class="ui tiny statistic">
                <div class="value">
                    ${bookingsCount}
                </div>
                <div class="label">
                    <spring:message code="dashboard.bookingsCount"/>
                </div>
            </div>
        </div>
        <div class="center aligned column">
            <div class="ui tiny statistic">
                <div class="value">
                    ${pendingBookingsCount}
                </div>
                <div class="label">
                    <spring:message code="dashboard.pendingBookingsCount"/>
                </div>
            </div>
        </div>
    </div>
</div>


