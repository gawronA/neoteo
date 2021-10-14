<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<security:authorize access="hasRole('ROLE_ADMIN')" var="auth"/>

<div id="subscriptionModal" class="ui modal">
    <i class="close icon"></i>
    <div class="header" data-add="<spring:message code="dashboard.subscriptions.addSubscription"/>" data-edit="<spring:message code="dashboard.subscriptions.editSubscription"/>"></div>
    <div class="content"></div>
    <div class="actions">
        <button id="delete" class="ui left floated secondary button"><i class="ui inverted trash icon"></i><spring:message code="delete"/></button>
        <div class="ui cancel button"><spring:message code="cancel"/></div>
        <div class="ui approve primary button"><spring:message code="save"/></div>
    </div>
</div>
<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="dashboard.subscriptions.title"/></h2>
        <table class="ui striped celled table">
            <thead>
            <tr>
                <th><spring:message code="name"/></th>
                <th><spring:message code="subscription.basePrice"/></th>
                <th><spring:message code="subscription.bookingPrice"/></th>
                <th><spring:message code="subscription.hourPrice"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <%--@elvariable id="subscription" type="pl.local.neoteo.entity.Subscription"--%>
            <c:forEach items="${subscriptions}" var="subscription">
                <tr>
                    <td>${subscription.name}</td>
                    <td>${subscription.basePrice}</td>
                    <td>${subscription.bookingPrice}</td>
                    <td>${subscription.hourPrice}</td>
                    <c:if test="${auth}">
                        <td>
                            <button class="ui grey button subscriptions"
                                    data-id="${subscription.id}"
                                    data-name="${subscription.name}">
                                <spring:message code="edit"/>
                            </button>
                        </td>
                    </c:if>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${auth}">
            <button class="ui grey button subscriptions" data-id="0"><spring:message code="dashboard.subscriptions.addSubscription"/></button>
        </c:if>
        <c:if test="${not auth}">
            <a class="ui grey button" href="<c:url value="/prices"/>"><spring:message code="dashboard.subscriptions.changeSubscription"/></a>
        </c:if>
    </div>
</div>
