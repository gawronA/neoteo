<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="name" var="msgName"/>
<spring:message code="subscription.basePrice" var="msgSubscriptionBasePrice"/>
<spring:message code="subscription.bookingPrice" var="msgSubscriptionBookingPrice"/>
<spring:message code="subscription.hourPrice" var="msgSubscriptionHourPrice"/>


<form:form id="subscriptionModalForm" class="ui form" action="subscriptions" method="post" modelAttribute="subscription">
    <form:input type="hidden" path="id"/>
    <div class="field">
        <label>${msgName}</label>
        <div class="field">
            <form:input type="text" path="name" placeholder="${msgName}"/>
        </div>
        <div class="field">
            <div class="three fields">
                <div class="field">
                    <label>${msgSubscriptionBasePrice}</label>
                    <form:input path="basePrice" placeholder="${msgSubscriptionBasePrice}"/>
                </div>
                <div class="field">
                    <label>${msgSubscriptionBookingPrice}</label>
                    <form:input path="bookingPrice" placeholder="${msgSubscriptionBookingPrice}"/>
                </div>
                <div class="field">
                    <label>${msgSubscriptionHourPrice}</label>
                    <form:input path="hourPrice" placeholder="${msgSubscriptionHourPrice}"/>
                </div>
            </div>
        </div>
    </div>
</form:form>
<form id="deleteModalForm" action="<c:url value="deleteSubscription"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${subscription.id}"/>
</form>
