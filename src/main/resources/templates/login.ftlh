<#import "parts/common.ftlh" as c>
<@c.page>
<form class="form-signin">
    <h1 class="h2 mb-2 font-weight-normal">Войти</h1>
    <label for="inputEmail" class="sr-only">Email address</label>
    <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
    <label for="inputPassword" class="sr-only">Говори пароль!</label>
    <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
</form>
</@c.page>