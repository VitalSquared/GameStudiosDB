<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Genres">
    <@header.header/>
    <br/>



    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Genre ID</th>
            <th scope="col">Name</th>
            <th scope="col">
                <#if s.isAdmin>
                    <a class="nav-link btn btn-primary" href="/admin_panel/genres/new">Create new genre</a>
                </#if>
            </th>
        </tr>
        </thead>
        <tbody>
        <#foreach genre in genres>
            <tr>
                <td>${genre.genreID}</td>
                <td>${genre.name}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="btn btn-primary" href="/admin_panel/genres/${genre.genreID}/edit">Edit</a>
                        &emsp;
                        <a class="btn btn-primary" href="/admin_panel/genres/${genre.genreID}/delete">Delete</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>