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
                    <a class="nav-link <#if .main_template_name == "eventList.ftlh"> active</#if>" href="/event">Все ивенты</a>
                </li>
            </#if>
            <#if known>
                <li class="nav-item">
                    <a class="nav-link <#if .main_template_name == "currentUserEvents.ftlh"> active</#if>" href="/event/user">Мои ивенты</a>
                </li>
            </#if>
            <#if !known>
                <li class="nav-item">
                    <a class="nav-link <#if .main_template_name == "registration.ftlh"> active</#if>" href="/registration">Регистрация</a>
                </li>
            </#if>
        </ul>

        <#if known>
            <@l.logout/>
        <#else>
            <a class="btn btn-primary" href="/login">Войти</a>
        </#if>
    </div>
</nav>
