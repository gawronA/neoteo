<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="ui three column grid">
    <div class="four wide column">
        <h1><spring:message code="dashboard.hello"/>, ${user.firstName}!</h1>
    </div>
    <div class="eight wide column"></div>
    <div class="four wide column">
        <div class="ui small statistic">
            <div class="value">
                ${balance} zł
            </div>
            <div class="label">
                <spring:message code="dashboard.balance"/>
            </div>
        </div>
    </div>
</div>


<%--<div class="ui centered one column grid">--%>
<%--    <div class="row">--%>
<%--        <h3>Korzystasz z subskrybcji ${user.subscription.name}</h3>--%>
<%--    </div>--%>
<%--    <div class="row">--%>
<%--        <div class="ui tiny two flex-column middle statistics">--%>
<%--            <div class="statistic">--%>
<%--                <div class="value">--%>
<%--                    ${user.subscription.hourPrice}--%>
<%--                </div>--%>
<%--                <div class="label">--%>
<%--                    za godzinę postoju--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="statistic">--%>
<%--                <div class="value">--%>
<%--                    ${user.subscription.bookingPrice}--%>
<%--                </div>--%>
<%--                <div class="label">--%>
<%--                    za rezerwację--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<div class="ui horizontal statistics">
    <div class="statistic">
        <div class="value">
            ${user.bookings.size()}
        </div>
        <div class="label">
            <spring:message code="dashboard.bookingsCount"/>
        </div>
    </div>
    <div class="statistic">
        <div class="value">
            ${pendingPaymentsCount}
        </div>
        <div class="label">
            <spring:message code="dashboard.pendingPaymentsCount"/>
        </div>
    </div>
</div>

