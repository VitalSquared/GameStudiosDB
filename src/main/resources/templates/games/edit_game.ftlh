<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Modify Game">
    <br/>
    <form method="POST" action="/games/${game.gameID}" enctype="multipart/form-data">
        <label for="startDate">Start Date: </label>
        <label for="startDate">
            <@spring.bind "game.startDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${game.startDate?string('YYYY-MM-dd')!}" placeholder="" />
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
        <input type="submit" value="Update"/>
    </form>
</@c.page>