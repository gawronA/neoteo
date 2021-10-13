<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertAttribute name="navbar"/>
<div class="main-banner"></div>

<div class="ui vertical center aligned borderless segment">
    <h1 class="header"><spring:message code="prices.header"/></h1>
</div>
<div class="ui expressive vertical segment">
    <div class="ui one column centered grid container">
        <div class="twelve wide column">
            <div class="ui divided items">
                <c:forEach items="${subscriptions}" var="subscription">
                    <div class="item">
                        <div class="content">
                            <a class="header">${subscription.name}</a>
                            <div class="ui two column center aligned grid">
                                <div class="four wide column">
                                    <h3>${subscription.hourPrice} zł</h3>
                                    <div class="extra">
                                        <span><spring:message code="prices.forHour"/></span>
                                    </div>
                                </div>
                                <div class="four wide column">
                                    <h4>${subscription.bookingPrice} zł</h4>
                                    <div class="extra">
                                        <span><spring:message code="prices.forBooking"/></span>
                                    </div>
                                </div>
                            </div>
                            <a class="ui right floated primary large button subscription"
                               href="<c:url value="/account/changeSubscription?id=${subscription.id}"/>">
                                <spring:message code="prices.take"/> ${subscription.basePrice} zł
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
