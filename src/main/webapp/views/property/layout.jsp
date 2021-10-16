<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<tiles:insertAttribute name="navbar"/>
<div class="main-banner"></div>

<div class="full-height flex-row">
    <div class="ui vertical inverted sticky menu">
        <tiles:insertAttribute name="sidenav"/>
    </div>
    <div class="board">
        <tiles:insertAttribute name="body"/>
    </div>
</div>

