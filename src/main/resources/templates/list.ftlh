<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Список пользователей</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>E-mail</th>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <#if user.userId != 1>
                <tr>
                    <td>${user.email}</td>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.userId}">edit user</a></td>
                </tr>
            </#if>
        </#list>
        </tbody>
    </table>
</@c.page>