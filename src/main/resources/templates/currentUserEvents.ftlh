<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Список ивентов ${username}</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название ивента</th>
            <th>Дата создания</th>
            <th>Участники</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list events as event>
            <tr>
                <td>${event.eventName}</td>
                <td>${event.createDate}</td>
                <td><a href="/user/event/${event.eventId}">просмотр участников ивента</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>