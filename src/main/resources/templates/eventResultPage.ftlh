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
        <div class="col">Action</div>
        <div class="col">${action.actionName}</div>
    </div>
    <br/>
    <h2>Список всех игр</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Team 1</th>
            <th>Team 2</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#if playActions?size gt 0>
            <#list playActions as playAction>
                <tr>
                    <td>${playAction.master.teamName}</td>
                    <td>${playAction.slave.teamName}</td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="3"><h5><span class="badge badge-secondary">Список пуст.</span></h5></td>
            </tr>
        </#if>
        </tbody>
    </table>
    <div>
        <#if user.username == event.ownerUser.username>
            <form action="start_event" method = "post">
                <input type="hidden" name="eventId" value="${event.eventId}" />
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit" class="btn btn-primary">Жеребьевка</button>
            </form>
        </#if>
    </div>
</@c.page>