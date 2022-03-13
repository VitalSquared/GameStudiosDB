<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Modify Game">
    <br/>
    <form method="POST" action="/games/${gameID}/edit" enctype="multipart/form-data">
        <label for="startDate">Start Date: </label>
        <label for="startDate">
            <@spring.bind "game.startDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(game.startDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
            <@spring.showErrors "<br>" "color:red;"/>
        </label>
        <br/>
        <label for="devName">Dev Name: </label>
        <@spring.formInput "game.devName"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="releaseName">Release Name: </label>
        <@spring.formInput "game.releaseName"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="releaseName">Expenses: </label>
        <@spring.formInput "game.expenses"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <input type="submit" value="Update"/>
    </form>
</@c.page>