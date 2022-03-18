<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Me">

    <#assign studios_map = {}>
    <#foreach studio in all_studios>
        <#assign studios_map = studios_map + { studio.studioID?string : studio.name }>
    </#foreach>

    <#assign departments_map = {}>
    <#foreach department in all_departments>
        <#assign departments_map = departments_map + { department.departmentID?string : department.name }>
    </#foreach>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Studio ID</th>
            <#if employee.departmentID??>
                <th scope="col">Department ID</th>
            </#if>
            <th scope="col">Active</th>
            <th scope="col">User Name</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${employee.employeeID}</td>
            <td>${employee.firstName}</td>
            <td>${employee.lastName}</td>
            <td>${studios_map[employee.studioID?string]}</td>
            <#if employee.departmentID??>
                <td>${departments_map[employee.departmentID?string]}</td>
            </#if>
            <td>${employee.active?c}</td>
            <td>${account.email}</td>
        </tr>
        </tbody>
    </table>
    <br/>
    <a class="btn btn-primary" href="/userInfo/change_password">Change password</a>
</@c.page>