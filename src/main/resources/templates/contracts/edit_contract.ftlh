<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Edit Contract">
    <br/>
    <form method="POST" action="/contracts/${contractID}/edit" enctype="multipart/form-data">
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
        <input type="submit" value="Update"/>
    </form>
</@c.page>