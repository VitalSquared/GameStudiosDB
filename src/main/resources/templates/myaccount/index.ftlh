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
            <th scope="col">Department ID</th>
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
            <td>${employee.departmentID}</td>
            <td>${employee.active?c}</td>
            <td>${account.login}</td>
        </tr>
        </tbody>
    </table>
</@c.page>