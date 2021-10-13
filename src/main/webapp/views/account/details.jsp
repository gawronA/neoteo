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

<tiles:insertAttribute name="navbar"/>
<div class="main-banner"></div>

<div class="ui expressive vertical segment">
    <div class="ui one column centered grid container">
        <div class="twelve wide column">
            <form:form class="ui form" action="details" method="post" modelAttribute="user">
                <h1><spring:message code="account.details.title"/></h1>
                <form:input type="hidden" path="id"/>
                <form:input type="hidden" path="active"/>
                <input type="hidden" name="_payments" value="1"/>
                <input type="hidden" name="_bookings" value="1"/>
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
                <button class="ui grey button" type="submit"><spring:message code="account.details.updateInformation"/></button>
            </form:form>
            <form class="ui form" action="<c:url value="changePassword"/>" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${user.id}"/>
                <div class="field">
                    <label><spring:message code="account.details.changePassword"/></label>
                    <div class="two fields">
                        <div class="field">
                            <input name="password" type="password" placeholder="${msgUserPassword}"/>
                        </div>
                        <div class="field">
                            <input name="repeatPassword" type="password" placeholder="${msgUserRepeatPassword}"/>
                        </div>
                    </div>
                </div>
                <button class="ui grey button" type="submit"><spring:message code="account.details.changePassword"/></button>
            </form>
        </div>
    </div>
</div>
