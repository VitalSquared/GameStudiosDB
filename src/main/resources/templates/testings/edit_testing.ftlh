<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Edit Test">
    <br/>
    <form method="POST" action="/tests/${testID}/edit" enctype="multipart/form-data">
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="first_name" style="width: 100px;">Start Date: </label>
            <div class="col-sm-5">
                <label for="startDate">
                    <@spring.bind "test.startDate"/>
                    <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(test.startDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
                    <@spring.showErrors "<br>" "color:red;"/>
                </label>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="last_name" style="width: 100px;">End Date: </label>
            <div class="col-sm-5">
                <label for="endDate">
                    <@spring.bind "test.endDate"/>
                    <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(test.endDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
                    <@spring.showErrors "<br>" "color:red;"/>
                </label>

            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="grand" style="width: 100px;">Grand: </label>
            <div class="col-sm-5">
                <@spring.formInput "test.grand", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="minStudiosNum" style="width: 100px;">Min Studios Number: </label>
            <div class="col-sm-5">
                <@spring.formInput "test.minStudiosNum", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="appsDeadline" style="width: 100px;">Applications Deadline: </label>
            <div class="col-sm-5">
                <label for="appsDeadline">
                    <@spring.bind "test.appsDeadline"/>
                    <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(test.appsDeadline!.now)?string('YYYY-MM-dd')}" placeholder="" />
                    <@spring.showErrors "<br>" "color:red;"/>
                </label>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Update"/>
    </form>
</@c.page>