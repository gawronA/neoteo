<%@ page import="java.util.stream.Collectors" %>
<%@ page import="pl.local.neoteo.entity.Property" %>
<%@ page import="pl.local.neoteo.entity.UtilityType" %>
<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="propertiesModal" class="ui modal">
    <i class="close icon"></i>
    <div class="header" data-add="<spring:message code="property.addProperty"/>" data-edit="<spring:message code="property.editProperty"/>"></div>
    <div class="content"></div>
    <div class="actions">
        <div class="ui cancel button"><spring:message code="cancel"/></div>
        <div class="ui approve primary button"><spring:message code="save"/></div>
    </div>
</div>

<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="property.title"/></h2>
            <table class="ui striped celled table">
                <thead>
                    <tr>
                        <th><spring:message code="name"/></th>
                        <th><spring:message code="user"/></th>
                        <th>Media</th>
                        <th><spring:message code="property.active"/></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${properties}" var="property">
                    <tr>
                        <th>${property.name}</th>
                        <th>${property.user.email}</th>
                        <th>
                            <c:forEach items="${property.utilityTypes}" var="utilityType">
                                ${utilityType.utilityName},
                            </c:forEach>
                        </th>
                        <th>${property.active}</th>
                        <th>
                            <button class="ui grey button properties"
                                    data-id="${property.id}"
                                    data-name="${property.name}">
                                <spring:message code="edit"/>
                            </button>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
    </div>
</div>