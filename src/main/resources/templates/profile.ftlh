<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Редактирование профиля для пользователя ${username}</h2>
    <form method = "post">
        <div class="form-group">
            <label for="inputEmail">Email</label>
            <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email" value="${email!''}" required autofocus>
        </div>
        <div class="form-group">
            <label for="inputPhone">Телефон</label>
            <input type="text" name="phoneNumber" id="inputPhone" class="form-control" placeholder="Телефон" value="${phone_number!''}" required autofocus>
        </div>
        <div class="form-group">
            <label for="inputPassword">Пароль</label>
            <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Пароль" required>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Cохранить изменения</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>