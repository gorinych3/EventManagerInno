<#macro login path>
    <form action="/login" method = "post">
        <div class="form-group">
            <label for="inputUserName">Имя пользователя</label>
            <input type="text" name="username" id="inputUserName" class="form-control" placeholder="Введите имя пользователя" required autofocus>
        </div>
        <div class="form-group">
            <label for="inputPassword">Пароль</label>
            <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Введите пароль" required>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit">Выйти</button>
    </form>
</#macro>

