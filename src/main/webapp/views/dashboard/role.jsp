<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="name" var="msgName"/>

<%--@elvariable id="appUserRole" type="pl.local.neoteo.entity.AppUserRole"--%>
<form:form id="roleModalForm" class="ui form" action="roles" method="post" modelAttribute="appUserRole">
    <form:input type="hidden" path="id"/>
    <div class="field">
        <label>${msgName}</label>
        <form:input path="role" placeholder="${msgName}"/>
    </div>
</form:form>
<form id="deleteModalForm" action="<c:url value="deleteRole"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" id="deleteModalInputId" name="id" value="${appUserRole.id}"/>
</form>
