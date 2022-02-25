<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />
<#import "_header.ftlh" as header>

<@c.page "New Message">
<@header.header numberOfUnread/>

<form method="POST" action="/messages" enctype="multipart/form-data">
    <label for="receivers">Send to: </label>
    <@spring.formInput "message.receiversString"/>
    <br/>
    <label for="topic">Topic: </label>
    <@spring.formInput "message.topic"/>
    <br/>
    <label for="content"></label>
    <@spring.formInput "message.content"/>
    <br/>

    <#foreach idx in [1, 2, 3, 4, 5]>
        <div>
            <label>Add attachment ${idx}: </label>
            <input type="file" name="file"/>
        </div>
    </#foreach>
    <br/>
    <input type="submit" value="Send!"/>
</form>
</@c.page>