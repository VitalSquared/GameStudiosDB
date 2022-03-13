<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "New Genre">
    <@header.header/>
    <br/>

    <form method="POST" action="/admin_panel/genres/new" enctype="multipart/form-data">
        <label for="topic">Name: </label>
        <@spring.formInput "genre.name"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <input type="submit" value="Create"/>
    </form>
</@c.page>