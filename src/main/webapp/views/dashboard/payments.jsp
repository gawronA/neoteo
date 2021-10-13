<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')" var="auth"/>

<div id="paymentModal" class="ui modal">
    <i class="close icon"></i>
    <div class="header" data-add="<spring:message code="dashboard.payments.addPayment"/>" data-edit="<spring:message code="dashboard.payments.editPayment"/>"></div>
    <div class="content"></div>
    <div class="actions">
        <button id="delete" class="ui left floated secondary button"><i class="ui inverted trash icon"></i><spring:message code="delete"/></button>
        <div class="ui cancel button"><spring:message code="cancel"/></div>
        <div class="ui approve primary button"><spring:message code="save"/></div>
    </div>
</div>
<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="dashboard.payments.title"/></h2>
        <table class="ui striped celled table">
            <thead>
            <tr>
                <th><spring:message code="user"/></th>
                <th><spring:message code="name"/></th>
                <th><spring:message code="payment.amount"/></th>
                <th><spring:message code="payment.paid"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${payments}" var="payment">
                <tr>
                    <td>${payment.appUser.firstName} ${payment.appUser.lastName}</td>
                    <td>${payment.name}</td>
                    <td>${payment.amount}</td>
                    <td>
                        <c:if test="${payment.paid}"><spring:message code="yes"/></c:if>
                        <c:if test="${not payment.paid}"><spring:message code="no"/></c:if>
                    </td>
                    <td>
                        <c:if test="${auth}">
                            <button class="ui grey button payments"
                                    data-id="${payment.id}"
                                    data-name="${payment.name}">
                                <spring:message code="edit"/>
                            </button>
                        </c:if>
                        <c:if test="${not auth and not payment.paid}">
                            <form action="account/pay" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="id" value="${payment.id}">
                                <button class="ui grey button payments" type="submit">
                                    <spring:message code="dashboard.payments.pay"/>
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${auth}">
            <button class="ui grey button payments" data-id="0"><spring:message code="dashboard.payments.addPayment"/></button>
        </c:if>
        <c:if test="${not auth}">
            <form action="account/pay" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <c:forEach items="${payments}" var="payment">
                    <c:if test="${not payment.paid}">
                        <input type="hidden" name="id" value="${payment.id}"/>
                    </c:if>
                </c:forEach>
                <button class="ui grey button payments" type="submit">
                    <spring:message code="dashboard.payments.payAll"/>
                </button>
                <a class="ui grey button" href="<c:url value="/account/printPayments"/>"><spring:message code="dashboard.payments.downloadPayments"/></a>
            </form>

        </c:if>
    </div>
</div>
