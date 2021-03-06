<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Departments">
    <@header.header/>
    <br/>
    <#if s.isAdmin || s.isGeneralDirector>
        <#assign anyStudio = [['Any Studio' , -1]]>
    <#else>
        <#assign anyStudio = []>
    </#if>

    <#if sortField == idSortField>
        <#assign idSortDir = nextSortDir />
    <#else>
        <#assign idSortDir = "ASC"/>
    </#if>

    <#if sortField == nameSortField>
        <#assign nameSortDir = nextSortDir />
    <#else>
        <#assign nameSortDir = "ASC"/>
    </#if>

    <#assign studios_map = {}>
    <#foreach studio in all_studios>
        <#assign studios_map = studios_map + { studio.studioID?string : studio.name }>
    </#foreach>

    <#assign employees_map = {}>
    <#foreach employee in all_employees>
        <#assign employees_map = employees_map + { employee.employeeID?string : employee.firstName + ' ' + employee.lastName }>
    </#foreach>

    <@f.filter url
        {
            'studio': ['Search studio', 'selection'] + anyStudio + studios?sequence?map(x -> [x.name, x.studioID])
        }
        {
            'studio' : 'studio'
        }
        false
    />
    <br/>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">
                <a href="${urlFilter}&sortField=${idSortField}&sortDir=${idSortDir}">ID</a>
            </th>
            <th scope="col">
                <a href="${urlFilter}&sortField=${nameSortField}&sortDir=${nameSortDir}">Name</a>
            </th>
            <th scope="col">Studio</th>
            <th scope="col">Head</th>
            <th scope="col"></th>
            <th scope="col">
                <#if s.isAdmin || s.isGeneralDirector || s.isStudioDirector>
                    <a class="nav-link btn btn-primary" href="/departments/new">Create new department</a>
                </#if>
            </th>
        </tr>
        </thead>
        <tbody>
        <#foreach department in departments>
            <tr>
                <td>${department.departmentID}</td>
                <td>${department.name}</td>
                <td>${studios_map[department.studioID?string]} (${department.studioID})</td>
                <td>
                    <#if (department.headID!-1) gt 0>
                        ${employees_map[department.headID?string]} (${department.headID})
                    <#else>
                        None
                    </#if>
                </td>
                <td>
                    <#if s.isAdmin || s.isGeneralDirector || s.isStudioDirector>
                        <a class="nav-link btn btn-primary" href="/departments/${department.departmentID}/edit">Edit</a>
                    </#if>
                </td>
                <td>
                    <#if s.isAdmin && !department.isRoot>
                        <a class="nav-link btn btn-primary" href="/departments/${department.departmentID}/delete">Delete</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>