<#import "../parts/common.ftlh" as c>

<@c.page "Employees">
<#foreach employee in employees>
    <a href="/employees/${employee.getEmployeeID()}">
        ${employee.getFirstName()} ${employee.getLastName()}, ${employee.getStudioID()}
    </a>
    <br/>
</#foreach>
</@c.page>