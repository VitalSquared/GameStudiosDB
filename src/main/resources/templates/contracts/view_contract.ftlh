<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Contract #" + contract.contractID>
    <#assign studios_map = {}>
    <#foreach studio in all_studios>
        <#assign studios_map = studios_map + { studio.studioID?string : studio.name }>
    </#foreach>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Date</th>
            <th scope="col">Percent</th>
            <th scope="col">Test ID</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${contract.contractID}</td>
            <td>${contract.date}</td>
            <td>${contract.percent}</td>
            <td>${contract.testID}</td>
            <td>
                <#if s.isAdmin || s.isGeneralDirector>
                    <a class="nav-link btn btn-primary" href="/contracts/${contract.contractID}/edit">Edit</a>
                </#if>
            </td>
            <td>
                <#if s.isAdmin>
                    <a class="nav-link btn btn-primary" href="/contracts/${contract.contractID}/delete">Delete</a>
                </#if>
            </td>
        </tr>
        </tbody>
    </table>
    <br/>
    <h1>Games:</h1>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Release Name</th>
            <th scope="col">Studio</th>
            <th scope="col">
                <#if s.isAdmin || s.isGeneralDirector>
                    <a class="nav-link btn btn-primary" href="/contracts/${contract.contractID}/add_game">Add Game</a>
                </#if>
            </th>
        </tr>
        </thead>
        <tbody>
        <#foreach game in games>
            <tr>
                <td>${game.gameID}</td>
                <td>${game.releaseName}</td>
                <td>${studios_map[game.studioID?string]} (${game.studioID})</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/contracts/${contract.contractID}/remove_game/${game.gameID}">Remove from contract</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>