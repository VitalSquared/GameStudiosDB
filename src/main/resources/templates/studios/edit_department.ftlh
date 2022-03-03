<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Edit Department">
    <@header.header/>
    <br/>

    <form method="POST" action="/departments/${department.departmentID}" enctype="multipart/form-data">
        <label for="topic">Studio: </label>
        <#assign hash1 = {}>
        <#foreach studio in studios>
            <#assign hash1 = hash1 + { studio.studioID : studio.name }>
        </#foreach>
        <@spring.formSingleSelect "department.studioID", hash1, "disabled"/>
        <br/>
        <label for="topic">Name: </label>
        <@spring.formInput "department.name"/>
        <br/>
        <label for="content">Head: </label>
        <#assign hash2 = { -1 : "None"}>
        <#foreach employee in employees>
            <#assign hash2 = hash2 + { employee.employeeID : employee.firstName + ' ' + employee.lastName }>
        </#foreach>
        <@spring.formSingleSelect "department.headID", hash2/>
        <br/>
        <input type="submit" value="Modify"/>
    </form>
</@c.page>