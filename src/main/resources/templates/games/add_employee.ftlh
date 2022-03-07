<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Add Employee To Game">
    <br/>
    <form method="POST" action="/games/${gameID}/employee" enctype="multipart/form-data">
        <#assign employees_map = {}>
        <#foreach employee in employees>
            <#assign employees_map = employees_map + { employee.employeeID : employee.firstName + " " + employee.lastName + " (" + employee.getEmployeeID() + ")"}>
        </#foreach>
        <label for="genreID">Genre: </label>
        <@spring.formSingleSelect "employee.employeeID", employees_map/>
        <br/>
        <input type="submit" value="Add"/>
    </form>
</@c.page>