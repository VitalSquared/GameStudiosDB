<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "New Department">
    <@header.header/>
    <br/>
    <form method="POST" action="/departments/new" enctype="multipart/form-data">
        <#assign hash1 = {}>
        <#foreach studio in studios>
            <#assign hash1 = hash1 + { studio.studioID : studio.name }>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="topic" style="width: 100px;">Studio: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "department.studioID", hash1, "class=\"form-control\""/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="topic" style="width: 100px;">Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "department.name", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <#assign hash2 = { -1 : "None"}>
        <#foreach employee in employees>
            <#assign hash2 = hash2 + { employee.employeeID : employee.firstName + ' ' + employee.lastName }>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="content" style="width: 100px;">Head: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "department.headID", hash2, "class=\"form-control\""/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Create"/>
    </form>
</@c.page>