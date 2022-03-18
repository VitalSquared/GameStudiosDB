<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />
<#import "_header.ftlh" as header>

<@c.page "New Message">
<@header.header numberOfUnread/>
<br/>
<form method="POST" action="/messages/new_message" enctype="multipart/form-data">
    <div class="form-group row offset-1">
        <label for="receivers">Send to: </label>
        <div class="col-sm-5">
            <@spring.formInput "message.receiversString", "class=\"form-control\""/>
            <@spring.showErrors "<br>" "color:red;"/>
        </div>
    </div>

    <div class="form-group row offset-1">
        <label for="topic">Topic: </label>
        <div class="col-sm-5">
            <@spring.formInput "message.topic", "class=\"form-control\""/>
            <@spring.showErrors "<br>" "color:red;"/>
        </div>
    </div>

    <div class="form-group row offset-1">
        <label for="content"></label>
        <div class="col-sm-10">
            <@spring.formTextarea "message.content", "class=\"form-control\" style=\"height: 300px;\""/>
            <@spring.showErrors "<br>" "color:red;"/>
        </div>
    </div>

    <#foreach idx in [1, 2, 3, 4, 5]>
        <div class="form-group row offset-1">
            <label>Add attachment ${idx}: </label>
            <div class="col-sm-5 custom-file">
                <input type="file" name="file" class="form-control" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01"/>
            </div>
        </div>
    </#foreach>

    <input type="submit" class="btn btn-primary offset-1" value="Send!"/>
</form>
</@c.page>