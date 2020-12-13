<#import "parts/common.ftlh" as c>
<@c.page>
    <h3>List of users</h3>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.userId}">edit user</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>