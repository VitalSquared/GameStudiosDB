<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "New Test">
    <br/>
    <form method="POST" action="/tests" enctype="multipart/form-data">
        <label for="first_name">Start Date: </label>
        <label for="startDate">
            <@spring.bind "test.startDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${test.startDate!}" placeholder="" />
        </label>
        <br/>
        <label for="last_name">End Date: </label>
        <label for="endDate">
            <@spring.bind "test.endDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${test.endDate!}" placeholder="" />
        </label>
        <br/>
        <label for="grand">Grand: </label>
        <@spring.formInput "test.grand"/>
        <br/>
        <label for="minStudiosNum">Min Studios Number: </label>
        <@spring.formInput "test.minStudiosNum"/>
        <br/>
        <label for="appsDeadline">Applications Deadline: </label>
        <label for="appsDeadline">
            <@spring.bind "test.appsDeadline"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${test.appsDeadline!}" placeholder="" />
        </label>
        <br/>
        <input type="submit" value="Create"/>
    </form>
</@c.page>