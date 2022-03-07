<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "New Game">
    <br/>
    <form method="POST" action="/games" enctype="multipart/form-data">
        <label for="startDate">Start Date: </label>
        <label for="startDate">
            <@spring.bind "game.startDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${game.startDate!}" placeholder="" />
        </label>
        <br/>
        <label for="devName">Dev Name: </label>
        <@spring.formInput "game.devName"/>
        <br/>
        <label for="releaseName">Release Name: </label>
        <@spring.formInput "game.releaseName"/>
        <br/>
        <label for="releaseName">Expenses: </label>
        <@spring.formInput "game.expenses"/>
        <br/>
        <#assign studios_map = {}>
        <#foreach studio in studios>
            <#assign studios_map = studios_map + { studio.studioID : "#" + studio.name }>
        </#foreach>
        <label for="studioID">Studio ID: </label>
        <@spring.formSingleSelect "game.studioID", studios_map/>
        <br/>
        <input type="submit" value="Create"/>
    </form>
</@c.page>