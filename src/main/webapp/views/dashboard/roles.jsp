<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="appUserRoles" scope="request" type="java.util.List"/>

<div id="roleModal" class="ui modal">
    <i class="close icon"></i>
    <div class="header" data-add="<spring:message code="dashboard.roles.addRole"/>" data-edit="<spring:message code="dashboard.roles.editRole"/>"></div>
    <div class="content"></div>
    <div class="actions">
        <button id="delete" class="ui left floated secondary button"><i class="ui inverted trash icon"></i><spring:message code="delete"/></button>
        <div class="ui cancel button"><spring:message code="cancel"/></div>
        <div class="ui approve primary button"><spring:message code="save"/></div>
    </div>
</div>
<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="dashboard.roles.title"/></h2>
        <table class="ui striped celled table">
            <thead>
            <tr>
                <th><spring:message code="name"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <%--@elvariable id="appUserRole" type="pl.local.neoteo.entity.AppUserRole"--%>
            <c:forEach items="${appUserRoles}" var="appUserRole">
                <tr>
                    <td>${appUserRole.role}</td>
                    <td>
                        <button class="ui grey button roles"
                                data-id="${appUserRole.id}"
                                data-name="${appUserRole.role}">
                            <spring:message code="edit"/>
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <button class="ui grey button roles" data-id="0"><spring:message code="dashboard.roles.addRole"/></button>
    </div>
</div>
