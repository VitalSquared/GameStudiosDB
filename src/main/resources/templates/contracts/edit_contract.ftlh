<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Edit Contract">
    <br/>
    <form method="POST" action="/contracts/${contract.contractID}" enctype="multipart/form-data">
        <label for="date">Date: </label>
        <label for="date">
            <@spring.bind "contract.date"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${contract.date?string('YYYY-MM-dd')!}" placeholder="" />
        </label>
        <br/>
        <label for="percent">Percent: </label>
        <@spring.formInput "contract.percent"/>
        <br/>
        <input type="submit" value="Update"/>
    </form>
</@c.page>