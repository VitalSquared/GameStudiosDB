<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "Edit Platform">
    <@header.header/>
    <br/>

    <form method="POST" action="/admin_panel/platforms/${platformID}/edit" enctype="multipart/form-data">
        <label for="topic">Name: </label>
        <@spring.formInput "platform.name"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="topic">Percent: </label>
        <#assign values = {} />
        <#foreach value in 0..100>
            <#assign values = values + { value : value + "%" }>
        </#foreach>
        <@spring.formSingleSelect "platform.percent", values/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <input type="submit" value="Modify"/>
    </form>
</@c.page>