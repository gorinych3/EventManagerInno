<#import "parts/common.ftlh" as c>
<@c.page>
<form action ="/login" class="form-signin" method = "post">
    <h1 class="h2 mb-2 font-weight-normal">Войти</h1>
    <label for="inputUserName" class="sr-only">User</label>
    <input type="text" name="username" id="inputUserName" class="form-control" placeholder="UserName" required autofocus>
    <label for="inputPassword" class="sr-only">Говори пароль!</label>
    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
    ${message?ifExists}
<a href="/registration">Регистрация</a>
</@c.page>