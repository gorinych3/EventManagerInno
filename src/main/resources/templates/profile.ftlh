<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Редактирование Вашего профиля</h2>
    <#if errorMessage??>
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </#if>
    <form action ="/user/profile" method = "post">

        <#include "parts/userDataFields.ftlh">

        <button class="btn btn-lg btn-primary btn-block" type="submit">Cохранить изменения</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>