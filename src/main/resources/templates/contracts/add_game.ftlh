<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Add Game To Contract">
    <br/>
    <form method="POST" action="/contracts/${contractID}/game" enctype="multipart/form-data">
        <#assign games_map = {}>
        <#foreach game in games>
            <#assign games_map = games_map + { game.gameID : game.devName }>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="testID" style="width: 100px;">Game: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "game.gameID", games_map, "class=\"form-control\""/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Add"/>
    </form>
</@c.page>