<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="name" var="msgName"/>
<spring:message code="payment.amount" var="msgPaymentAmount"/>
<spring:message code="payment.paid" var="msgPaymentPaid"/>

<form:form id="paymentModalForm" class="ui form" action="payments" method="post" modelAttribute="payment">
    <form:input type="hidden" path="id"/>
    <div class="field">
        <label><spring:message code="name"/></label>
        <div class="field">
            <form:input type="text" path="name" placeholder="${msgName}"/>
        </div>
        <div class="field">
            <div class="two fields">
                <div class="field">
                    <label><spring:message code="payment.amount"/></label>
                    <form:input path="amount" placeholder="${msgPaymentAmount}"/>
                </div>
                <div class="field">
                    <div class="ui toggle checkbox">
                        <form:checkbox path="paid"/>
                        <label>${msgPaymentPaid}</label>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>
<form id="deleteModalForm" action="<c:url value="deletePayment"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${payment.id}"/>
</form>
