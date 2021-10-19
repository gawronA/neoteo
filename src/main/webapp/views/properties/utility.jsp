<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<form:form id="utilitiesModalForm" class="ui form" action="utilities" method="post" modelAttribute="utilityType">
    <form:input type="hidden" path="id"/>
    <div class="three fields">
        <div class="field">
            <label><spring:message code="name"/></label>
            <form:input type="text" path="utilityName" placeholder="${msgName}"/>
        </div>
        <div class="field">
            <label>Cena</label>
            <form:input type="text" path="price" placeholder="${msgName}"/>
        </div>
        <div class="field">
            <label>Jednostka</label>
            <form:input type="text" path="unit" placeholder="${msgName}"/>
        </div>
    </div>
</form:form>
<form id="deleteModalForm" action="<c:url value="deleteUtilities"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${utilityType.id}"/>
</form>
