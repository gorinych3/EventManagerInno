<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>

<h2>Войти</h2>
    <#if RequestParameters.error??>
        <div class="alert alert-danger">
            Вы ввели неправильный e-mail или пароль или такого пользователя не существует.
        </div>
    </#if>
<@l.login "/login"/>
${message?ifExists}
<a href="/registration">Регистрация</a>
</@c.page>
