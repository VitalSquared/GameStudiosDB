<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "New Game Release">
    <br/>
    <form method="POST" action="/games/${gameID}/new_release" enctype="multipart/form-data">
        <#assign contracts_map = {}>
        <#foreach contract in contracts>
            <#assign contracts_map = contracts_map + { contract.contractID : '#' + contract.contractID}>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="contractID" style="width: 100px;">Contract: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "release.contractID", contracts_map, "class=\"form-control\""/>
            </div>
        </div>

        <#assign platforms_map = {}>
        <#foreach platform in platforms>
            <#assign platforms_map = platforms_map + { platform.platformID : platform.name}>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="platformID" style="width: 100px;">Platform: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "release.platformID", platforms_map, "class=\"form-control\""/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="date" style="width: 100px;">Release Date: </label>
            <div class="col-sm-5">
                <label for="date">
                    <@spring.bind "release.releaseDate"/>
                    <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(release.releaseDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
                    <@spring.showErrors "<br>" "color:red;"/>
                </label>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="cost" style="width: 100px;">Cost: </label>
            <div class="col-sm-5">
                <@spring.formInput "release.cost", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="soldCount" style="width: 100px;">Sold Count: </label>
            <div class="col-sm-5">
                <@spring.formInput "release.soldCount", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Create"/>
    </form>
</@c.page>