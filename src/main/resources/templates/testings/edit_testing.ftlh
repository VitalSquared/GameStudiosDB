<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Edit Test">
    <br/>
    <form method="POST" action="/tests/${testID}/edit" enctype="multipart/form-data">
        <label for="first_name">Start Date: </label>
        <label for="startDate">
            <@spring.bind "test.startDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(test.startDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
            <@spring.showErrors "<br>" "color:red;"/>
        </label>
        <br/>
        <label for="last_name">End Date: </label>
        <label for="endDate">
            <@spring.bind "test.endDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(test.endDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
            <@spring.showErrors "<br>" "color:red;"/>
        </label>
        <br/>
        <label for="grand">Grand: </label>
        <@spring.formInput "test.grand"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="minStudiosNum">Min Studios Number: </label>
        <@spring.formInput "test.minStudiosNum"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="appsDeadline">Applications Deadline: </label>
        <label for="appsDeadline">
            <@spring.bind "test.appsDeadline"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(test.appsDeadline!.now)?string('YYYY-MM-dd')}" placeholder="" />
            <@spring.showErrors "<br>" "color:red;"/>
        </label>
        <br/>
        <input type="submit" value="Update"/>
    </form>
</@c.page>