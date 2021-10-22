<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<spring:message code="user.firstName" var="msgUserFirstName"/>
<spring:message code="user.lastName" var="msgUserLastName"/>
<spring:message code="user.email" var="msgUserEmail"/>
<spring:message code="user.phone" var="msgUserTelephoneNumber"/>
<spring:message code="user.password" var="msgUserPassword"/>
<spring:message code="user.repeatPassword" var="msgUserRepeatPassword"/>
<spring:message code="none" var="msgNone"/>

<%--ADD ACCOUNT--%>
<c:if test="${user.id == 0}">
    <form:form id="userModalForm" class="ui form" action="users" method="post" modelAttribute="user">
        <form:input type="hidden" path="id"/>
        <div class="field">
            <label><spring:message code="user.contactInformation"/></label>
            <div class="three fields">
                <div class="field">
                    <form:input path="firstName" placeholder="${msgUserFirstName}"/>
                </div>
                <div class="field">
                    <form:input path="lastName" placeholder="${msgUserLastName}"/>
                </div>
                <div class="field">
                    <form:input path="pesel" placeholder="PESEL"/>
                </div>
            </div>
        </div>
        <div class="field">
            <div class="two fields">
                <div class="field">
                    <form:input path="email" placeholder="${msgUserEmail}"/>
                </div>
                <div class="field">
                    <form:input path="phone" placeholder="${msgUserTelephoneNumber}"/>
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
                    <label><spring:message code="user.accountPermissions"/></label>
                    <form:select path="userRoles" multiple="true">
                        <form:options items="${userRoleList}" itemValue="id" itemLabel="role"/>
                    </form:select>
                </div>
            </div>
        </div>
    </form:form>
</c:if>

<%--EDIT ACCOUNT--%>
<c:if test="${user.id != 0}">
    <form:form id="userModalForm" class="ui form" action="users" method="post" modelAttribute="user">
        <form:input type="hidden" path="id"/>
        <div class="field">
            <label><spring:message code="user.contactInformation"/></label>
            <div class="three fields">
                <div class="field">
                    <form:input path="firstName" placeholder="${msgUserFirstName}"/>
                </div>
                <div class="field">
                    <form:input path="lastName" placeholder="${msgUserLastName}"/>
                </div>
                <div class="field">
                    <form:input path="pesel" placeholder="PESEL"/>
                </div>
            </div>
        </div>
        <div class="field">
            <div class="two fields">
                <div class="field">
                    <form:input path="email" placeholder="${msgUserEmail}"/>
                </div>
                <div class="field">
                    <form:input path="phone" placeholder="${msgUserTelephoneNumber}"/>
                </div>
            </div>
        </div>
        <div class="field">
            <label><spring:message code="user.accountPermissions"/></label>
            <div class="two fields">
                <div class="field">
                    <label><spring:message code="user.roles"/></label>
                    <form:select path="userRoles" multiple="true">
                        <form:options items="${userRoleList}" itemValue="id" itemLabel="role"/>
                    </form:select>
                    <p>
                        <spring:message code="user.chosenRoles"/>:
                        <c:forEach items="${user.userRoles}" var="role">
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
<form id="deleteModalForm" action="<c:url value="/users/deleteUser"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" id="deleteModalInputId" name="id" value="${user.id}"/>
</form>
