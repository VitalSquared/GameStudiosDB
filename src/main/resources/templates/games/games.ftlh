<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Games">
    <#if s.isAdmin || s.isGeneralDirector>
        <a class="nav-link btn btn-primary" href="/games/new">Create new game</a>
    </#if>

    <#assign studios_map = {}>
    <#foreach studio in all_studios>
        <#assign studios_map = studios_map + { studio.studioID?string : studio.name }>
    </#foreach>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Start Date</th>
            <th scope="col">Dev Name</th>
            <th scope="col">Release Name</th>
            <th scope="col">Expenses</th>
            <th scope="col">Studio</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#foreach game in games>
            <tr>
                <td>${game.gameID}</td>
                <td>${game.startDate}</td>
                <td>${game.devName}</td>
                <td>${game.releaseName}</td>
                <td>${game.expenses}</td>
                <td>${studios_map[game.studioID?string]} (${game.studioID})</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/games/${game.gameID}">View</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>