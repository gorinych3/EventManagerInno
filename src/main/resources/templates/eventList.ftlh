<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Список всех ивентов</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>CreateDate</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list events as event>
            <tr>
                <td><a href="/event/${event.eventId}">${event.eventName}</a></td>
                <td>${event.createDate}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>