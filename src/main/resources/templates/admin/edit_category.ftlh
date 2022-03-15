<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Edit Category">
    <@header.header/>
    <br/>
    <form method="POST" action="/admin_panel/categories/${categoryID}/edit" enctype="multipart/form-data">
        <div class="form-group row offset-1">
            <label for="topic">Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "category.name", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Update"/>
    </form>
</@c.page>