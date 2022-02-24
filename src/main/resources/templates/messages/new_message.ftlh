<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "New Message">
<#include "_header.ftlh">

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

    <div>
    <label>Add attachment: </label>
    <input type="file" name="file"/>
    </div>

    <br/>
    <input type="submit" value="Send!"/>
</form>
</@c.page>