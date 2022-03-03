<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Employees">
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

    <#if s.isAdmin>
        <a class="nav-link btn btn-primary" href="/employees/new">Create new employee</a>
    </#if>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Studio ID</th>
            <th scope="col">Department ID</th>
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
                <td>${employee.studioID}</td>
                <td>${employee.departmentID}</td>
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