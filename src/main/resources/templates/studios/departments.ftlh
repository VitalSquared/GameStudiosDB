<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Departments">
    <@header.header/>
    <br/>
    <@f.filter url
        {
            'studio': ['Search studio', 'selection'] + studios?sequence?map(x -> [x.name, x.studioID])
        }
        {
            'studio' : 'studio'
        }
        false
    />
    <br/>

    <#if s.isAdmin || s.isGeneralDirector || s.isStudioDirector>
        <a class="nav-link btn btn-primary" href="/departments/new">Create new department</a>
    </#if>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Department ID</th>
            <th scope="col">Name</th>
            <th scope="col">Studio ID</th>
            <th scope="col">Head ID</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#foreach department in departments>
            <tr>
                <td>${department.departmentID}</td>
                <td>${department.name}</td>
                <td>${department.studioID}</td>
                <td>${department.headID!'null'}</td>
                <td>
                    <#if s.isAdmin || s.isGeneralDirector || s.isStudioDirector>
                        <a class="nav-link btn btn-primary" href="/departments/${department.departmentID}/edit">Edit</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>