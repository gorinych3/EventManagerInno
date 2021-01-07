<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Ивент</h2>
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
        <div class="col">Дата</div>
        <div class="col">${event.createDate}</div>
    </div>
    <form action="send_invitation" method = "post">
        <div class="form-group">
            <label for="email">Email того, кого хочу пригласить</label>
            <input type="email" class="form-control" placeholder="Введите email" id="email" name="email">
        </div>
        <input type="hidden" name="eventId" value="${event.eventId}" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Пригласить</button>
    </form>
</@c.page>