<#import "../parts/common.ftlh" as c>
<#import "../parts/filter.ftlh" as f>
<#import "../parts/security.ftlh" as s>

<@c.page "Me">
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
            <td>${employee.studioID}</td>
            <#if employee.departmentID??>
                <td>${employee.departmentID}</td>
            </#if>
            <td>${employee.active?c}</td>
            <td>${account.email}</td>
        </tr>
        </tbody>
    </table>
    <br/>
    <a class="btn btn-primary" href="/userInfo/change_password">Change password</a>
</@c.page>