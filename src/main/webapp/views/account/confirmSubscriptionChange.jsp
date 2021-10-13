<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertAttribute name="navbar"/>
<div class="main-banner"></div>

<div class="ui expressive vertical segment">
    <div class="ui one column centered grid container">
        <div class="twelve wide column">
            <h1 clas="header"><spring:message code="account.confirmSubscriptionChange.title"/></h1>
            <p><spring:message code="user.subscription"/> ${subscription.name}</p>
            <p><spring:message code="payment.amount"/>: ${subscription.basePrice}</p>
            <form method="post" action="changeSubscription">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${subscription.id}"/>
                <button type="submit" class="ui primary button"><spring:message code="account.confirmSubscriptionChange.buy"/></button>
            </form>
        </div>
    </div>
</div>
