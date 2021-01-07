<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Список ивентов ${username}</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название ивента</th>
            <th>Дата создания</th>

        </tr>
        </thead>
        <tbody>
        <#if events?size gt 0>
        <#list events as event>
            <tr>
                <td><a href="/event/${event.eventId}">${event.eventName}</a></td>
                <td>${event.createDate}</td>

            </tr>
        </#list>
        <#else>
        <tr>
            <td colspan="2"><h5><span class="badge badge-secondary">Вы пока не создали ни одного ивента. <a href="/event/create">Создать Ивент.</a> </span></h5></td>
        </tr>
        </#if>
        </tbody>
    </table>
</@c.page>