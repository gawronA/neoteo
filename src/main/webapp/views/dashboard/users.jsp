<jsp:useBean id="appUsers" scope="request" type="java.util.List"/>
<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--<tiles:insertAttribute name="modal"/>--%>
<div id="userModal" class="ui modal">
    <i class="close icon"></i>
    <div class="header" data-add="<spring:message code="dashboard.users.addUser"/>" data-edit="<spring:message code="dashboard.users.editUser"/>"></div>
    <div class="content"></div>
    <div class="actions">
        <button id="delete" class="ui left floated secondary button"><i class="ui inverted trash icon"></i><spring:message code="delete"/></button>
        <div class="ui cancel button"><spring:message code="cancel"/></div>
        <div class="ui approve primary button"><spring:message code="save"/></div>
    </div>
</div>
<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="dashboard.users.title"/></h2>
        <table class="ui striped celled table">
            <thead>
            <tr>
                <th><spring:message code="dashboard.users.firstAndLastName"/></th>
                <th><spring:message code="user.email"/></th>
                <th><spring:message code="user.telephoneNumber"/></th>
                <th><spring:message code="user.subscription"/></th>
                <th><spring:message code="user.cardNumber"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${appUsers}" var="appUser">
            <tr>
                <td>${appUser.firstName} ${appUser.lastName}</td>
                <td>${appUser.email}</td>
                <td>${appUser.telephoneNumber}</td>
                <td>${appUser.subscription.name}</td>
                <td>${appUser.card.number}</td>
                <td>
                    <button class="ui grey button users"
                            data-id="${appUser.id}"
                            data-name="${appUser.firstName} ${appUser.lastName}">
                        <spring:message code="edit"/>
                    </button>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <button class="ui grey button users" data-id="0"><spring:message code="dashboard.users.addUser"/></button>
    </div>
</div>
