<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Modify Game">
    <br/>
    <form method="POST" action="/games/${gameID}/edit" enctype="multipart/form-data">
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="startDate" style="width: 100px;">Start Date: </label>
            <div class="col-sm-5">
                <label for="startDate">
                    <@spring.bind "game.startDate"/>
                    <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(game.startDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
                    <@spring.showErrors "<br>" "color:red;"/>
                </label>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="devName" style="width: 100px;">Dev Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "game.devName", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="releaseName" style="width: 100px;">Release Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "game.releaseName", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="releaseName" style="width: 100px;">Expenses: </label>
            <div class="col-sm-5">
                <@spring.formInput "game.expenses", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Update"/>
    </form>
</@c.page>