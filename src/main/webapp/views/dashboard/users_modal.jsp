<%@ page contentType="text/html;charset=UTF8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:useBean id="subscriptionList" scope="request" type="java.util.List"/>
<jsp:useBean id="appUserRoleList" scope="request" type="java.util.List"/>

<div id="userModal" class="ui modal">
    <i class="close icon"></i>
    <div id="userModalHeader" class="header" data-addlabel="Dodaj nowego użytkownika" data-editlabel="Edytuj użytkownika"></div>
    <div class="content">
        <form id="userModalForm" class="ui form" action="users" method="post">
            <input id="userInputId" type="hidden" name="id" value="0"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="field">
                <label>Dane kontaktowe</label>
                <div class="two fields">
                    <div class="field">
                        <input id="userModalInputFirstName" type="text" name="firstName" placeholder="Imię"/>
                    </div>
                    <div class="field">
                        <input id="userModalInputLastName" type="text" name="lastName" placeholder="Nazwisko"/>
                    </div>
                </div>
            </div>
            <div class="field">
                <div class="two fields">
                    <div class="field">
                        <input id="userModalInputEmail" type="email" name="email" placeholder="E-mail"/>
                    </div>
                    <div class="field">
                        <input id="userModalInputTelephoneNumber" type="tel" name="telephoneNumber" placeholder="Telefon"/>
                    </div>
                </div>
            </div>
            <div class="field">
                <label>Konto</label>
                <div class="two fields">
                    <div class="field">
                        <label>Subskrybcja</label>
                        <select name="subscription">
                            <c:forEach items="${subscriptionList}" var="subscription">
                                <option value="${subscription.id}">${subscription.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="field">
                        <label>Nr karty</label>
                        <div class="ui disabled input">
                            <input id="userModalInputCard" class="ui disabled input" type="text" path="card"/>
                        </div>
                    </div>
                </div>

            </div>
            <div class="field">
                <label>Uprawnienia</label>
                <div class="two fields">
                    <div class="field">
                        <label>Role użytkownika</label>
                        <select name="appUserRoles" multiple="true">
                            <c:forEach items="${appUserRoleList}" var="appUserRole">
                                <option value="${appUserRole.id}">${appUserRole.role}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="field">
                        <div class="ui toggle checkbox">
                            <input id="userModalInputActive" type="checkbox" name="active"/>
                            <label>Konto aktywne</label>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="actions">
        <div class="ui cancel button">Cancel</div>
        <div class="ui approve primary button">OK</div>
    </div>
</div>
