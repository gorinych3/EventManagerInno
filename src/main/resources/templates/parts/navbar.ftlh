<#import "login.ftlh" as l>
<nav class="navbar navbar-expand-sm bg-light navbar-light fixed-top">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link <#if .main_template_name == "main.ftlh"> active</#if>" href="/">Главная страница</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <#if .main_template_name == "list.ftlh" || .main_template_name == "userEdit.ftlh"> active</#if>" href="/user">Cписок</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <#if .main_template_name == "registration.ftlh"> active</#if>" href="/registration">Регистрация</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <#if .main_template_name == "login.ftlh"> active</#if>" href="/login">Войти</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <#if .main_template_name == "profile.ftlh"> active</#if>" href="/user/profile">Профиль</a>
            </li>
        </ul>

        <@l.logout/>
    </div>
</nav>
