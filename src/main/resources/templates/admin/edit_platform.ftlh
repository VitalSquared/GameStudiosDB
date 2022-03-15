<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Edit Platform">
    <@header.header/>
    <br/>
    <form method="POST" action="/admin_panel/platforms/${platformID}/edit" enctype="multipart/form-data">
        <div class="form-group row offset-1">
            <label for="topic">Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "platform.name", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <#assign values = {} />
        <#foreach value in 0..100>
            <#assign values = values + { value : value + "%" }>
        </#foreach>

        <div class="form-group row offset-1">
            <label for="topic">Percent: </label>
            <div class="col-sm-auto">
                <@spring.formSingleSelect "platform.percent", values, "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Update"/>
    </form>
</@c.page>