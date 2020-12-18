<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>

<h2>Войти</h2>
<@l.login "/login"/>
${message?ifExists}
<a href="/registration">Регистрация</a>
</@c.page>