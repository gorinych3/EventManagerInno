<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Регистрация</h2>
<form action ="/registration" method = "post">
    <div class="form-group">
        <label for="inputUsername">Имя</label>
        <input type="text" name="username" id="inputUsername" class="form-control" placeholder="Введите ваше имя" required autofocus>
    </div>
    <div class="form-group">
        <label for="inputEmail">Email</label>
        <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Введите ваш Email" required autofocus>
    </div>
    <div class="form-group">
        <label for="inputPhone">Телефон</label>
        <input type="text" name="phoneNumber" id="inputPhone" class="form-control" placeholder="Введите ваш телефон" required autofocus>
    </div>
    <div class="form-group">
        <label for="inputPassword">Пароль</label>
        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Введите ваш пароль" required>
    </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Зарегистрироваться</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
    ${message?ifExists}
</@c.page>