<#import "parts/common.ftlh" as c>
<@c.page>

    <div>
        <table style="margin-top: 10px" class="table">
            <tr>
                <th scope="col">
                    <div>
                        <form action="/event/create" method="post">
                            <p><b>Создание эвента</b></p>
                            <div class="form-group row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 col-form-label">Название эвента:</label>
                                    <input type="text" name="eventName" class="form-control" placeholder="Event name" />
                                    <label class="col-sm-2 col-form-label">Дата эвента:</label>
                                    <input type="datetime-local" name="createDate" class="form-control" placeholder="Event createDate"/>
                                    <label class="col-sm-2 col-form-label">Количество команд:</label>
                                    <input type="number" name="teams" class="form-control" placeholder="number of teams"/>
                                    <label class="col-sm-2 col-form-label">Количество участников в команде:</label>
                                    <input type="number" name="playersOnTeam" class="form-control" placeholder="number of players in the team"/>
                                    <input type="hidden" id="membersJSON" name = "membersJSON" value=""/>
                                </div>
                            </div>
                            <button class="btn btn-primary" type="submit">Создать эвент</button>
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        </form>
                        <div>
                            <p><b>Email участника:</b><br>
                                <input id="inp" type="text" size="40">
                            </p>
                            <p><button onclick="getInputValue()">Добавление участника</button></p>
                        </div>
                    </div>
                </th>
                <th scope="col">
                    <div>
                        <table style="margin-top: 10px" class="table">
                            <thead class="thead-dark">
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Email</th>
                                <th scope="col"/>
                            </tr>
                            </thead>
                            <tbody id="mainTable">
                            </tbody>
                        </table>
                    </div>

                </th>
            </tr>
        </table>
    </div>
    <script>
        let invitations = [];
        let idx = 0;

        function updateMembers(email) {
            invitations[idx++] = email;
        };

        function save(i, mail){
            invitations[i] = mail;
            renewData();
        };

        function update(btelem, i, textelem) {
            textelem.innerHTML = "";
            let mail = document.createElement("input");
            mail.setAttribute("type", "text");
            textelem.appendChild(mail);

            btelem.innerHTML = "";
            let btSave = document.createElement("button");
            btSave.setAttribute("type", "button");
            btSave.setAttribute("class", "btn btn-danger btn-sm");
            btSave.appendChild(document.createTextNode("Save"));
            btSave.addEventListener("click", function() {
                save(i, mail.value)
            });
            btelem.appendChild(btSave);
        };

        function renewData() {
            document.getElementById("mainTable").innerHTML = "";
            writeTable();
            let membersJSON = document.getElementById("membersJSON");
            membersJSON.value=JSON.stringify(invitations);
        };

        function deleteRow(i){
            idx--;
            invitations.splice(i,1);
            renewData();
        };

        function writeTable(){
            let len = invitations.length;
            let table = document.getElementById("mainTable");
            for (let i = 0; i < len; i++){
                let tr = document.createElement("tr");
                let tdId = document.createElement("td");
                tdId.setAttribute("scope","row");
                tdId.appendChild(document.createTextNode(i));
                tr.appendChild(tdId)
                let th = document.createElement("td");
                th.setAttribute("scope","row");
                th.appendChild(document.createTextNode(invitations[i]));
                tr.appendChild(th);
                let th1 = document.createElement("td");
                let bt = document.createElement("button");
                bt.setAttribute("type", "button");
                bt.setAttribute("class", "btn btn-danger btn-sm");
                bt.appendChild(document.createTextNode("Delete"));
                bt.addEventListener("click", function() {
                    deleteRow(i)
                });
                let btEdit = document.createElement("button");
                btEdit.setAttribute("type", "button");
                btEdit.setAttribute("class", "btn btn-danger btn-sm");
                btEdit.appendChild(document.createTextNode("Update"));
                btEdit.addEventListener("click", function() {
                    update(th1, i, th)
                });
                th1.appendChild(bt);
                th1.appendChild(btEdit);
                tr.appendChild(th1);
                table.appendChild(tr)
            }
        };

        function getInputValue() {
            var p = document.getElementById("inp");
            updateMembers(p.value);
            renewData();
        };
    </script>
</@c.page>