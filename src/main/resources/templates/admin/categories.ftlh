<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Categories">
    <@header.header/>
    <br/>

    <#if s.isAdmin>
        <a class="nav-link btn btn-primary" href="/admin_panel/categories/new">Create new category</a>
    </#if>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Category ID</th>
            <th scope="col">Name</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#foreach category in categories>
            <tr>
                <td>${category.categoryID}</td>
                <td>${category.name}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/admin_panel/categories/${category.categoryID}/edit">Edit</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>