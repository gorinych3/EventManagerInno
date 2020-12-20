<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Список пользователей ивента</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>email</th>
            <th>phoneNumber</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.phoneNumber}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>