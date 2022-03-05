<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Testings">
    <#if s.isAdmin || s.isGeneralDirector>
        <a class="nav-link btn btn-primary" href="/tests/new">Create new test</a>
    </#if>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Start Date</th>
            <th scope="col">End Date</th>
            <th scope="col">Grand</th>
            <th scope="col">Min Studios Num</th>
            <th scope="col">Applications Deadline</th>
            <th scope="col">Status ID</th>
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
                <td>${test.statusID}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/tests/${test.testID}/edit">Edit</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>