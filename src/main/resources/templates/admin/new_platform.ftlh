<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "New Platform">
    <@header.header/>
    <br/>

    <form method="POST" action="/admin_panel/platforms/new" enctype="multipart/form-data">
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="topic" style="width: 100px;">Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "platform.name", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <#assign values = {} />
        <#foreach value in 0..100>
            <#assign values = values + { value : value + "%" }>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="topic" style="width: 100px;">Percent: </label>
            <div class="col-sm-auto">
                <@spring.formSingleSelect "platform.percent", values, "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Create"/>
    </form>
</@c.page>