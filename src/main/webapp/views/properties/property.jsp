<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="name" var="msgName"/>

<security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')" var="auth"/>

<c:if test="${not auth}">
    <form:form id="propertiesModalForm" class="ui form" action="properties" method="post" modelAttribute="property">
        <form:input type="hidden" path="id"/>
        <form:input type="hidden" path="active"/>
            <div class="field">
                <label><spring:message code="name"/></label>
                <div class="field">
                    <form:input type="text" path="name" placeholder="${msgName}"/>
                </div>
            </div>
    </form:form>
</c:if>

<c:if test="${auth}">
    <form:form id="propertiesModalForm" class="ui form" action="properties" method="post" modelAttribute="property">
        <form:input type="hidden" path="id"/>
        <div class="two fields">
            <div class="field">
                <label><spring:message code="name"/></label>
                <form:input type="text" path="name" placeholder="${msgName}"/>
            </div>
            <div class="field">
                <div class="field">
                    <div class="ui toggle checkbox">
                        <form:checkbox path="active"/>
                        <label><spring:message code="property.active"/></label>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</c:if>

