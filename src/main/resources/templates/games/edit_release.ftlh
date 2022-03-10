<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Edit Release To Game">
    <br/>
    <form method="POST" action="/games/${gameID}/edit_release/${platformID}/post" enctype="multipart/form-data">
        <label for="date">Release Date: </label>
        <label for="date">
            <@spring.bind "release.releaseDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${release.releaseDate?string('YYYY-MM-dd')!}" placeholder="" />
        </label>
        <br/>
        <label for="cost">Cost: </label>
        <@spring.formInput "release.cost"/>
        <br/>
        <label for="soldCount">Sold Count: </label>
        <@spring.formInput "release.soldCount"/>
        <br/>
        <input type="submit" value="Update"/>
    </form>
</@c.page>