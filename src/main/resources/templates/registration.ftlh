<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Регистрация</h2>
<form action ="/registration" class="form-signin" method = "post">
    <label for="inputUsername" class="sr-only">User</label>
    <input type="text" name="username" id="inputUsername" class="form-control" placeholder="name" required autofocus>
    <label for="inputEmail" class="sr-only">Email</label>
    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email" required autofocus>
    <label for="inputPhone" class="sr-only">Phone</label>
    <input type="text" name="phoneNumber" id="inputPhone" class="form-control" placeholder="Phone" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Зарегистрироваться</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
    ${message?ifExists}
</@c.page>