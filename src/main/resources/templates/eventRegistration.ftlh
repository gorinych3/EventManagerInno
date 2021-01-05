<#import "parts/common.ftlh" as c>
<@c.page>

    <div>
        <form action="/event/create" method="post">
            <p><b>Создание эвента</b></p>
            <div class="form-group row">
                <div class="col-sm-6">
                    <label class="col-sm-2 col-form-label">Название эвента:</label>
                    <input type="text" name="eventName" class="form-control" placeholder="Event name" />
                    <label class="col-sm-2 col-form-label">Дата эвента:</label>
                    <input type="datetime-local" name="createDate" class="form-control" placeholder="Event createDate"/>
                    <input type="hidden" id="membersJSON" name = "membersJSON" value=""/>
                </div>
            </div>
            <button class="btn btn-primary" type="submit">Создать эвент</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form>
    </div>
    <div>
        <p><b>Имя участника:</b><br>
            <input id="inp" type="text" size="40">
        </p>
        <p><b>Email участника:</b><br>
            <input id="eml" type="text" size="40">
        </p>
        <p><button onclick="getInputValue()">Добавление участника</button></p>
    </div>
    <div>
        <table style="margin-top: 10px" class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Email</th>
            </tr>
            </thead>
            <tbody id="mainTable">
            </tbody>
        </table>
    </div>
    <script>
        var members = [];
        var idx = 0;

        function updateMembers(name, email) {
            members[idx++] = new Member(name, email);
        };

        function Member(_memberName, _email) {
            this.memberName = _memberName;
            this.email = _email;
        };

        function getInputValue() {
            var p = document.getElementById("inp");
            var em = document.getElementById("eml");
            var table = document.getElementById("mainTable");
            var tr = document.createElement("tr");
            var th = document.createElement("th");
            var th2 = document.createElement("th");
            th.setAttribute("scope","row");
            th2.setAttribute("scope","row");
            th.appendChild(document.createTextNode(p.value));
            th2.appendChild(document.createTextNode(em.value));
            tr.appendChild(th);
            tr.appendChild(th2);
            table.appendChild(tr);

            var membersJSON = document.getElementById("membersJSON");
            updateMembers(p.value, em.value);
            membersJSON.value=JSON.stringify(members);
        };
    </script>
</@c.page>