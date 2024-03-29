<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message code="name" var="msgName"/>

<security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')" var="auth"/>

<c:if test="${not auth}">
    <form:form id="propertiesModalForm" class="ui form" action="properties" method="post" modelAttribute="property">
        <form:input type="hidden" path="id"/>
        <form:input type="hidden" path="active"/>
        <input type="hidden" name="utilityTypes" value="0"/>
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
        <c:if test="${property.records != null}">
            <div class="field">
                <label>Pomiary</label>
                <table style="width: 100%">
                    <thead>
                    <tr>
                        <th>Data</th>
                        <th>Medium</th>
                        <th>Zużycie</th>
                        <th>Koszt</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${property.records}" var="record">
                        <tr>
                            <th>${record.date}</th>
                            <th>${record.type.utilityName}</th>
                            <th><fmt:formatNumber type="number" maxFractionDigits="3" value="${record.amount}"/> ${record.type.unit}</th>
                            <th><fmt:formatNumber type="number" maxFractionDigits="2" value="${record.price}"/> zł</th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </form:form>
</c:if>

