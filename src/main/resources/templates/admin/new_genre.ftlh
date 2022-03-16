<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "New Genre">
    <@header.header/>
    <br/>

    <form method="POST" action="/admin_panel/genres/new" enctype="multipart/form-data">
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="topic" style="width: 100px;">Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "genre.name", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Create"/>
    </form>
</@c.page>