<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Testings">
    <#if s.isAdmin || s.isGeneralDirector>
        <a class="nav-link btn btn-primary" href="/tests/new">Create new test</a>
    </#if>

    <#assign statuses_map = {}>
    <#foreach status in all_statuses>
        <#assign statuses_map = statuses_map + { status.statusID?string : status.name }>
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
        <#foreach test in tests>
            <tr>
                <td>${test.testID}</td>
                <td>${test.startDate}</td>
                <td>${test.endDate}</td>
                <td>${test.grand}</td>
                <td>${test.minStudiosNum}</td>
                <td>${test.appsDeadline}</td>
                <td>${statuses_map[test.statusID?string]}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/tests/${test.testID}">View</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>