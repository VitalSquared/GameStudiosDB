<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Delete Category">
    <@header.header/>
    <br/>

    <#assign categories_map = {}>
    <#foreach category in remaining_categories>
        <#assign categories_map = categories_map + { category.categoryID : category.name }>
    </#foreach>

    <form method="POST" action="/admin_panel/categories/${categoryID}/delete" enctype="multipart/form-data">
        <label for="topic">Replace With: </label>
        <@spring.formSingleSelect "new_category.newCategoryID" categories_map/>
        <br/>
        <input type="submit" value="Delete '${category.name}'"/>
    </form>
</@c.page>