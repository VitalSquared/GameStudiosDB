<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Modify Studio">
    <@header.header/>
    <br/>
    <form method="POST" action="/studios/${studioID}/edit" enctype="multipart/form-data">
        <label for="topic">Name: </label>
        <@spring.formInput "studio.name"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="content">Address: </label>
        <@spring.formInput "studio.address"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>

        <input type="submit" value="Update"/>
    </form>
</@c.page>