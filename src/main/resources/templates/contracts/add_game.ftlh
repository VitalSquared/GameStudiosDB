<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Add Game To Contract">
    <br/>
    <form method="POST" action="/contracts/${contractID}/game" enctype="multipart/form-data">
        <#assign games_map = {}>
        <#foreach game in games>
            <#assign games_map = games_map + { game.gameID : game.devName }>
        </#foreach>
        <label for="testID">Game: </label>
        <@spring.formSingleSelect "game.gameID", games_map/>
        <br/>
        <input type="submit" value="Add"/>
    </form>
</@c.page>