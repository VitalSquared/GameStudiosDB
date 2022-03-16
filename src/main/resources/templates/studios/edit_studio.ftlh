<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />
<#import "../parts/security.ftlh" as s>

<@c.page "Modify Studio">
    <@header.header/>
    <br/>
    <form method="POST" action="/studios/${studioID}/edit" enctype="multipart/form-data">
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="topic" style="width: 100px;">Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "studio.name", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="content" style="width: 100px;">Address: </label>
            <div class="col-sm-5">
                <@spring.formInput "studio.address", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Update"/>
    </form>

    <br/>
    <#if s.isAdmin && !anyReferences>
        <a class="nav-link btn btn-primary" href="/studios/${studioID}/delete">Delete</a>
    </#if>
</@c.page>
