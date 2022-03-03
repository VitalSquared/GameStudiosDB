<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Edit Genre">
    <@header.header/>
    <br/>

    <form method="POST" action="/admin_panel/genres/${genre.genreID}" enctype="multipart/form-data">
        <label for="topic">Name: </label>
        <@spring.formInput "genre.name"/>
        <br/>
        <input type="submit" value="Modify"/>
    </form>
</@c.page>