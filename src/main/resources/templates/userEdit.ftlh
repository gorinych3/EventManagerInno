<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Редактирование пользователя ${user.username}</h2>

    <form action="/user" method="post">
        <div class="form-group">
            <label for="email">E-mail пользователя:</label>
            <input type="text" name="email" class="form-control" placeholder="" disabled value="${user.email}">
        </div>
        <div class="form-group">
            <label for="name">Имя пользователя:</label>
            <input type="text" name="username" class="form-control" placeholder="Введите новое имя пользователя" value="${user.username}">
        </div>
        <#list roles as role>
            <div class="form-check">
                <label class="form-check-label">
                    <input class="form-check-input" type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${user.userId}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-primary btn-block">Сохранить</button>
    </form>
</@c.page>