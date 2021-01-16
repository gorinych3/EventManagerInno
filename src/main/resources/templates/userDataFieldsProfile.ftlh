    <div class="form-group">
        <label for="inputEmail">E-mail</label>
        <input type="email" name="email" id="inputEmail"
               class="form-control"
               placeholder="E-mail" required autofocus
               value="<#if user??>${user.email}</#if><#if userToSave??>${userToSave.email}</#if><#if emailValue??>${emailValue}</#if>"
        >
        <#if emailError??>
            <div class="invalid-feedback">
                ${emailError}
            </div>
        </#if>
    </div>
    <div class="form-group">
        <label for="inputUsername">Имя</label>
        <input type="username" name="username" id="inputUsername"
               class="form-control  ${(usernameError??)?string('is-invalid', '')}"
               placeholder="Введите ваше имя" required autofocus
               value="<#if user??>${user.username}</#if><#if userToSave??>${userToSave.username}</#if><#if usernameValue??>${usernameValue}</#if>"
        >
        <#if usernameError??>
            <div class="invalid-feedback">
                ${usernameError}
            </div>
        </#if>
    </div>
    <div class="form-group">
        <label for="inputPhone">Телефон</label>
        <input type="text" name="phoneNumber" id="inputPhone" class="form-control  ${(phoneNumberError??)?string('is-invalid', '')}"
               placeholder="Введите ваш телефон" required autofocus
               value="<#if user??>${user.phoneNumber}</#if><#if userToSave??>${userToSave.phoneNumber}</#if>"
        >
        <#if phoneNumberError??>
            <div class="invalid-feedback">
                ${phoneNumberError}
            </div>
        </#if>
    </div>
    <div class="form-group">
        <label for="inputPassword">Пароль</label>
        <input type="password" name="password" id="inputPassword" class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Введите ваш пароль" required>
        <#if passwordError??>
            <div class="invalid-feedback">
                ${passwordError}
            </div>
        </#if>
    </div>
    <div class="form-group">
        <label for="inputPassword">Подтверждение Пароля*</label>
        <input type="password" name="password2" id="inputPassword2" class="form-control ${(password2Error??)?string('is-invalid', '')}" placeholder="Подтвердите пароль" required>
        <#if password2Error??>
            <div class="invalid-feedback">
                ${password2Error}
            </div>
        </#if>
    </div>
