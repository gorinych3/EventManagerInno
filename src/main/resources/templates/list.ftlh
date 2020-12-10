<#import "parts/common.ftlh" as c>
<@c.page>
    <h3>List of users</h3>
    <table>
        <thead>
        <tr>
            <th>Name</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>