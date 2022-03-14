<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Test Application">

    <#assign results_map = {}>
    <#foreach result in all_results>
        <#assign results_map = results_map + { result.resultID?string : result.name }>
    </#foreach>

    <#assign studios_map = {}>
    <#foreach studio in all_studios>
        <#assign studios_map = studios_map + { studio.studioID?string : studio.name }>
    </#foreach>

    <a class="btn btn-primary" href="/tests/${app.testID}">Back to test</a>
    <br/>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Studio</th>
            <th scope="col">Test ID</th>
            <th scope="col">Result</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${app.appID}</td>
            <td>${studios_map[app.studioID?string]}</td>
            <td>${app.testID}</td>
            <td>${results_map[app.resultID?string]}</td>
        </tr>
        </tbody>
    </table>
    <#if app.resultID == 0 && curDate?date <= test.appsDeadline?date>
        <a class="btn btn-primary" href="/tests/apps/${app.appID}/accept">Accept</a>
        <a class="btn btn-primary" href="/tests/apps/${app.appID}/decline">Decline</a>
    </#if>
    <br/>
    <#if app.resultID == 1 && (test.statusID == 0 || test.statusID == 1)>
        <a class="btn btn-primary" href="/tests/apps/${app.appID}/decline">Quit</a>
        <br/>
    </#if>
    <#if app.resultID gt 0>
        Employees:
        <#if (s.isAdmin || s.isGeneralDirector || s.isStudioDirector) && (test.statusID == 0 || test.statusID == 1)>
            <a class="nav-link btn btn-primary" href="/tests/apps/${app.appID}/add_employee">Add employee</a>
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
                        <#if test.statusID == 0 || test.statusID == 1>
                            <a class="nav-link btn btn-primary" href="/tests/apps/${app.appID}/remove_employee/${employee.employeeID}">
                                Remove from application
                            </a>
                        </#if>
                    </td>
                </tr>
            </#foreach>
            </tbody>
        </table>
        <br/>
    </#if>
    <#if test.statusID == 2 || test.statusID == 3>
        <label>
            Result: ${results_map[app.resultID?string]}
        </label>
    </#if>
    <#if test.statusID == 2 && (s.isAdmin || s.isGeneralDirector)>
        <a class="nav-link btn btn-primary" href="/tests/apps/${app.appID}/success">Success</a>
        <a class="nav-link btn btn-primary" href="/tests/apps/${app.appID}/failure">Failure</a>
    </#if>
</@c.page>