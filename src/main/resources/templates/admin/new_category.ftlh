<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "New Category">
    <@header.header/>
    <br/>

    <form method="POST" action="/admin_panel/categories" enctype="multipart/form-data">
        <label for="topic">Name: </label>
        <@spring.formInput "category.name"/>
        <br/>
        <input type="submit" value="Create"/>
    </form>
</@c.page>