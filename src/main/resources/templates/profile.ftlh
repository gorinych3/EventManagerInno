<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Редактирование Вашего профиля</h2>
    <#if errorMessage??>
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </#if>
    <form action ="/user/profile" method = "post">

        <#include "userDataFieldsProfile.ftlh">

        <h6>Для применения изменений необходимо будет заново авторизоваться</h6>
        <h6></h6>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Cохранить изменения</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>