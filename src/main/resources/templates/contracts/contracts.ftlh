<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Contracts">


    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Date</th>
            <th scope="col">Percent</th>
            <th scope="col">Test ID</th>
            <th scope="col">
                <#if s.isAdmin || s.isGeneralDirector>
                    <a class="nav-link btn btn-primary" href="/contracts/new">Create new contract</a>
                </#if>
            </th>
        </tr>
        </thead>
        <tbody>
        <#foreach contract in contracts>
            <tr>
                <td>${contract.contractID}</td>
                <td>${contract.date}</td>
                <td>${contract.percent}</td>
                <td>${contract.testID}</td>
                <td>
                    <#if s.isAdmin || s.isGeneralDirector || s.isStudioDirector>
                        <a class="nav-link btn btn-primary" href="/contracts/${contract.contractID}">View</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>