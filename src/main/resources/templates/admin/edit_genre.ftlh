<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Edit Genre">
    <@header.header/>
    <br/>

    <form method="POST" action="/admin_panel/genres/${genreID}/edit" enctype="multipart/form-data">
        <label for="topic">Name: </label>
        <@spring.formInput "genre.name"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <input type="submit" value="Modify"/>
    </form>
</@c.page>