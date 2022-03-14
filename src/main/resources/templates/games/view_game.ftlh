<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page game.releaseName>
    <#assign studios_map = {}>
    <#foreach studio in all_studios>
        <#assign studios_map = studios_map + { studio.studioID?string : studio.name }>
    </#foreach>

    <#assign platforms_map = {}>
    <#foreach platform in all_platforms>
        <#assign platforms_map = platforms_map + { platform.platformID?string : platform.name }>
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
            <tr>
                <td>${game.gameID}</td>
                <td>${game.startDate}</td>
                <td>${game.devName}</td>
                <td>${game.releaseName}</td>
                <td>${game.expenses}</td>
                <td>${studios_map[game.studioID?string]} (${game.studioID})</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/games/${game.gameID}/edit">Edit</a>
                    </#if>
                </td>
            </tr>
        </tbody>
    </table>
    <br/>
    Genres:
    <#if s.isAdmin || s.isGeneralDirector>
        <a class="nav-link btn btn-primary" href="/games/${game.gameID}/add_genre">Add genre</a>
    </#if>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Name</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#foreach genre in genres>
            <tr>
                <td>${genre.name}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/games/${game.gameID}/remove_genre/${genre.genreID}">Remove from game</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
    <br/>
    Employees:
    <#if s.isAdmin || s.isGeneralDirector>
        <a class="nav-link btn btn-primary" href="/games/${game.gameID}/add_employee">Add employee</a>
    </#if>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#foreach employee in employees>
            <tr>
                <td>${employee.employeeID}</td>
                <td>${employee.firstName}</td>
                <td>${employee.lastName}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/games/${game.gameID}/remove_employee/${employee.employeeID}">Remove from game</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
    <br/>
    Contracts:
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Date</th>
            <th scope="col">Percent</th>
            <th scope="col">Test ID</th>
        </tr>
        </thead>
        <tbody>
        <#foreach contract in contracts>
            <tr>
                <td>${contract.contractID}</td>
                <td>${contract.date}</td>
                <td>${contract.percent}</td>
                <td>${contract.testID}</td>
            </tr>
        </#foreach>
        </tbody>
    </table>
    <br/>
    Releases:
    <#if s.isAdmin || s.isGeneralDirector>
        <a class="nav-link btn btn-primary" href="/games/${game.gameID}/new_release">Create release</a>
    </#if>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Contract ID</th>
            <th scope="col">Platform</th>
            <th scope="col">Release Date</th>
            <th scope="col">Percent</th>
            <th scope="col">Sold Count</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#foreach release in releases>
            <tr>
                <td>${release.contractID}</td>
                <td>${platforms_map[release.platformID?string]}</td>
                <td>${release.releaseDate}</td>
                <td>${release.cost}</td>
                <td>${release.soldCount}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/games/${game.gameID}/edit_release/${release.platformID}">Edit</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
    <br/>
</@c.page>