<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="ui vertical segment">
    <div class="ui container">
        <h2 class="header"><spring:message code="property.title"/></h2>
        <h4 class="header">${property.name}</h4>
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
    </div>
</div>
