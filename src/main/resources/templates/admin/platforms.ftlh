<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Platforms">
    <@header.header/>
    <br/>

    <#if s.isAdmin>
        <a class="nav-link btn btn-primary" href="/admin_panel/platforms/new">Create new platform</a>
    </#if>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Platform ID</th>
            <th scope="col">Name</th>
            <th scope="col">Percent</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#foreach platform in platforms>
            <tr>
                <td>${platform.platformID}</td>
                <td>${platform.name}</td>
                <td>${platform.percent}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/admin_panel/platforms/${platform.platformID}/edit">Edit</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>