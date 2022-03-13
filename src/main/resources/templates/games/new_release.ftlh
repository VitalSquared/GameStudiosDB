<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "New Game Release">
    <br/>
    <form method="POST" action="/games/${gameID}/new_release" enctype="multipart/form-data">
        <#assign contracts_map = {}>
        <#foreach contract in contracts>
            <#assign contracts_map = contracts_map + { contract.contractID : '#' + contract.contractID}>
        </#foreach>
        <label for="contractID">Contract: </label>
        <@spring.formSingleSelect "release.contractID", contracts_map/>
        <br/>
        <#assign platforms_map = {}>
        <#foreach platform in platforms>
            <#assign platforms_map = platforms_map + { platform.platformID : platform.name}>
        </#foreach>
        <label for="platformID">Platform: </label>
        <@spring.formSingleSelect "release.platformID", platforms_map/>
        <br/>
        <label for="date">Release Date: </label>
        <label for="date">
            <@spring.bind "release.releaseDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(release.releaseDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
            <@spring.showErrors "<br>" "color:red;"/>
        </label>
        <br/>
        <label for="cost">Cost: </label>
        <@spring.formInput "release.cost"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="soldCount">Sold Count: </label>
        <@spring.formInput "release.soldCount"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <input type="submit" value="Create"/>
    </form>
</@c.page>