<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Список ивентов ${username}</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название ивента</th>
            <th>Дата создания</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#if events?size gt 0>
        <#list events as event>
            <tr>
                <td><a href="/event/${event.eventId}">${event.eventName}</a></td>
                <td>${event.createDate.toLocalDate()}</td>
                <td>
                    <form action="remove_event" method = "post">
                        <input type="hidden" name="eventId" value="${event.eventId}" />
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" class="btn btn-primary">Удалить ивент</button>
                    </form>
                </td>
            </tr>
        </#list>
        <#else>
        <tr>
            <td colspan="3"><h5><span class="badge badge-secondary">Вы пока не создали ни одного ивента. <a href="/event/create">Создать Ивент.</a> </span></h5></td>
        </tr>
        </#if>
        </tbody>
    </table>
</@c.page>