<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Общие сведения об ивенте</h2>
    <#if errorMessage??>
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </#if>
    <div class="row">
        <div class="col">Название</div>
        <div class="col">${event.eventName}</div>
    </div>
    <div class="row">
        <div class="col">Дата создания</div>
        <div class="col">${event.createDate}</div>
    </div>
    <div class="row">
        <div class="col">Дата проведения</div>
        <div class="col"><#if event.eventDate??>${event.eventDate}<#else>Не назначено</#if></div>
    </div>
    <div class="row">
        <div class="col">Дата проведения жеребьевки</div>
        <div class="col"><#if event.eventTossDate??>${event.eventTossDate}<#else>Не назначено</#if></div>
    </div>
    <br/>
    <h2>Добавить приглашенных</h2>
    <div class="row">
        <div class="col">
    <form action="send_invitation" method = "post">
        <div class="form-group">
            <label for="email">Email того, кого хочу пригласить</label>
            <input type="email" class="form-control" placeholder="Введите email" id="email" name="email">
        </div>
        <input type="hidden" name="eventId" value="${event.eventId}" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Пригласить</button>
    </form>
        </div>
    </div>
    <br/>
    <h2>Список приглашенных</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Дата приглашения</th>
            <th>Кого пригласили</th>
            <th>Принято</th>
            <th>Прочитано</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#if invitations?size gt 0>

            <#list invitations as invitation>
                <tr>
                    <td>${invitation.creationDate}</td>
                    <td><#if invitation.invitedUser??>
                            <#if invitation.emailInvitation??>${invitation.emailInvitation}<#else>${invitation.invitedUser.username}</#if>
                        <#elseif invitation.emailInvitation??>
                            ${invitation.emailInvitation}</#if></td>
                    <td>${invitation.accepted?then('Да', 'Нет')}</td>
                    <td>${invitation.informed?then('Да', 'Нет')}</td>
                    <td>
                        <form action="remove_invitation" method="post">
                            <input hidden name="invitationId" value="${invitation.invitationId}">
                            <input type="hidden" name="eventId" value="${event.eventId}" />
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button class="btn btn-primary" type="submit" >Отозвать приглашение</button>
                        </form>
                    </td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="5"><h5><span class="badge badge-secondary">Список приглашенных пуст.</span></h5></td>
            </tr>
        </#if>
        </tbody>
    </table>
    <br>
    <div>
        <#if user.username == event.ownerUser.username && !isResult && event.action??>
            <form action="start_event" method = "post">
                <input type="hidden" name="eventId" value="${event.eventId}" />
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit" class="btn btn-primary">Жеребьевка</button>
            </form>
        <#elseif isResult>
            <form action="resultAction" method = "post">
                <input type="hidden" name="eventId" value="${event.eventId}" />
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit" class="btn btn-primary">Результаты жеребьевки</button>
            </form>
        </#if>
    </div>
    <br/>
</@c.page>