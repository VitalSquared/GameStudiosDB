<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Edit Game Release">
    <br/>
    <form method="POST" action="/games/${gameID}/edit_release/${platformID}" enctype="multipart/form-data">
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

        <input type="submit" class="btn btn-primary offset-1" value="Update"/>
    </form>
</@c.page>