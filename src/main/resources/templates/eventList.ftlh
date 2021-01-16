<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Список всех ивентов, в которых я принимаю участие</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название ивента</th>
            <th>Дата создания ивента</th>
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
            <td colspan="3"><h5><span class="badge badge-secondary">Список пуст.</span></h5></td>
        </tr>
        </#if>
        </tbody>
    </table>
</@c.page>