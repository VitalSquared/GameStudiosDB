<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Add Employee To Test Application">
    <br/>
    <form method="POST" action="/tests/apps/${appID}/employee" enctype="multipart/form-data">
        <#assign employees_map = {}>
        <#foreach employee in employees>
            <#assign employees_map = employees_map + { employee.employeeID : employee.firstName + " " + employee.lastName + " (" + employee.getEmployeeID() + ")"}>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="genreID" style="width: 100px;">Genre: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "employee.employeeID", employees_map, "class=\"form-control\""/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Add"/>
    </form>
</@c.page>