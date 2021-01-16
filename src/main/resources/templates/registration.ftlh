<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Регистрация</h2>
    <#if errorMessage??>
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </#if>
    <#if infoMessage??>
        <div class="alert alert-info">
            ${infoMessage}
        </div>
    </#if>
<form action ="/registration" method = "post">
    <div class="form-group">
        <label for="inputUsername">Имя*</label>
        <input type="text" name="username" id="inputUsername"
               class="form-control ${(usernameError??)?string('is-invalid', '')}"
               placeholder="Введите ваше имя" required autofocus
               value="<#if user??>${user.username}</#if>"
        >
        <#if usernameError??>
            <div class="invalid-feedback">
                ${usernameError}
            </div>
        </#if>
    </div>
<#--    <div class="form-group">-->
<#--        <label for="inputEmail">E-mail*</label>-->
<#--        <input type="text" name="email" id="inputEmail"-->
<#--               class="form-control ${(emailError??)?string('is-invalid', '')}"-->
<#--               placeholder="Введите ваш e-mail" required autofocus-->
<#--               value="<#if user??>${user.email}</#if>"-->
<#--        >-->
<#--        <#if emailError??>-->
<#--            <div class="invalid-feedback">-->
<#--                ${emailError}-->
<#--            </div>-->
<#--        </#if>-->
<#--    </div>-->

    <#include "parts/userDataFields.ftlh">

        <button class="btn btn-lg btn-primary btn-block" type="submit">Зарегистрироваться</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
    ${message?ifExists}
</@c.page>