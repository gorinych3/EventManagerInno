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
        <div class="col">Дата</div>
        <div class="col">${event.createDate}</div>
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
</@c.page>