<#import "parts/common.ftlh" as c>
<@c.page>
<form action ="/login" class="form-signin" method = "post">
    <h1 class="h2 mb-2 font-weight-normal">Войти</h1>
    <label for="inputEmail" class="sr-only">User</label>
    <input type="text" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
    <label for="inputPassword" class="sr-only">Говори пароль!</label>
    <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
<form action="/logout" method="post">
    <button class="btn btn-primary" type="submit">Sing Out</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
</@c.page>