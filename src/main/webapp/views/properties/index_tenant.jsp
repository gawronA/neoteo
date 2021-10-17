<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="propertiesModal" class="ui modal">
    <i class="close icon"></i>
    <div class="header" data-add="<spring:message code="property.addProperty"/>" data-edit="<spring:message code="property.editProperty"/>"></div>
    <div class="content"></div>
    <div class="actions">
        <button id="delete" class="ui left floated secondary button"><i class="ui inverted trash icon"></i><spring:message code="delete"/></button>
        <div class="ui cancel button"><spring:message code="cancel"/></div>
        <div class="ui approve primary button"><spring:message code="save"/></div>
    </div>
</div>

<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="property.title"/></h2>
        <c:if test="${empty property}">
            <p><spring:message code="property.noPropertyText"/></p>
            <div>
                <button class="ui button properties" data-id="0"><spring:message code="property.addProperty"/></button>
            </div>
        </c:if>
        <c:if test="${not empty property}">
            <h4 class="header">${property.name}</h4>
            <c:if test="${not property.active}">
                <p><spring:message code="property.awaitingAccept"/></p>
            </c:if>
            <c:if test="${property.active}">
                <table class="ui striped celled table">
                    <tbody>
                    <c:forEach items="${property.utilities}" var="utility">
                        <tr>
                            <th>${utility.type.utilityName}</th>
                            <th>${utility.amount} ${utility.type.unit}</th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </c:if>
    </div>
</div>
