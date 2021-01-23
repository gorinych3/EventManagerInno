<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Общие сведения об ивенте</h2>
    <#if errorMessage??>
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </#if>
    <form action="create" class="form-horizontal" method = "post">
        <div class="form-group">
            <label for="name" class="control-label">Название</label>
            <input type="text" class="form-control-plaintext" placeholder="Введите Название" id="name" name="eventName" value="${event.eventName}" readonly>
        </div>
        <div class="form-group">
            <label for="create_date" class="control-label">Дата создания</label>
            <input type="datetime-local" class="form-control-plaintext" placeholder="Введите Дату создания" id="create_date" name="create_date" value="${event.createDate}" readonly>
        </div>
        <div class="form-group">
            <label for="event_date" class="control-label">Дата проведения</label>
            <input type="datetime-local" class="form-control-plaintext" placeholder="Введите Дату проведения" id="event_date" name="eventDate" value="<#if event.eventDate??>${event.eventDate}</#if>" readonly>
        </div>
        <div class="form-group">
            <label for="event_toss_date" class="control-label">Дата проведения жеребьевки</label>
            <input type="date" class="form-control-plaintext" placeholder="Введите Дату жеребьевки" id="event_toss_date" name="eventTossDate" value="<#if event.eventTossDate??>${event.eventTossDate}</#if>" readonly>
        </div>

        <input type="hidden" name="eventId" value="${event.eventId}" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <button type="submit" class="btn btn-primary" style="visibility: hidden" id="saveButton">Сохранить</button>

    </form>

    <#if user.userId == event.ownerUser.userId >
    <script>
        function makeItEditable() {
            const fields = ['name', 'event_date', 'event_toss_date'];

            fields.forEach(function(field) {
                document.getElementById(field).readOnly = false;
                document.getElementById(field).className = "form-control";
            })

            document.getElementById('saveButton').style.visibility = "visible";
            document.getElementById('cancelButton').style.visibility = "visible";
            document.getElementById('editButton').style.visibility = "hidden";

        }

        function makeItReadOnly() {
            const fields = ['name', 'event_date', 'event_toss_date'];

            fields.forEach(function(field) {
                document.getElementById(field).readOnly = true;
                document.getElementById(field).className = "form-control-plaintext";
            })

            document.getElementById('saveButton').style.visibility = "hidden";
            document.getElementById('cancelButton').style.visibility = "hidden";
            document.getElementById('editButton').style.visibility = "visible";

        }
    </script>
    <br/>
    <div class="row">
        <div class="col">
            <form action="remove_event" method = "post">
                <input type="hidden" name="eventId" value="${event.eventId}" />
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit" class="btn btn-primary">Удалить ивент</button>
            </form>
        </div>
        <div class="col">
            <button type="button" class="btn btn-primary" onclick="makeItEditable()" id="editButton" >Редактировать</button>
            <button type="button" class="btn btn-primary"  style="visibility: hidden" onclick="makeItReadOnly()" id="cancelButton">Отменить редактирование</button>
        </div>
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
    </#if>
    <h2>Список приглашенных</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Дата приглашения</th>
            <th>Кого пригласили</th>
            <#if user.userId == event.ownerUser.userId >
            <th>Принято</th>
            <th>Прочитано</th>
            <th>Действия</th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#if invitations?? && invitations?size gt 0>

            <#list invitations as invitation>
                <tr>
                    <td>${invitation.creationDate.toLocalDate()}</td>
                    <td><#if invitation.invitedUser??>
                            <#if invitation.emailInvitation??>${invitation.emailInvitation}<#else>${invitation.invitedUser.email} (${invitation.invitedUser.username})</#if>
                        <#elseif invitation.emailInvitation??>
                            ${invitation.emailInvitation}</#if></td>
                    <#if user.userId == event.ownerUser.userId >
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
                    </#if>
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
        <#if event.action?? && usersCount gte 2 && user.userId == event.ownerUser.userId && !isResult>
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