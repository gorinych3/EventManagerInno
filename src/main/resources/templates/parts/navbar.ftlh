<nav class="navbar navbar-expand-sm bg-light navbar-light fixed-top">
<#--    <a class="navbar-brand" href="#">Logo</a>-->
    <h5 class="my-0 mr-md-auto font-weight-normal">Event менеджер</h5>
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link <#if .main_template_name == "main.ftlh"> active</#if>" href="/">Главная страница</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <#if springMacroRequestContext.requestUri?contains("/user")> active</#if>" href="/user">Cписок</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <#if springMacroRequestContext.requestUri?contains("/registration")> active</#if>" href="/registration">Регистрация</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <#if springMacroRequestContext.requestUri?contains("/login")> active</#if>" href="/login">Войти</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <#if springMacroRequestContext.requestUri?contains("/user/profile")> active</#if>" href="/user/profile">Профиль</a>
        </li>
    </ul>
</nav>
