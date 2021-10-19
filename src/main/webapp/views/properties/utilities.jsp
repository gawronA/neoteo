<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="utilitiesModal" class="ui modal">
    <i class="close icon"></i>
    <div class="header" data-add="<spring:message code="utilities.addUtility"/>" data-edit="<spring:message code="utilities.editUtility"/>"></div>
    <div class="content"></div>
    <div class="actions">
        <button id="delete" class="ui left floated secondary button"><i class="ui inverted trash icon"></i><spring:message code="delete"/></button>
        <div class="ui cancel button"><spring:message code="cancel"/></div>
        <div class="ui approve primary button"><spring:message code="save"/></div>
    </div>
</div>

<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="utilities.title"/></h2>
        <c:if test="${empty utilityTypes}">
            <p><spring:message code="utilities.noUtilities"/></p>
        </c:if>
        <c:if test="${not empty utilityTypes}">
            <table class="ui striped celled table">
                <thead>
                <tr>
                    <th><spring:message code="name"/></th>
                    <th>Cena</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${utilityTypes}" var="utilityType">
                    <tr>
                        <th>${utilityType.utilityName}</th>
                        <th>${utilityType.price} zł/${utilityType.unit}</th>
                        <th>
                            <button class="ui grey button utilities"
                                    data-id="${utilityType.id}"
                                    data-name="${utilityType.utilityName}">
                                <spring:message code="edit"/>
                            </button>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <div>
            <button class="ui button utilities" data-id="0"><spring:message code="utilities.addUtility"/></button>
        </div>
    </div>
</div>
