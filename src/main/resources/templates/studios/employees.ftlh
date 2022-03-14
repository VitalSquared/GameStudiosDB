<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Employees">
    <@header.header/>
    <br/>
    <#if s.isAdmin || s.isGeneralDirector>
        <#assign anyStudio = [['Any Studio' , -1]]>
    <#else>
        <#assign anyStudio = []>
    </#if>
    <#assign any_filters = (filters?string?length gt 0) />

    <#if sortField == idSortField>
        <#assign idSortDir = nextSortDir />
    <#else>
        <#assign idSortDir = "ASC"/>
    </#if>

    <#if sortField == firstNameSortField>
        <#assign firstNameSortDir = nextSortDir />
    <#else>
        <#assign firstNameSortDir = "ASC"/>
    </#if>

    <#if sortField == lastNameSortField>
        <#assign lastNameSortDir = nextSortDir />
    <#else>
        <#assign lastNameSortDir = "ASC"/>
    </#if>

    <#assign accounts_map = {}>
    <#foreach account in all_accounts>
        <#assign accounts_map = accounts_map + { account.employeeID?string : account.email }>
    </#foreach>

    <#assign studios_map = {}>
    <#foreach studio in all_studios>
        <#assign studios_map = studios_map + { studio.studioID?string : studio.name }>
    </#foreach>

    <#assign departments_map = {}>
    <#foreach department in all_departments>
        <#assign departments_map = departments_map + { department.departmentID?string : department.name }>
    </#foreach>

    <@f.filter urlFilterless
        {
            'studio': ['Search studio', 'selection'] + anyStudio + studios?sequence?map(x -> [x.name, x.studioID]),
            'firstName' : ['First name', 'input'],
            'lastName' : ['Last name', 'input']
        }
        {
            'studio' : 'studio',
            'firstName' : 'firstName',
            'lastName' : 'lastName'
        }
        any_filters!false
    />
    <br/>

    <#if s.isAdmin>
        <a class="nav-link btn btn-primary" href="/employees/new">Create new employee</a>
    </#if>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">
                <a href="${urlFilter}&sortField=${idSortField}&sortDir=${idSortDir}">ID</a>
            </th>
            <th scope="col">
                <a href="${urlFilter}&sortField=${firstNameSortField}&sortDir=${firstNameSortDir}">First Name</a>
            </th>
            <th scope="col">
                <a href="${urlFilter}&sortField=${lastNameSortField}&sortDir=${lastNameSortDir}">Last Name</a>
            </th>
            <th scope="col">Studio</th>
            <th scope="col">Department</th>
            <th scope="col">Email</th>
            <th scope="col">Active</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#foreach employee in employees>
            <tr>
                <td>${employee.employeeID}</td>
                <td>${employee.firstName}</td>
                <td>${employee.lastName}</td>
                <td>${studios_map[employee.studioID?string]} (${employee.studioID})</td>
                <td>
                    <#if employee.departmentID??>
                        ${departments_map[employee.departmentID?string]} (${employee.departmentID})
                    <#else>
                        None
                    </#if>
                </td>
                <td>${accounts_map[employee.employeeID?string]}</td>
                <td>${employee.active?c}</td>
                <td>
                    <#if s.isAdmin>
                        <a class="nav-link btn btn-primary" href="/employees/${employee.employeeID}/edit">Edit</a>
                    </#if>
                </td>
            </tr>
        </#foreach>
        </tbody>
    </table>
</@c.page>