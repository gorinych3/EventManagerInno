<#import "parts/common.ftlh" as c>
<@c.page>
    <h3>${username}</h3>
    <form class="form-signin" method = "post">
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="email" name="email" id="inputEmail" class="form-control" value="${email!''}" required autofocus>
        <label for="inputPhone" class="sr-only">Phone</label>
        <input type="text" name="phoneNumber" id="inputPhone" class="form-control" placeholder="Phone" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Cохранить изменения</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>