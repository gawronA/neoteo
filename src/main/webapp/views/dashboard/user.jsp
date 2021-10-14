<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<spring:message code="user.firstName" var="msgUserFirstName"/>
<spring:message code="user.lastName" var="msgUserLastName"/>
<spring:message code="user.email" var="msgUserEmail"/>
<spring:message code="user.telephoneNumber" var="msgUserTelephoneNumber"/>
<spring:message code="user.password" var="msgUserPassword"/>
<spring:message code="user.repeatPassword" var="msgUserRepeatPassword"/>
<spring:message code="none" var="msgNone"/>

<%--ADD ACCOUNT--%>
<c:if test="${appUser.id == 0}">
    <form:form id="userModalForm" class="ui form" action="users" method="post" modelAttribute="appUser">
        <form:input type="hidden" path="id"/>
        <div class="field">
            <label><spring:message code="user.contactInformation"/></label>
            <div class="two fields">
                <div class="field">
                    <form:input path="firstName" placeholder="${msgUserFirstName}"/>
                </div>
                <div class="field">
                    <form:input path="lastName" placeholder="${msgUserLastName}"/>
                </div>
            </div>
        </div>
        <div class="field">
            <div class="two fields">
                <div class="field">
                    <form:input path="email" placeholder="${msgUserEmail}"/>
                </div>
                <div class="field">
                    <form:input path="telephoneNumber" placeholder="${msgUserTelephoneNumber}"/>
                </div>
            </div>
        </div>
        <div class="field">
            <label><spring:message code="user.account"/></label>
            <div class="two fields">
                <div class="field">
                    <label><spring:message code="user.password"/></label>
                    <form:input path="password" type="password" placeholder="${msgUserPassword}"/>
                </div>
                <div class="field">
                    <label><spring:message code="user.subscription"/></label>
                    <form:select path="subscription">
                        <form:options items="${subscriptions}" itemValue="id" itemLabel="name"/>
                    </form:select>
                </div>
            </div>
        </div>
        <div class="field">
            <label><spring:message code="user.accountPermissions"/></label>
            <form:select path="appUserRoles" multiple="true">
                <form:options items="${appUserRoleList}" itemValue="id" itemLabel="role"/>
            </form:select>
        </div>
    </form:form>
</c:if>

<%--EDIT ACCOUNT--%>
<c:if test="${appUser.id != 0}">
    <form:form id="userModalForm" class="ui form" action="users" method="post" modelAttribute="appUser">
        <form:input type="hidden" path="id"/>
        <div class="field">
            <label><spring:message code="user.contactInformation"/></label>
            <div class="two fields">
                <div class="field">
                    <form:input path="firstName" placeholder="${msgUserFirstName}"/>
                </div>
                <div class="field">
                    <form:input path="lastName" placeholder="${msgUserLastName}"/>
                </div>
            </div>
        </div>
        <div class="field">
            <div class="two fields">
                <div class="field">
                    <form:input path="email" placeholder="${msgUserEmail}"/>
                </div>
                <div class="field">
                    <form:input path="telephoneNumber" placeholder="${msgUserTelephoneNumber}"/>
                </div>
            </div>
        </div>
        <div class="field">
            <label><spring:message code="user.account"/></label>
            <div class="two fields">
                <div class="field">
                    <label><spring:message code="user.subscription"/></label>
                    <form:select path="subscription">
                        <c:forEach items="${subscriptions}" var="subscription">
                            <c:if test="${subscription.id == appUser.subscription.id}">
                                <form:option value="${subscription.id}" label="${subscription.name}" selected="true"/>
                            </c:if>
                            <c:if test="${subscription.id != appUser.subscription.id}">
                                <form:option value="${subscription.id}" label="${subscription.name}"/>
                            </c:if>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="field">
                    <label><spring:message code="user.cardNumber"/></label>
                    <div class="ui message">${appUser.card.number}</div>
                </div>
            </div>
        </div>
        <div class="field">
            <label><spring:message code="user.payments"/></label>
            <form:select path="payments" multiple="true">
                <%--@elvariable id="appUser" type="pl.local.neoteo.entity.AppUser"--%>
                <form:option value="0" label="${msgNone}"/>
                <c:forEach items="${appUser.payments}" var="payment">
                    <c:if test="${payment.paid}"><form:option value="${payment.id}" label="${payment.amount}zł - OPŁACONA - ${payment.name}"/></c:if>
                    <c:if test="${not payment.paid}"><form:option value="${payment.id}" label="${payment.amount}zł - DO ZAPŁACENIA - ${payment.name}"/></c:if>
                </c:forEach>
                <c:forEach items="${freePayments}" var="payment">
                    <c:if test="${payment.paid}"><form:option value="${payment.id}" label="** ${payment.amount}zł - OPŁACONA - ${payment.name} **"/></c:if>
                    <c:if test="${not payment.paid}"><form:option value="${payment.id}" label="** ${payment.amount}zł - DO ZAPŁACENIA - ${payment.name} **"/></c:if>
                </c:forEach>
            </form:select>
        </div>
        <div class="field">
            <label><spring:message code="user.bookings"/></label>
            <form:select path="bookings" multiple="true">
                <%--@elvariable id="appUser" type="pl.local.neoteo.entity.AppUser"--%>
                <form:option value="0" label="${msgNone}"/>
                <c:forEach items="${appUser.bookings}" var="booking">
                    <c:if test="${booking.accepted}"><form:option value="${booking.id}" label="${booking.fromTime} - ${booking.toTime} ZAAKCEPTOWANA"/></c:if>
                    <c:if test="${not booking.accepted}"><form:option value="${booking.id}" label="${booking.fromTime} - ${booking.toTime} NIE ZAAKCEPTOWANA"/></c:if>
                </c:forEach>
                <c:forEach items="${freeBookings}" var="booking">
                    <c:if test="${booking.accepted}"><form:option value="${booking.id}" label="** ${booking.fromTime} - ${booking.toTime} ZAAKCEPTOWANA **"/></c:if>
                    <c:if test="${not booking.accepted}"><form:option value="${booking.id}" label="** ${booking.fromTime} - ${booking.toTime} NIE ZAAKCEPTOWANA **"/></c:if>
                </c:forEach>
            </form:select>
        </div>
        <div class="field">
            <label><spring:message code="user.accountPermissions"/></label>
            <div class="two fields">
                <div class="field">
                    <label><spring:message code="user.roles"/></label>
                    <form:select path="appUserRoles" multiple="true">
                        <form:options items="${appUserRoleList}" itemValue="id" itemLabel="role"/>
                    </form:select>
                    <p>
                        <spring:message code="user.chosenRoles"/>:
                        <c:forEach items="${appUser.appUserRoles}" var="role">
                            ${role.role},
                        </c:forEach>
                    </p>
                </div>
                <div class="field">
                    <div class="ui toggle checkbox">
                        <form:checkbox id="userModalInputActive" path="active"/>
                        <label><spring:message code="user.active"/></label>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</c:if>
<form id="deleteModalForm" action="<c:url value="deleteUser"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" id="deleteModalInputId" name="id" value="${appUser.id}"/>
</form>
