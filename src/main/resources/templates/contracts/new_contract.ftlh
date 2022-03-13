<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "New Contract">
    <br/>
    <form method="POST" action="/contracts/new" enctype="multipart/form-data">
        <label for="date">Date: </label>
        <label for="date">
            <@spring.bind "contract.date"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(contract.date!.now)?string('YYYY-MM-dd')}" placeholder="" />
            <@spring.showErrors "<br>" "color:red;"/>
        </label>
        <br/>
        <label for="percent">Percent: </label>
        <#assign values = {} />
        <#foreach value in 0..100>
            <#assign values = values + { value : value + "%" }>
        </#foreach>
        <@spring.formSingleSelect "contract.percent", values/>
        <@spring.showErrors "<br>" "color:red;"/>
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