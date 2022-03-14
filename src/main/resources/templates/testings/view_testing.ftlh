<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Testing">

    <#assign statuses_map = {}>
    <#foreach status in all_statuses>
        <#assign statuses_map = statuses_map + { status.statusID?string : status.name }>
    </#foreach>

    <#assign results_map = {}>
    <#foreach result in all_results>
        <#assign results_map = results_map + { result.resultID?string : result.name }>
    </#foreach>

    <#assign studios_map = {}>
    <#foreach studio in all_studios>
        <#assign studios_map = studios_map + { studio.studioID?string : studio.name }>
    </#foreach>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Start Date</th>
            <th scope="col">End Date</th>
            <th scope="col">Grand</th>
            <th scope="col">Min Studios Num</th>
            <th scope="col">Applications Deadline</th>
            <th scope="col">Status</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${test.testID}</td>
            <td>${test.startDate}</td>
            <td>${test.endDate}</td>
            <td>${test.grand}</td>
            <td>${test.minStudiosNum}</td>
            <td>${test.appsDeadline}</td>
            <td>${statuses_map[test.statusID?string]}</td>
            <td>
                <#if s.isAdmin && test.statusID lt 3>
                    <a class="nav-link btn btn-primary" href="/tests/${test.testID}/edit">Edit</a>
                </#if>
            </td>
        </tr>
        </tbody>
    </table>
    <#if s.isAdmin>
        <#if test.statusID == 0>
            <a class="btn btn-primary" href="/tests/${test.testID}/start_test">Start Test</a>
        </#if>
        <#if test.statusID == 1>
            <a class="btn btn-primary" href="/tests/${test.testID}/finish_test">Finish Test</a>
        </#if>
        <#if test.statusID == 2>
            <a class="btn btn-primary" href="/tests/${test.testID}/results_test">Results are ready</a>
        </#if>
        <#if test.statusID lt 3>
            <a class="btn btn-primary" href="/tests/${test.testID}/cancel_test">Cancel Test</a>
        </#if>
        <#if test.statusID == 4>
            <a class="btn btn-primary" href="/tests/${test.testID}/delete_test">Delete Test</a>
        </#if>
    </#if>
    <br/>
    <#if test.statusID != 4>
        Genres:
        <#if (s.isAdmin || s.isGeneralDirector) && test.statusID != 3>
            <a class="nav-link btn btn-primary" href="/tests/${test.testID}/add_genre">Add genre</a>
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
                        <#if s.isAdmin && test.statusID != 3>
                            <a class="nav-link btn btn-primary" href="/tests/${test.testID}/remove_genre/${genre.genreID}">Remove from test</a>
                        </#if>
                    </td>
                </tr>
            </#foreach>
            </tbody>
        </table>
        <br/>
        List of applications:
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Studio</th>
                <th scope="col">Result</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#foreach app in apps>
                <tr>
                    <td>${app.appID}</td>
                    <td>${studios_map[app.studioID?string]} (${app.studioID})</td>
                    <td>${results_map[app.resultID?string]}</td>
                    <td>
                        <#if s.isAdmin>
                            <a class="nav-link btn btn-primary" href="/tests/apps/${app.appID}">View</a>
                        </#if>
                    </td>
                </tr>
            </#foreach>
            </tbody>
        </table>
    </#if>
</@c.page>