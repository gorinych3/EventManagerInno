<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Меня пригласили</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название ивента</th>
            <th>Дата приглашения</th>
            <th>Кто пригласил</th>
            <th>Принято</th>
            <th>Прочитано</th>
        </tr>
        </thead>
        <tbody>
        <#if invitationsInvited?size gt 0>

        <#list invitationsInvited as invitation>
            <tr>
                <td><a href="/event/${invitation.event.eventId}">${invitation.event.eventName}</a></td>
                <td>${invitation.creationDate}</td>
                <td>${invitation.invitorUser.username}</td>
                <td>${invitation.accepted?then('Да', 'Нет')}</td>
                <td>${invitation.informed?then('Да', 'Нет')}</td>
            </tr>
        </#list>

        <#else>
            <tr>
                <td colspan="5"><h5><span class="badge badge-secondary">К сожалению, Вас пока никуда не пригласили.</span></h5></td>
            </tr>
        </#if>
        </tbody>
    </table>
    <h2>Я пригласил</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название ивента</th>
            <th>Дата приглашения</th>
            <th>Кого пригласил</th>
            <th>Принято</th>
            <th>Прочитано</th>
        </tr>
        </thead>
        <tbody>
        <#if invitationsInvitor?size gt 0>

        <#list invitationsInvitor as invitation>
            <tr>
                <td><a href="/event/${invitation.event.eventId}">${invitation.event.eventName}</a></td>
                <td>${invitation.creationDate}</td>
                <td><#if invitation.invitedUser??>${invitation.invitedUser.username}</#if></td>
                <td>${invitation.accepted?then('Да', 'Нет')}</td>
                <td>${invitation.informed?then('Да', 'Нет')}</td>
            </tr>
        </#list>
        <#else>
            <tr>
                <td colspan="5"><h5><span class="badge badge-secondary">Вы пока никого никуда не приглашали.</span></h5></td>
            </tr>
        </#if>
        </tbody>
    </table>
</@c.page>