<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Modify Studio">
    <@header.header/>
    <br/>
    <form method="POST" action="/studios/${studio.studioID}" enctype="multipart/form-data">
        <label for="topic">Name: </label>
        <@spring.formInput "studio.name"/>
        <br/>
        <label for="content">Address: </label>
        <@spring.formInput "studio.address"/>
        <br/>

        <input type="submit" value="Modify"/>
    </form>
</@c.page>