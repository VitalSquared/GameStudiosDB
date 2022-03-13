<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Edit Game Release">
    <br/>
    <form method="POST" action="/games/${gameID}/edit_release/${platformID}" enctype="multipart/form-data">
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
        <input type="submit" value="Update"/>
    </form>
</@c.page>