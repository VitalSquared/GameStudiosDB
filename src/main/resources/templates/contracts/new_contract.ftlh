<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "New Contract">
    <br/>
    <form method="POST" action="/contracts" enctype="multipart/form-data">
        <label for="date">Date: </label>
        <label for="date">
            <@spring.bind "contract.date"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${contract.date!}" placeholder="" />
        </label>
        <br/>
        <label for="percent">Percent: </label>
        <@spring.formInput "contract.percent"/>
        <br/>
        <#assign tests_map = {}>
        <#foreach test in tests>
            <#assign tests_map = tests_map + { test.testID : "#" + test.testID }>
        </#foreach>
        <label for="testID">Test ID: </label>
        <@spring.formSingleSelect "contract.testID", tests_map/>
        <br/>
        <input type="submit" value="Create"/>
    </form>
</@c.page>