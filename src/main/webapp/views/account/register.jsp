<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set value="" var="error"/>
<c:if test="${isError}">
    <c:set value="error" var="error"/>
</c:if>

<spring:message code="user.firstName" var="msgUserFirstName"/>
<spring:message code="user.lastName" var="msgUserLastName"/>
<spring:message code="user.email" var="msgUserEmail"/>
<spring:message code="user.telephoneNumber" var="msgUserTelephoneNumber"/>
<spring:message code="user.password" var="msgUserPassword"/>
<spring:message code="user.repeatPassword" var="msgUserRepeatPassword"/>
<spring:message code="user.pesel" var="msgUserPesel"/>
<spring:message code="property.name" var="msgPropertyName"/>
<spring:message code="none" var="none"/>

<tiles:insertAttribute name="navbar"/>
<div class="main-banner"></div>

<div class="ui expressive vertical borderless segment">
    <div class="ui centered grid container">
        <div class="eight wide column">
            <h1 class="ui dividing header"><spring:message code="account.register.register"/></h1>
            <form:form name="registerForm" class="ui form ${error}" modelAttribute="user" action="register" method="post">
                <c:set var="firstNameError">
                    <form:errors path="firstName"/>
                </c:set>
                <c:set var="lastNameError">
                    <form:errors path="lastName"/>
                </c:set>
                <c:set var="emailError">
                    <form:errors path="email"/>
                </c:set>
                <c:set var="telephoneNumberError">
                    <form:errors path="phone"/>
                </c:set>
                <c:set var="passwordError">
                    <form:errors path="password"/>
                </c:set>
                <c:set var="peselError">
                    <form:errors path="pesel"/>
                </c:set>

                <div class="ui error message">
                    <div class="header"><spring:message code="message.error"/></div>
                    <ul class="list">
                        <spring:hasBindErrors name="user">
                            <%--@elvariable id="error" type="jorg.springframework.validation.ObjectError"--%>
                            <c:forEach var="error" items="${errors.allErrors}">
                                <li><spring:message message="${error}" /></li>
                            </c:forEach>
                        </spring:hasBindErrors>
                    </ul>
                </div>

                <div class="field">
                    <label><spring:message code="user.contactInformation"/></label>
                    <div class="two fields">
                        <div class="field ${not empty firstNameError?"error":""}">
                            <form:input path="firstName" placeholder="${msgUserFirstName}"/>
                        </div>
                        <div class="field ${not empty lastNameError?"error":""}">
                            <form:input path="lastName" placeholder="${msgUserLastName}"/>
                        </div>
                    </div>
                </div>
                <div class="field">
                    <div class="two fields">
                        <div class="field ${not empty emailError?"error":""}">
                            <form:input path="email" placeholder="${msgUserEmail}"/>
                        </div>
                        <div class="field ${not empty telephoneNumberError?"error":""}">
                            <form:input path="phone" placeholder="${msgUserTelephoneNumber}"/>
                        </div>
                    </div>
                </div>
                <div class="field">
                    <label>Hasło</label>
                    <div class="two fields">
                        <div class="field ${not empty passwordError?"error":""}">
                            <form:input path="password" type="password" placeholder="${msgUserPassword}"/>
                        </div>
                        <div class="field ${not empty passwordError?"error":""}">
                            <input name="repeatPassword" type="password" placeholder="${msgUserRepeatPassword}"/>
                        </div>
                    </div>
                </div>
                <div class="field ${not empty peselError?"error":""}"">
                    <label><spring:message code="user.pesel"/></label>
                    <form:input path="pesel" placeholder="${msgUserPesel}"/>
                </div>
                <div class="field">
                    <div class="g-recaptcha" data-sitekey="6LcHySUaAAAAAFd79YDCxU2vv1o2WpDXzrDGW7io"></div>
                </div>
                <button class="ui button" type="submit"><spring:message code="account.register.signin"/></button>
            </form:form>
        </div>
    </div>
</div>


