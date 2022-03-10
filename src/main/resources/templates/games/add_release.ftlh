<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Add Release To Game">
    <br/>
    <form method="POST" action="/games/${gameID}/release" enctype="multipart/form-data">
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
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${release.releaseDate!}" placeholder="" />
        </label>
        <br/>
        <label for="cost">Cost: </label>
        <@spring.formInput "release.cost"/>
        <br/>
        <label for="soldCount">Sold Count: </label>
        <@spring.formInput "release.soldCount"/>
        <br/>
        <input type="submit" value="Add"/>
    </form>
</@c.page>