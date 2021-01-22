<#include "security.ftlh">
<#import "login.ftlh" as l>
<nav class="navbar navbar-expand-sm bg-light navbar-light fixed-top">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link <#if .main_template_name == "main.ftlh"> active</#if>" href="/">Главная страница</a>
            </li>
            <#if known>
            <li class="nav-item">
                <a class="nav-link <#if .main_template_name == "profile.ftlh"> active</#if>" href="/user/profile">Мой профиль</a>
            </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link <#if .main_template_name == "list.ftlh" || .main_template_name == "userEdit.ftlh"> active</#if>" href="/user">Администрирование пользователей</a>
                </li>
            </#if>
            <#if known>
                <li class="nav-item">
                    <a class="nav-link <#if .main_template_name == "eventRegistration.ftlh"> active</#if>" href="/event/create">Создание ивента</a>
                </li>
            </#if>
            <#if known>
                <li class="nav-item">
                    <a class="nav-link <#if .main_template_name == "currentUserEvents.ftlh"> active</#if>" href="/event/user">Созданные мной ивенты</a>
                </li>
            </#if>
            <#if known>
                <li class="nav-item">
                    <a class="nav-link <#if .main_template_name == "currentUserInvitations.ftlh"> active</#if>" href="/invitations/user">Мои приглашения</a>
                </li>
            </#if>
            <#if !known>
                <li class="nav-item">
                    <a class="nav-link <#if .main_template_name == "registration.ftlh"> active</#if>" href="/registration">Регистрация</a>
                </li>
            </#if>
        </ul>

        <#if known>
            Привет, ${user.username}.<#if invitationService.getAmountOfNotReadInvitationsForCurrentUser() gt 0>&nbsp;<a href="/invitations/user">Новых приглашений: ${invitationService.getAmountOfNotReadInvitationsForCurrentUser()}</a></#if>&nbsp;
            <@l.logout/>
        <#else>
            <a class="btn btn-primary" href="/login">Войти</a>
        </#if>
    </div>
</nav>
