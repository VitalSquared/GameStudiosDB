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
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="topic" style="width: 100px;">Replace With: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "new_category.newCategoryID", categories_map, "class=\"form-control\""/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Delete '${category.name}'"/>
    </form>
</@c.page>