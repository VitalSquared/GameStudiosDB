<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Edit Category">
    <@header.header/>
    <br/>

    <form method="POST" action="/admin_panel/categories/${category.categoryID}" enctype="multipart/form-data">
        <label for="topic">Name: </label>
        <@spring.formInput "category.name"/>
        <br/>
        <input type="submit" value="Modify"/>
    </form>
</@c.page>